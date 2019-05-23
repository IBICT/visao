package br.com.ibict.visao.web.rest;

import br.com.ibict.visao.VisaoApp;

import br.com.ibict.visao.domain.GrupCategory;
import br.com.ibict.visao.domain.User;
import br.com.ibict.visao.domain.Category;
import br.com.ibict.visao.domain.User;
import br.com.ibict.visao.repository.GrupCategoryRepository;
import br.com.ibict.visao.service.GrupCategoryService;
import br.com.ibict.visao.web.rest.errors.ExceptionTranslator;
import br.com.ibict.visao.service.dto.GrupCategoryCriteria;
import br.com.ibict.visao.service.GrupCategoryQueryService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;


import static br.com.ibict.visao.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.ibict.visao.domain.enumeration.Permission;
/**
 * Test class for the GrupCategoryResource REST controller.
 *
 * @see GrupCategoryResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = VisaoApp.class)
public class GrupCategoryResourceIntTest {

    private static final byte[] DEFAULT_LOGO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_LOGO = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_LOGO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_LOGO_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_SOBRE = "AAAAAAAAAA";
    private static final String UPDATED_SOBRE = "BBBBBBBBBB";

    private static final Permission DEFAULT_PERMISSION = Permission.PUBLIC;
    private static final Permission UPDATED_PERMISSION = Permission.PRIVATE;

    @Autowired
    private GrupCategoryRepository grupCategoryRepository;
    @Mock
    private GrupCategoryRepository grupCategoryRepositoryMock;
    
    @Mock
    private GrupCategoryService grupCategoryServiceMock;

    @Autowired
    private GrupCategoryService grupCategoryService;

    @Autowired
    private GrupCategoryQueryService grupCategoryQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restGrupCategoryMockMvc;

    private GrupCategory grupCategory;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final GrupCategoryResource grupCategoryResource = new GrupCategoryResource(grupCategoryService, grupCategoryQueryService);
        this.restGrupCategoryMockMvc = MockMvcBuilders.standaloneSetup(grupCategoryResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GrupCategory createEntity(EntityManager em) {
        GrupCategory grupCategory = new GrupCategory()
            .logo(DEFAULT_LOGO)
            .logoContentType(DEFAULT_LOGO_CONTENT_TYPE)
            .sobre(DEFAULT_SOBRE)
            .permission(DEFAULT_PERMISSION);
        return grupCategory;
    }

    @Before
    public void initTest() {
        grupCategory = createEntity(em);
    }

    @Test
    @Transactional
    public void createGrupCategory() throws Exception {
        int databaseSizeBeforeCreate = grupCategoryRepository.findAll().size();

        // Create the GrupCategory
        restGrupCategoryMockMvc.perform(post("/api/grup-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(grupCategory)))
            .andExpect(status().isCreated());

        // Validate the GrupCategory in the database
        List<GrupCategory> grupCategoryList = grupCategoryRepository.findAll();
        assertThat(grupCategoryList).hasSize(databaseSizeBeforeCreate + 1);
        GrupCategory testGrupCategory = grupCategoryList.get(grupCategoryList.size() - 1);
        assertThat(testGrupCategory.getLogo()).isEqualTo(DEFAULT_LOGO);
        assertThat(testGrupCategory.getLogoContentType()).isEqualTo(DEFAULT_LOGO_CONTENT_TYPE);
        assertThat(testGrupCategory.getSobre()).isEqualTo(DEFAULT_SOBRE);
        assertThat(testGrupCategory.getPermission()).isEqualTo(DEFAULT_PERMISSION);
    }

    @Test
    @Transactional
    public void createGrupCategoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = grupCategoryRepository.findAll().size();

        // Create the GrupCategory with an existing ID
        grupCategory.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGrupCategoryMockMvc.perform(post("/api/grup-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(grupCategory)))
            .andExpect(status().isBadRequest());

        // Validate the GrupCategory in the database
        List<GrupCategory> grupCategoryList = grupCategoryRepository.findAll();
        assertThat(grupCategoryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllGrupCategories() throws Exception {
        // Initialize the database
        grupCategoryRepository.saveAndFlush(grupCategory);

        // Get all the grupCategoryList
        restGrupCategoryMockMvc.perform(get("/api/grup-categories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(grupCategory.getId().intValue())))
            .andExpect(jsonPath("$.[*].logoContentType").value(hasItem(DEFAULT_LOGO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].logo").value(hasItem(Base64Utils.encodeToString(DEFAULT_LOGO))))
            .andExpect(jsonPath("$.[*].sobre").value(hasItem(DEFAULT_SOBRE.toString())))
            .andExpect(jsonPath("$.[*].permission").value(hasItem(DEFAULT_PERMISSION.toString())));
    }
    
    public void getAllGrupCategoriesWithEagerRelationshipsIsEnabled() throws Exception {
        GrupCategoryResource grupCategoryResource = new GrupCategoryResource(grupCategoryServiceMock, grupCategoryQueryService);
        when(grupCategoryServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restGrupCategoryMockMvc = MockMvcBuilders.standaloneSetup(grupCategoryResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restGrupCategoryMockMvc.perform(get("/api/grup-categories?eagerload=true"))
        .andExpect(status().isOk());

        verify(grupCategoryServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    public void getAllGrupCategoriesWithEagerRelationshipsIsNotEnabled() throws Exception {
        GrupCategoryResource grupCategoryResource = new GrupCategoryResource(grupCategoryServiceMock, grupCategoryQueryService);
            when(grupCategoryServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restGrupCategoryMockMvc = MockMvcBuilders.standaloneSetup(grupCategoryResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restGrupCategoryMockMvc.perform(get("/api/grup-categories?eagerload=true"))
        .andExpect(status().isOk());

            verify(grupCategoryServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getGrupCategory() throws Exception {
        // Initialize the database
        grupCategoryRepository.saveAndFlush(grupCategory);

        // Get the grupCategory
        restGrupCategoryMockMvc.perform(get("/api/grup-categories/{id}", grupCategory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(grupCategory.getId().intValue()))
            .andExpect(jsonPath("$.logoContentType").value(DEFAULT_LOGO_CONTENT_TYPE))
            .andExpect(jsonPath("$.logo").value(Base64Utils.encodeToString(DEFAULT_LOGO)))
            .andExpect(jsonPath("$.sobre").value(DEFAULT_SOBRE.toString()))
            .andExpect(jsonPath("$.permission").value(DEFAULT_PERMISSION.toString()));
    }

    @Test
    @Transactional
    public void getAllGrupCategoriesByPermissionIsEqualToSomething() throws Exception {
        // Initialize the database
        grupCategoryRepository.saveAndFlush(grupCategory);

        // Get all the grupCategoryList where permission equals to DEFAULT_PERMISSION
        defaultGrupCategoryShouldBeFound("permission.equals=" + DEFAULT_PERMISSION);

        // Get all the grupCategoryList where permission equals to UPDATED_PERMISSION
        defaultGrupCategoryShouldNotBeFound("permission.equals=" + UPDATED_PERMISSION);
    }

    @Test
    @Transactional
    public void getAllGrupCategoriesByPermissionIsInShouldWork() throws Exception {
        // Initialize the database
        grupCategoryRepository.saveAndFlush(grupCategory);

        // Get all the grupCategoryList where permission in DEFAULT_PERMISSION or UPDATED_PERMISSION
        defaultGrupCategoryShouldBeFound("permission.in=" + DEFAULT_PERMISSION + "," + UPDATED_PERMISSION);

        // Get all the grupCategoryList where permission equals to UPDATED_PERMISSION
        defaultGrupCategoryShouldNotBeFound("permission.in=" + UPDATED_PERMISSION);
    }

    @Test
    @Transactional
    public void getAllGrupCategoriesByPermissionIsNullOrNotNull() throws Exception {
        // Initialize the database
        grupCategoryRepository.saveAndFlush(grupCategory);

        // Get all the grupCategoryList where permission is not null
        defaultGrupCategoryShouldBeFound("permission.specified=true");

        // Get all the grupCategoryList where permission is null
        defaultGrupCategoryShouldNotBeFound("permission.specified=false");
    }

    @Test
    @Transactional
    public void getAllGrupCategoriesByOwnerIsEqualToSomething() throws Exception {
        // Initialize the database
        User owner = UserResourceIntTest.createEntity(em);
        em.persist(owner);
        em.flush();
        grupCategory.setOwner(owner);
        grupCategoryRepository.saveAndFlush(grupCategory);
        Long ownerId = owner.getId();

        // Get all the grupCategoryList where owner equals to ownerId
        defaultGrupCategoryShouldBeFound("ownerId.equals=" + ownerId);

        // Get all the grupCategoryList where owner equals to ownerId + 1
        defaultGrupCategoryShouldNotBeFound("ownerId.equals=" + (ownerId + 1));
    }


    @Test
    @Transactional
    public void getAllGrupCategoriesByCategoryIsEqualToSomething() throws Exception {
        // Initialize the database
        Category category = CategoryResourceIntTest.createEntity(em);
        em.persist(category);
        em.flush();
        grupCategory.addCategory(category);
        grupCategoryRepository.saveAndFlush(grupCategory);
        Long categoryId = category.getId();

        // Get all the grupCategoryList where category equals to categoryId
        defaultGrupCategoryShouldBeFound("categoryId.equals=" + categoryId);

        // Get all the grupCategoryList where category equals to categoryId + 1
        defaultGrupCategoryShouldNotBeFound("categoryId.equals=" + (categoryId + 1));
    }


    @Test
    @Transactional
    public void getAllGrupCategoriesBySharedIsEqualToSomething() throws Exception {
        // Initialize the database
        User shared = UserResourceIntTest.createEntity(em);
        em.persist(shared);
        em.flush();
        grupCategory.addShared(shared);
        grupCategoryRepository.saveAndFlush(grupCategory);
        Long sharedId = shared.getId();

        // Get all the grupCategoryList where shared equals to sharedId
        defaultGrupCategoryShouldBeFound("sharedId.equals=" + sharedId);

        // Get all the grupCategoryList where shared equals to sharedId + 1
        defaultGrupCategoryShouldNotBeFound("sharedId.equals=" + (sharedId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultGrupCategoryShouldBeFound(String filter) throws Exception {
        restGrupCategoryMockMvc.perform(get("/api/grup-categories?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(grupCategory.getId().intValue())))
            .andExpect(jsonPath("$.[*].logoContentType").value(hasItem(DEFAULT_LOGO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].logo").value(hasItem(Base64Utils.encodeToString(DEFAULT_LOGO))))
            .andExpect(jsonPath("$.[*].sobre").value(hasItem(DEFAULT_SOBRE.toString())))
            .andExpect(jsonPath("$.[*].permission").value(hasItem(DEFAULT_PERMISSION.toString())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultGrupCategoryShouldNotBeFound(String filter) throws Exception {
        restGrupCategoryMockMvc.perform(get("/api/grup-categories?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    @Transactional
    public void getNonExistingGrupCategory() throws Exception {
        // Get the grupCategory
        restGrupCategoryMockMvc.perform(get("/api/grup-categories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGrupCategory() throws Exception {
        // Initialize the database
        grupCategoryService.save(grupCategory);

        int databaseSizeBeforeUpdate = grupCategoryRepository.findAll().size();

        // Update the grupCategory
        GrupCategory updatedGrupCategory = grupCategoryRepository.findById(grupCategory.getId()).get();
        // Disconnect from session so that the updates on updatedGrupCategory are not directly saved in db
        em.detach(updatedGrupCategory);
        updatedGrupCategory
            .logo(UPDATED_LOGO)
            .logoContentType(UPDATED_LOGO_CONTENT_TYPE)
            .sobre(UPDATED_SOBRE)
            .permission(UPDATED_PERMISSION);

        restGrupCategoryMockMvc.perform(put("/api/grup-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedGrupCategory)))
            .andExpect(status().isOk());

        // Validate the GrupCategory in the database
        List<GrupCategory> grupCategoryList = grupCategoryRepository.findAll();
        assertThat(grupCategoryList).hasSize(databaseSizeBeforeUpdate);
        GrupCategory testGrupCategory = grupCategoryList.get(grupCategoryList.size() - 1);
        assertThat(testGrupCategory.getLogo()).isEqualTo(UPDATED_LOGO);
        assertThat(testGrupCategory.getLogoContentType()).isEqualTo(UPDATED_LOGO_CONTENT_TYPE);
        assertThat(testGrupCategory.getSobre()).isEqualTo(UPDATED_SOBRE);
        assertThat(testGrupCategory.getPermission()).isEqualTo(UPDATED_PERMISSION);
    }

    @Test
    @Transactional
    public void updateNonExistingGrupCategory() throws Exception {
        int databaseSizeBeforeUpdate = grupCategoryRepository.findAll().size();

        // Create the GrupCategory

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restGrupCategoryMockMvc.perform(put("/api/grup-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(grupCategory)))
            .andExpect(status().isBadRequest());

        // Validate the GrupCategory in the database
        List<GrupCategory> grupCategoryList = grupCategoryRepository.findAll();
        assertThat(grupCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteGrupCategory() throws Exception {
        // Initialize the database
        grupCategoryService.save(grupCategory);

        int databaseSizeBeforeDelete = grupCategoryRepository.findAll().size();

        // Get the grupCategory
        restGrupCategoryMockMvc.perform(delete("/api/grup-categories/{id}", grupCategory.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<GrupCategory> grupCategoryList = grupCategoryRepository.findAll();
        assertThat(grupCategoryList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GrupCategory.class);
        GrupCategory grupCategory1 = new GrupCategory();
        grupCategory1.setId(1L);
        GrupCategory grupCategory2 = new GrupCategory();
        grupCategory2.setId(grupCategory1.getId());
        assertThat(grupCategory1).isEqualTo(grupCategory2);
        grupCategory2.setId(2L);
        assertThat(grupCategory1).isNotEqualTo(grupCategory2);
        grupCategory1.setId(null);
        assertThat(grupCategory1).isNotEqualTo(grupCategory2);
    }
}
