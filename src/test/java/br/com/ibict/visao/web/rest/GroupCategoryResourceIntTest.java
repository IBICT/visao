package br.com.ibict.visao.web.rest;

import br.com.ibict.visao.VisaoApp;

import br.com.ibict.visao.domain.GroupCategory;
import br.com.ibict.visao.domain.User;
import br.com.ibict.visao.domain.Category;
import br.com.ibict.visao.domain.User;
import br.com.ibict.visao.repository.GroupCategoryRepository;
import br.com.ibict.visao.service.GroupCategoryService;
import br.com.ibict.visao.web.rest.errors.ExceptionTranslator;
import br.com.ibict.visao.service.dto.GroupCategoryCriteria;
import br.com.ibict.visao.service.GroupCategoryQueryService;

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

import br.com.ibict.visao.domain.enumeration.TypePermission;
/**
 * Test class for the GroupCategoryResource REST controller.
 *
 * @see GroupCategoryResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = VisaoApp.class)
public class GroupCategoryResourceIntTest {

    private static final String DEFAULT_ICON_CONTENT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_ICON_CONTENT_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_ABOUT = "AAAAAAAAAA";
    private static final String UPDATED_ABOUT = "BBBBBBBBBB";

    private static final TypePermission DEFAULT_PERMISSION = TypePermission.PUBLIC;
    private static final TypePermission UPDATED_PERMISSION = TypePermission.PRIVATE;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final byte[] DEFAULT_ICON_PRESENTATION = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_ICON_PRESENTATION = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_ICON_PRESENTATION_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_ICON_PRESENTATION_CONTENT_TYPE = "image/png";

    @Autowired
    private GroupCategoryRepository groupCategoryRepository;
    @Mock
    private GroupCategoryRepository groupCategoryRepositoryMock;
    
    @Mock
    private GroupCategoryService groupCategoryServiceMock;

    @Autowired
    private GroupCategoryService groupCategoryService;

    @Autowired
    private GroupCategoryQueryService groupCategoryQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restGroupCategoryMockMvc;

    private GroupCategory groupCategory;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final GroupCategoryResource groupCategoryResource = new GroupCategoryResource(groupCategoryService, groupCategoryQueryService);
        this.restGroupCategoryMockMvc = MockMvcBuilders.standaloneSetup(groupCategoryResource)
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
    public static GroupCategory createEntity(EntityManager em) {
        GroupCategory groupCategory = new GroupCategory()
            .about(DEFAULT_ABOUT)
            .permission(DEFAULT_PERMISSION)
            .name(DEFAULT_NAME)
            .iconPresentation(DEFAULT_ICON_PRESENTATION)
            .iconPresentationContentType(DEFAULT_ICON_PRESENTATION_CONTENT_TYPE);
        return groupCategory;
    }

    @Before
    public void initTest() {
        groupCategory = createEntity(em);
    }

    @Test
    @Transactional
    public void createGroupCategory() throws Exception {
        int databaseSizeBeforeCreate = groupCategoryRepository.findAll().size();

        // Create the GroupCategory
        restGroupCategoryMockMvc.perform(post("/api/group-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(groupCategory)))
            .andExpect(status().isCreated());

        // Validate the GroupCategory in the database
        List<GroupCategory> groupCategoryList = groupCategoryRepository.findAll();
        assertThat(groupCategoryList).hasSize(databaseSizeBeforeCreate + 1);
        GroupCategory testGroupCategory = groupCategoryList.get(groupCategoryList.size() - 1);
        assertThat(testGroupCategory.getAbout()).isEqualTo(DEFAULT_ABOUT);
        assertThat(testGroupCategory.getPermission()).isEqualTo(DEFAULT_PERMISSION);
        assertThat(testGroupCategory.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testGroupCategory.getIconPresentation()).isEqualTo(DEFAULT_ICON_PRESENTATION);
        assertThat(testGroupCategory.getIconPresentationContentType()).isEqualTo(DEFAULT_ICON_PRESENTATION_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createGroupCategoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = groupCategoryRepository.findAll().size();

        // Create the GroupCategory with an existing ID
        groupCategory.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGroupCategoryMockMvc.perform(post("/api/group-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(groupCategory)))
            .andExpect(status().isBadRequest());

        // Validate the GroupCategory in the database
        List<GroupCategory> groupCategoryList = groupCategoryRepository.findAll();
        assertThat(groupCategoryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = groupCategoryRepository.findAll().size();
        // set the field null
        groupCategory.setName(null);

        // Create the GroupCategory, which fails.

        restGroupCategoryMockMvc.perform(post("/api/group-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(groupCategory)))
            .andExpect(status().isBadRequest());

        List<GroupCategory> groupCategoryList = groupCategoryRepository.findAll();
        assertThat(groupCategoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllGroupCategories() throws Exception {
        // Initialize the database
        groupCategoryRepository.saveAndFlush(groupCategory);

        // Get all the groupCategoryList
        restGroupCategoryMockMvc.perform(get("/api/group-categories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(groupCategory.getId().intValue())))
            .andExpect(jsonPath("$.[*].iconContentType").value(hasItem(DEFAULT_ICON_CONTENT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].about").value(hasItem(DEFAULT_ABOUT.toString())))
            .andExpect(jsonPath("$.[*].permission").value(hasItem(DEFAULT_PERMISSION.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].iconPresentationContentType").value(hasItem(DEFAULT_ICON_PRESENTATION_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].iconPresentation").value(hasItem(Base64Utils.encodeToString(DEFAULT_ICON_PRESENTATION))));
    }
    
    public void getAllGroupCategoriesWithEagerRelationshipsIsEnabled() throws Exception {
        GroupCategoryResource groupCategoryResource = new GroupCategoryResource(groupCategoryServiceMock, groupCategoryQueryService);
        when(groupCategoryServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restGroupCategoryMockMvc = MockMvcBuilders.standaloneSetup(groupCategoryResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restGroupCategoryMockMvc.perform(get("/api/group-categories?eagerload=true"))
        .andExpect(status().isOk());

        verify(groupCategoryServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    public void getAllGroupCategoriesWithEagerRelationshipsIsNotEnabled() throws Exception {
        GroupCategoryResource groupCategoryResource = new GroupCategoryResource(groupCategoryServiceMock, groupCategoryQueryService);
            when(groupCategoryServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restGroupCategoryMockMvc = MockMvcBuilders.standaloneSetup(groupCategoryResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restGroupCategoryMockMvc.perform(get("/api/group-categories?eagerload=true"))
        .andExpect(status().isOk());

            verify(groupCategoryServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getGroupCategory() throws Exception {
        // Initialize the database
        groupCategoryRepository.saveAndFlush(groupCategory);

        // Get the groupCategory
        restGroupCategoryMockMvc.perform(get("/api/group-categories/{id}", groupCategory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(groupCategory.getId().intValue()))
            .andExpect(jsonPath("$.iconContentType").value(DEFAULT_ICON_CONTENT_TYPE.toString()))
            .andExpect(jsonPath("$.about").value(DEFAULT_ABOUT.toString()))
            .andExpect(jsonPath("$.permission").value(DEFAULT_PERMISSION.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.iconPresentationContentType").value(DEFAULT_ICON_PRESENTATION_CONTENT_TYPE))
            .andExpect(jsonPath("$.iconPresentation").value(Base64Utils.encodeToString(DEFAULT_ICON_PRESENTATION)));
    }

    @Test
    @Transactional
    public void getAllGroupCategoriesByIconContentTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        groupCategoryRepository.saveAndFlush(groupCategory);

        // Get all the groupCategoryList where iconContentType equals to DEFAULT_ICON_CONTENT_TYPE
        defaultGroupCategoryShouldBeFound("iconContentType.equals=" + DEFAULT_ICON_CONTENT_TYPE);

        // Get all the groupCategoryList where iconContentType equals to UPDATED_ICON_CONTENT_TYPE
        defaultGroupCategoryShouldNotBeFound("iconContentType.equals=" + UPDATED_ICON_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void getAllGroupCategoriesByIconContentTypeIsInShouldWork() throws Exception {
        // Initialize the database
        groupCategoryRepository.saveAndFlush(groupCategory);

        // Get all the groupCategoryList where iconContentType in DEFAULT_ICON_CONTENT_TYPE or UPDATED_ICON_CONTENT_TYPE
        defaultGroupCategoryShouldBeFound("iconContentType.in=" + DEFAULT_ICON_CONTENT_TYPE + "," + UPDATED_ICON_CONTENT_TYPE);

        // Get all the groupCategoryList where iconContentType equals to UPDATED_ICON_CONTENT_TYPE
        defaultGroupCategoryShouldNotBeFound("iconContentType.in=" + UPDATED_ICON_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void getAllGroupCategoriesByIconContentTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        groupCategoryRepository.saveAndFlush(groupCategory);

        // Get all the groupCategoryList where iconContentType is not null
        defaultGroupCategoryShouldBeFound("iconContentType.specified=true");

        // Get all the groupCategoryList where iconContentType is null
        defaultGroupCategoryShouldNotBeFound("iconContentType.specified=false");
    }

    @Test
    @Transactional
    public void getAllGroupCategoriesByAboutIsEqualToSomething() throws Exception {
        // Initialize the database
        groupCategoryRepository.saveAndFlush(groupCategory);

        // Get all the groupCategoryList where about equals to DEFAULT_ABOUT
        defaultGroupCategoryShouldBeFound("about.equals=" + DEFAULT_ABOUT);

        // Get all the groupCategoryList where about equals to UPDATED_ABOUT
        defaultGroupCategoryShouldNotBeFound("about.equals=" + UPDATED_ABOUT);
    }

    @Test
    @Transactional
    public void getAllGroupCategoriesByAboutIsInShouldWork() throws Exception {
        // Initialize the database
        groupCategoryRepository.saveAndFlush(groupCategory);

        // Get all the groupCategoryList where about in DEFAULT_ABOUT or UPDATED_ABOUT
        defaultGroupCategoryShouldBeFound("about.in=" + DEFAULT_ABOUT + "," + UPDATED_ABOUT);

        // Get all the groupCategoryList where about equals to UPDATED_ABOUT
        defaultGroupCategoryShouldNotBeFound("about.in=" + UPDATED_ABOUT);
    }

    @Test
    @Transactional
    public void getAllGroupCategoriesByAboutIsNullOrNotNull() throws Exception {
        // Initialize the database
        groupCategoryRepository.saveAndFlush(groupCategory);

        // Get all the groupCategoryList where about is not null
        defaultGroupCategoryShouldBeFound("about.specified=true");

        // Get all the groupCategoryList where about is null
        defaultGroupCategoryShouldNotBeFound("about.specified=false");
    }

    @Test
    @Transactional
    public void getAllGroupCategoriesByPermissionIsEqualToSomething() throws Exception {
        // Initialize the database
        groupCategoryRepository.saveAndFlush(groupCategory);

        // Get all the groupCategoryList where permission equals to DEFAULT_PERMISSION
        defaultGroupCategoryShouldBeFound("permission.equals=" + DEFAULT_PERMISSION);

        // Get all the groupCategoryList where permission equals to UPDATED_PERMISSION
        defaultGroupCategoryShouldNotBeFound("permission.equals=" + UPDATED_PERMISSION);
    }

    @Test
    @Transactional
    public void getAllGroupCategoriesByPermissionIsInShouldWork() throws Exception {
        // Initialize the database
        groupCategoryRepository.saveAndFlush(groupCategory);

        // Get all the groupCategoryList where permission in DEFAULT_PERMISSION or UPDATED_PERMISSION
        defaultGroupCategoryShouldBeFound("permission.in=" + DEFAULT_PERMISSION + "," + UPDATED_PERMISSION);

        // Get all the groupCategoryList where permission equals to UPDATED_PERMISSION
        defaultGroupCategoryShouldNotBeFound("permission.in=" + UPDATED_PERMISSION);
    }

    @Test
    @Transactional
    public void getAllGroupCategoriesByPermissionIsNullOrNotNull() throws Exception {
        // Initialize the database
        groupCategoryRepository.saveAndFlush(groupCategory);

        // Get all the groupCategoryList where permission is not null
        defaultGroupCategoryShouldBeFound("permission.specified=true");

        // Get all the groupCategoryList where permission is null
        defaultGroupCategoryShouldNotBeFound("permission.specified=false");
    }

    @Test
    @Transactional
    public void getAllGroupCategoriesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        groupCategoryRepository.saveAndFlush(groupCategory);

        // Get all the groupCategoryList where name equals to DEFAULT_NAME
        defaultGroupCategoryShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the groupCategoryList where name equals to UPDATED_NAME
        defaultGroupCategoryShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllGroupCategoriesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        groupCategoryRepository.saveAndFlush(groupCategory);

        // Get all the groupCategoryList where name in DEFAULT_NAME or UPDATED_NAME
        defaultGroupCategoryShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the groupCategoryList where name equals to UPDATED_NAME
        defaultGroupCategoryShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllGroupCategoriesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        groupCategoryRepository.saveAndFlush(groupCategory);

        // Get all the groupCategoryList where name is not null
        defaultGroupCategoryShouldBeFound("name.specified=true");

        // Get all the groupCategoryList where name is null
        defaultGroupCategoryShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllGroupCategoriesByOwnerIsEqualToSomething() throws Exception {
        // Initialize the database
        User owner = UserResourceIntTest.createEntity(em);
        em.persist(owner);
        em.flush();
        groupCategory.setOwner(owner);
        groupCategoryRepository.saveAndFlush(groupCategory);
        Long ownerId = owner.getId();

        // Get all the groupCategoryList where owner equals to ownerId
        defaultGroupCategoryShouldBeFound("ownerId.equals=" + ownerId);

        // Get all the groupCategoryList where owner equals to ownerId + 1
        defaultGroupCategoryShouldNotBeFound("ownerId.equals=" + (ownerId + 1));
    }


    @Test
    @Transactional
    public void getAllGroupCategoriesByCategoriesIsEqualToSomething() throws Exception {
        // Initialize the database
        Category categories = CategoryResourceIntTest.createEntity(em);
        em.persist(categories);
        em.flush();
        groupCategory.addCategories(categories);
        groupCategoryRepository.saveAndFlush(groupCategory);
        Long categoriesId = categories.getId();

        // Get all the groupCategoryList where categories equals to categoriesId
        defaultGroupCategoryShouldBeFound("categoriesId.equals=" + categoriesId);

        // Get all the groupCategoryList where categories equals to categoriesId + 1
        defaultGroupCategoryShouldNotBeFound("categoriesId.equals=" + (categoriesId + 1));
    }


    @Test
    @Transactional
    public void getAllGroupCategoriesBySharedsIsEqualToSomething() throws Exception {
        // Initialize the database
        User shareds = UserResourceIntTest.createEntity(em);
        em.persist(shareds);
        em.flush();
        groupCategory.addShareds(shareds);
        groupCategoryRepository.saveAndFlush(groupCategory);
        Long sharedsId = shareds.getId();

        // Get all the groupCategoryList where shareds equals to sharedsId
        defaultGroupCategoryShouldBeFound("sharedsId.equals=" + sharedsId);

        // Get all the groupCategoryList where shareds equals to sharedsId + 1
        defaultGroupCategoryShouldNotBeFound("sharedsId.equals=" + (sharedsId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultGroupCategoryShouldBeFound(String filter) throws Exception {
        restGroupCategoryMockMvc.perform(get("/api/group-categories?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(groupCategory.getId().intValue())))
            .andExpect(jsonPath("$.[*].iconContentType").value(hasItem(DEFAULT_ICON_CONTENT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].about").value(hasItem(DEFAULT_ABOUT.toString())))
            .andExpect(jsonPath("$.[*].permission").value(hasItem(DEFAULT_PERMISSION.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].iconPresentationContentType").value(hasItem(DEFAULT_ICON_PRESENTATION_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].iconPresentation").value(hasItem(Base64Utils.encodeToString(DEFAULT_ICON_PRESENTATION))));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultGroupCategoryShouldNotBeFound(String filter) throws Exception {
        restGroupCategoryMockMvc.perform(get("/api/group-categories?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    @Transactional
    public void getNonExistingGroupCategory() throws Exception {
        // Get the groupCategory
        restGroupCategoryMockMvc.perform(get("/api/group-categories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGroupCategory() throws Exception {
        // Initialize the database
        groupCategoryService.save(groupCategory);

        int databaseSizeBeforeUpdate = groupCategoryRepository.findAll().size();

        // Update the groupCategory
        GroupCategory updatedGroupCategory = groupCategoryRepository.findById(groupCategory.getId()).get();
        // Disconnect from session so that the updates on updatedGroupCategory are not directly saved in db
        em.detach(updatedGroupCategory);
        updatedGroupCategory
            .about(UPDATED_ABOUT)
            .permission(UPDATED_PERMISSION)
            .name(UPDATED_NAME)
            .iconPresentation(UPDATED_ICON_PRESENTATION)
            .iconPresentationContentType(UPDATED_ICON_PRESENTATION_CONTENT_TYPE);

        restGroupCategoryMockMvc.perform(put("/api/group-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedGroupCategory)))
            .andExpect(status().isOk());

        // Validate the GroupCategory in the database
        List<GroupCategory> groupCategoryList = groupCategoryRepository.findAll();
        assertThat(groupCategoryList).hasSize(databaseSizeBeforeUpdate);
        GroupCategory testGroupCategory = groupCategoryList.get(groupCategoryList.size() - 1);
        assertThat(testGroupCategory.getAbout()).isEqualTo(UPDATED_ABOUT);
        assertThat(testGroupCategory.getPermission()).isEqualTo(UPDATED_PERMISSION);
        assertThat(testGroupCategory.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testGroupCategory.getIconPresentation()).isEqualTo(UPDATED_ICON_PRESENTATION);
        assertThat(testGroupCategory.getIconPresentationContentType()).isEqualTo(UPDATED_ICON_PRESENTATION_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingGroupCategory() throws Exception {
        int databaseSizeBeforeUpdate = groupCategoryRepository.findAll().size();

        // Create the GroupCategory

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restGroupCategoryMockMvc.perform(put("/api/group-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(groupCategory)))
            .andExpect(status().isBadRequest());

        // Validate the GroupCategory in the database
        List<GroupCategory> groupCategoryList = groupCategoryRepository.findAll();
        assertThat(groupCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteGroupCategory() throws Exception {
        // Initialize the database
        groupCategoryService.save(groupCategory);

        int databaseSizeBeforeDelete = groupCategoryRepository.findAll().size();

        // Get the groupCategory
        restGroupCategoryMockMvc.perform(delete("/api/group-categories/{id}", groupCategory.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<GroupCategory> groupCategoryList = groupCategoryRepository.findAll();
        assertThat(groupCategoryList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GroupCategory.class);
        GroupCategory groupCategory1 = new GroupCategory();
        groupCategory1.setId(1L);
        GroupCategory groupCategory2 = new GroupCategory();
        groupCategory2.setId(groupCategory1.getId());
        assertThat(groupCategory1).isEqualTo(groupCategory2);
        groupCategory2.setId(2L);
        assertThat(groupCategory1).isNotEqualTo(groupCategory2);
        groupCategory1.setId(null);
        assertThat(groupCategory1).isNotEqualTo(groupCategory2);
    }
}
