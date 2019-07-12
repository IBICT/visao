package br.com.ibict.web.rest;

import br.com.ibict.VisaoApp;

import br.com.ibict.domain.GroupLayer;
import br.com.ibict.domain.MetaDado;
import br.com.ibict.domain.User;
import br.com.ibict.domain.Category;
import br.com.ibict.repository.GroupLayerRepository;
import br.com.ibict.service.GroupLayerService;
import br.com.ibict.web.rest.errors.ExceptionTranslator;
import br.com.ibict.service.dto.GroupLayerCriteria;
import br.com.ibict.service.GroupLayerQueryService;

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

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;


import static br.com.ibict.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the GroupLayerResource REST controller.
 *
 * @see GroupLayerResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = VisaoApp.class)
public class GroupLayerResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    private static final String DEFAULT_KEY_WORD = "AAAAAAAAAA";
    private static final String UPDATED_KEY_WORD = "BBBBBBBBBB";

    @Autowired
    private GroupLayerRepository groupLayerRepository;
    @Mock
    private GroupLayerRepository groupLayerRepositoryMock;
    
    @Mock
    private GroupLayerService groupLayerServiceMock;

    @Autowired
    private GroupLayerService groupLayerService;

    @Autowired
    private GroupLayerQueryService groupLayerQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restGroupLayerMockMvc;

    private GroupLayer groupLayer;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final GroupLayerResource groupLayerResource = new GroupLayerResource(groupLayerService, groupLayerQueryService);
        this.restGroupLayerMockMvc = MockMvcBuilders.standaloneSetup(groupLayerResource)
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
    public static GroupLayer createEntity(EntityManager em) {
        GroupLayer groupLayer = new GroupLayer()
            .name(DEFAULT_NAME)
            .active(DEFAULT_ACTIVE)
            .keyWord(DEFAULT_KEY_WORD);
        return groupLayer;
    }

    @Before
    public void initTest() {
        groupLayer = createEntity(em);
    }

    @Test
    @Transactional
    public void createGroupLayer() throws Exception {
        int databaseSizeBeforeCreate = groupLayerRepository.findAll().size();

        // Create the GroupLayer
        restGroupLayerMockMvc.perform(post("/api/group-layers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(groupLayer)))
            .andExpect(status().isCreated());

        // Validate the GroupLayer in the database
        List<GroupLayer> groupLayerList = groupLayerRepository.findAll();
        assertThat(groupLayerList).hasSize(databaseSizeBeforeCreate + 1);
        GroupLayer testGroupLayer = groupLayerList.get(groupLayerList.size() - 1);
        assertThat(testGroupLayer.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testGroupLayer.isActive()).isEqualTo(DEFAULT_ACTIVE);
        assertThat(testGroupLayer.getKeyWord()).isEqualTo(DEFAULT_KEY_WORD);
    }

    @Test
    @Transactional
    public void createGroupLayerWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = groupLayerRepository.findAll().size();

        // Create the GroupLayer with an existing ID
        groupLayer.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGroupLayerMockMvc.perform(post("/api/group-layers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(groupLayer)))
            .andExpect(status().isBadRequest());

        // Validate the GroupLayer in the database
        List<GroupLayer> groupLayerList = groupLayerRepository.findAll();
        assertThat(groupLayerList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = groupLayerRepository.findAll().size();
        // set the field null
        groupLayer.setName(null);

        // Create the GroupLayer, which fails.

        restGroupLayerMockMvc.perform(post("/api/group-layers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(groupLayer)))
            .andExpect(status().isBadRequest());

        List<GroupLayer> groupLayerList = groupLayerRepository.findAll();
        assertThat(groupLayerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActiveIsRequired() throws Exception {
        int databaseSizeBeforeTest = groupLayerRepository.findAll().size();
        // set the field null
        groupLayer.setActive(null);

        // Create the GroupLayer, which fails.

        restGroupLayerMockMvc.perform(post("/api/group-layers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(groupLayer)))
            .andExpect(status().isBadRequest());

        List<GroupLayer> groupLayerList = groupLayerRepository.findAll();
        assertThat(groupLayerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllGroupLayers() throws Exception {
        // Initialize the database
        groupLayerRepository.saveAndFlush(groupLayer);

        // Get all the groupLayerList
        restGroupLayerMockMvc.perform(get("/api/group-layers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(groupLayer.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].keyWord").value(hasItem(DEFAULT_KEY_WORD.toString())));
    }
    
    public void getAllGroupLayersWithEagerRelationshipsIsEnabled() throws Exception {
        GroupLayerResource groupLayerResource = new GroupLayerResource(groupLayerServiceMock, groupLayerQueryService);
        when(groupLayerServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restGroupLayerMockMvc = MockMvcBuilders.standaloneSetup(groupLayerResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restGroupLayerMockMvc.perform(get("/api/group-layers?eagerload=true"))
        .andExpect(status().isOk());

        verify(groupLayerServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    public void getAllGroupLayersWithEagerRelationshipsIsNotEnabled() throws Exception {
        GroupLayerResource groupLayerResource = new GroupLayerResource(groupLayerServiceMock, groupLayerQueryService);
            when(groupLayerServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restGroupLayerMockMvc = MockMvcBuilders.standaloneSetup(groupLayerResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restGroupLayerMockMvc.perform(get("/api/group-layers?eagerload=true"))
        .andExpect(status().isOk());

            verify(groupLayerServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getGroupLayer() throws Exception {
        // Initialize the database
        groupLayerRepository.saveAndFlush(groupLayer);

        // Get the groupLayer
        restGroupLayerMockMvc.perform(get("/api/group-layers/{id}", groupLayer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(groupLayer.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.keyWord").value(DEFAULT_KEY_WORD.toString()));
    }

    @Test
    @Transactional
    public void getAllGroupLayersByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        groupLayerRepository.saveAndFlush(groupLayer);

        // Get all the groupLayerList where name equals to DEFAULT_NAME
        defaultGroupLayerShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the groupLayerList where name equals to UPDATED_NAME
        defaultGroupLayerShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllGroupLayersByNameIsInShouldWork() throws Exception {
        // Initialize the database
        groupLayerRepository.saveAndFlush(groupLayer);

        // Get all the groupLayerList where name in DEFAULT_NAME or UPDATED_NAME
        defaultGroupLayerShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the groupLayerList where name equals to UPDATED_NAME
        defaultGroupLayerShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllGroupLayersByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        groupLayerRepository.saveAndFlush(groupLayer);

        // Get all the groupLayerList where name is not null
        defaultGroupLayerShouldBeFound("name.specified=true");

        // Get all the groupLayerList where name is null
        defaultGroupLayerShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllGroupLayersByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        groupLayerRepository.saveAndFlush(groupLayer);

        // Get all the groupLayerList where active equals to DEFAULT_ACTIVE
        defaultGroupLayerShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the groupLayerList where active equals to UPDATED_ACTIVE
        defaultGroupLayerShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllGroupLayersByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        groupLayerRepository.saveAndFlush(groupLayer);

        // Get all the groupLayerList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultGroupLayerShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the groupLayerList where active equals to UPDATED_ACTIVE
        defaultGroupLayerShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllGroupLayersByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        groupLayerRepository.saveAndFlush(groupLayer);

        // Get all the groupLayerList where active is not null
        defaultGroupLayerShouldBeFound("active.specified=true");

        // Get all the groupLayerList where active is null
        defaultGroupLayerShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllGroupLayersByKeyWordIsEqualToSomething() throws Exception {
        // Initialize the database
        groupLayerRepository.saveAndFlush(groupLayer);

        // Get all the groupLayerList where keyWord equals to DEFAULT_KEY_WORD
        defaultGroupLayerShouldBeFound("keyWord.equals=" + DEFAULT_KEY_WORD);

        // Get all the groupLayerList where keyWord equals to UPDATED_KEY_WORD
        defaultGroupLayerShouldNotBeFound("keyWord.equals=" + UPDATED_KEY_WORD);
    }

    @Test
    @Transactional
    public void getAllGroupLayersByKeyWordIsInShouldWork() throws Exception {
        // Initialize the database
        groupLayerRepository.saveAndFlush(groupLayer);

        // Get all the groupLayerList where keyWord in DEFAULT_KEY_WORD or UPDATED_KEY_WORD
        defaultGroupLayerShouldBeFound("keyWord.in=" + DEFAULT_KEY_WORD + "," + UPDATED_KEY_WORD);

        // Get all the groupLayerList where keyWord equals to UPDATED_KEY_WORD
        defaultGroupLayerShouldNotBeFound("keyWord.in=" + UPDATED_KEY_WORD);
    }

    @Test
    @Transactional
    public void getAllGroupLayersByKeyWordIsNullOrNotNull() throws Exception {
        // Initialize the database
        groupLayerRepository.saveAndFlush(groupLayer);

        // Get all the groupLayerList where keyWord is not null
        defaultGroupLayerShouldBeFound("keyWord.specified=true");

        // Get all the groupLayerList where keyWord is null
        defaultGroupLayerShouldNotBeFound("keyWord.specified=false");
    }

    @Test
    @Transactional
    public void getAllGroupLayersByMetaDadoIsEqualToSomething() throws Exception {
        // Initialize the database
        MetaDado metaDado = MetaDadoResourceIntTest.createEntity(em);
        em.persist(metaDado);
        em.flush();
        groupLayer.addMetaDado(metaDado);
        groupLayerRepository.saveAndFlush(groupLayer);
        Long metaDadoId = metaDado.getId();

        // Get all the groupLayerList where metaDado equals to metaDadoId
        defaultGroupLayerShouldBeFound("metaDadoId.equals=" + metaDadoId);

        // Get all the groupLayerList where metaDado equals to metaDadoId + 1
        defaultGroupLayerShouldNotBeFound("metaDadoId.equals=" + (metaDadoId + 1));
    }


    @Test
    @Transactional
    public void getAllGroupLayersByOwnerIsEqualToSomething() throws Exception {
        // Initialize the database
        User owner = UserResourceIntTest.createEntity(em);
        em.persist(owner);
        em.flush();
        groupLayer.setOwner(owner);
        groupLayerRepository.saveAndFlush(groupLayer);
        Long ownerId = owner.getId();

        // Get all the groupLayerList where owner equals to ownerId
        defaultGroupLayerShouldBeFound("ownerId.equals=" + ownerId);

        // Get all the groupLayerList where owner equals to ownerId + 1
        defaultGroupLayerShouldNotBeFound("ownerId.equals=" + (ownerId + 1));
    }


    @Test
    @Transactional
    public void getAllGroupLayersByCategoryIsEqualToSomething() throws Exception {
        // Initialize the database
        Category category = CategoryResourceIntTest.createEntity(em);
        em.persist(category);
        em.flush();
        groupLayer.addCategory(category);
        groupLayerRepository.saveAndFlush(groupLayer);
        Long categoryId = category.getId();

        // Get all the groupLayerList where category equals to categoryId
        defaultGroupLayerShouldBeFound("categoryId.equals=" + categoryId);

        // Get all the groupLayerList where category equals to categoryId + 1
        defaultGroupLayerShouldNotBeFound("categoryId.equals=" + (categoryId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultGroupLayerShouldBeFound(String filter) throws Exception {
        restGroupLayerMockMvc.perform(get("/api/group-layers?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(groupLayer.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].keyWord").value(hasItem(DEFAULT_KEY_WORD.toString())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultGroupLayerShouldNotBeFound(String filter) throws Exception {
        restGroupLayerMockMvc.perform(get("/api/group-layers?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    @Transactional
    public void getNonExistingGroupLayer() throws Exception {
        // Get the groupLayer
        restGroupLayerMockMvc.perform(get("/api/group-layers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGroupLayer() throws Exception {
        // Initialize the database
        groupLayerService.save(groupLayer);

        int databaseSizeBeforeUpdate = groupLayerRepository.findAll().size();

        // Update the groupLayer
        GroupLayer updatedGroupLayer = groupLayerRepository.findById(groupLayer.getId()).get();
        // Disconnect from session so that the updates on updatedGroupLayer are not directly saved in db
        em.detach(updatedGroupLayer);
        updatedGroupLayer
            .name(UPDATED_NAME)
            .active(UPDATED_ACTIVE)
            .keyWord(UPDATED_KEY_WORD);

        restGroupLayerMockMvc.perform(put("/api/group-layers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedGroupLayer)))
            .andExpect(status().isOk());

        // Validate the GroupLayer in the database
        List<GroupLayer> groupLayerList = groupLayerRepository.findAll();
        assertThat(groupLayerList).hasSize(databaseSizeBeforeUpdate);
        GroupLayer testGroupLayer = groupLayerList.get(groupLayerList.size() - 1);
        assertThat(testGroupLayer.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testGroupLayer.isActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testGroupLayer.getKeyWord()).isEqualTo(UPDATED_KEY_WORD);
    }

    @Test
    @Transactional
    public void updateNonExistingGroupLayer() throws Exception {
        int databaseSizeBeforeUpdate = groupLayerRepository.findAll().size();

        // Create the GroupLayer

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restGroupLayerMockMvc.perform(put("/api/group-layers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(groupLayer)))
            .andExpect(status().isBadRequest());

        // Validate the GroupLayer in the database
        List<GroupLayer> groupLayerList = groupLayerRepository.findAll();
        assertThat(groupLayerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteGroupLayer() throws Exception {
        // Initialize the database
        groupLayerService.save(groupLayer);

        int databaseSizeBeforeDelete = groupLayerRepository.findAll().size();

        // Get the groupLayer
        restGroupLayerMockMvc.perform(delete("/api/group-layers/{id}", groupLayer.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<GroupLayer> groupLayerList = groupLayerRepository.findAll();
        assertThat(groupLayerList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GroupLayer.class);
        GroupLayer groupLayer1 = new GroupLayer();
        groupLayer1.setId(1L);
        GroupLayer groupLayer2 = new GroupLayer();
        groupLayer2.setId(groupLayer1.getId());
        assertThat(groupLayer1).isEqualTo(groupLayer2);
        groupLayer2.setId(2L);
        assertThat(groupLayer1).isNotEqualTo(groupLayer2);
        groupLayer1.setId(null);
        assertThat(groupLayer1).isNotEqualTo(groupLayer2);
    }
}
