package br.com.ibict.web.rest;

import br.com.ibict.VisaoApp;

import br.com.ibict.domain.GrupIndicator;
import br.com.ibict.domain.MetaDado;
import br.com.ibict.domain.User;
import br.com.ibict.domain.TypePresentation;
import br.com.ibict.domain.Category;
import br.com.ibict.repository.GrupIndicatorRepository;
import br.com.ibict.service.GrupIndicatorService;
import br.com.ibict.web.rest.errors.ExceptionTranslator;
import br.com.ibict.service.dto.GrupIndicatorCriteria;
import br.com.ibict.service.GrupIndicatorQueryService;

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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;


import static br.com.ibict.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the GrupIndicatorResource REST controller.
 *
 * @see GrupIndicatorResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = VisaoApp.class)
public class GrupIndicatorResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_KEY_WORD = "AAAAAAAAAA";
    private static final String UPDATED_KEY_WORD = "BBBBBBBBBB";

    private static final Instant DEFAULT_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_SOURCE = "AAAAAAAAAA";
    private static final String UPDATED_SOURCE = "BBBBBBBBBB";

    private static final Instant DEFAULT_DATE_CHANGE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_CHANGE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_NOTE = "AAAAAAAAAA";
    private static final String UPDATED_NOTE = "BBBBBBBBBB";

    @Autowired
    private GrupIndicatorRepository grupIndicatorRepository;
    @Mock
    private GrupIndicatorRepository grupIndicatorRepositoryMock;
    
    @Mock
    private GrupIndicatorService grupIndicatorServiceMock;

    @Autowired
    private GrupIndicatorService grupIndicatorService;

    @Autowired
    private GrupIndicatorQueryService grupIndicatorQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restGrupIndicatorMockMvc;

    private GrupIndicator grupIndicator;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final GrupIndicatorResource grupIndicatorResource = new GrupIndicatorResource(grupIndicatorService, grupIndicatorQueryService);
        this.restGrupIndicatorMockMvc = MockMvcBuilders.standaloneSetup(grupIndicatorResource)
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
    public static GrupIndicator createEntity(EntityManager em) {
        GrupIndicator grupIndicator = new GrupIndicator()
            .name(DEFAULT_NAME)
            .active(DEFAULT_ACTIVE)
            .description(DEFAULT_DESCRIPTION)
            .keyWord(DEFAULT_KEY_WORD)
            .date(DEFAULT_DATE)
            .source(DEFAULT_SOURCE)
            .dateChange(DEFAULT_DATE_CHANGE)
            .note(DEFAULT_NOTE);
        return grupIndicator;
    }

    @Before
    public void initTest() {
        grupIndicator = createEntity(em);
    }

    @Test
    @Transactional
    public void createGrupIndicator() throws Exception {
        int databaseSizeBeforeCreate = grupIndicatorRepository.findAll().size();

        // Create the GrupIndicator
        restGrupIndicatorMockMvc.perform(post("/api/grup-indicators")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(grupIndicator)))
            .andExpect(status().isCreated());

        // Validate the GrupIndicator in the database
        List<GrupIndicator> grupIndicatorList = grupIndicatorRepository.findAll();
        assertThat(grupIndicatorList).hasSize(databaseSizeBeforeCreate + 1);
        GrupIndicator testGrupIndicator = grupIndicatorList.get(grupIndicatorList.size() - 1);
        assertThat(testGrupIndicator.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testGrupIndicator.isActive()).isEqualTo(DEFAULT_ACTIVE);
        assertThat(testGrupIndicator.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testGrupIndicator.getKeyWord()).isEqualTo(DEFAULT_KEY_WORD);
        assertThat(testGrupIndicator.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testGrupIndicator.getSource()).isEqualTo(DEFAULT_SOURCE);
        assertThat(testGrupIndicator.getDateChange()).isEqualTo(DEFAULT_DATE_CHANGE);
        assertThat(testGrupIndicator.getNote()).isEqualTo(DEFAULT_NOTE);
    }

    @Test
    @Transactional
    public void createGrupIndicatorWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = grupIndicatorRepository.findAll().size();

        // Create the GrupIndicator with an existing ID
        grupIndicator.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGrupIndicatorMockMvc.perform(post("/api/grup-indicators")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(grupIndicator)))
            .andExpect(status().isBadRequest());

        // Validate the GrupIndicator in the database
        List<GrupIndicator> grupIndicatorList = grupIndicatorRepository.findAll();
        assertThat(grupIndicatorList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = grupIndicatorRepository.findAll().size();
        // set the field null
        grupIndicator.setName(null);

        // Create the GrupIndicator, which fails.

        restGrupIndicatorMockMvc.perform(post("/api/grup-indicators")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(grupIndicator)))
            .andExpect(status().isBadRequest());

        List<GrupIndicator> grupIndicatorList = grupIndicatorRepository.findAll();
        assertThat(grupIndicatorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActiveIsRequired() throws Exception {
        int databaseSizeBeforeTest = grupIndicatorRepository.findAll().size();
        // set the field null
        grupIndicator.setActive(null);

        // Create the GrupIndicator, which fails.

        restGrupIndicatorMockMvc.perform(post("/api/grup-indicators")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(grupIndicator)))
            .andExpect(status().isBadRequest());

        List<GrupIndicator> grupIndicatorList = grupIndicatorRepository.findAll();
        assertThat(grupIndicatorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllGrupIndicators() throws Exception {
        // Initialize the database
        grupIndicatorRepository.saveAndFlush(grupIndicator);

        // Get all the grupIndicatorList
        restGrupIndicatorMockMvc.perform(get("/api/grup-indicators?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(grupIndicator.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].keyWord").value(hasItem(DEFAULT_KEY_WORD.toString())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].source").value(hasItem(DEFAULT_SOURCE.toString())))
            .andExpect(jsonPath("$.[*].dateChange").value(hasItem(DEFAULT_DATE_CHANGE.toString())))
            .andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE.toString())));
    }
    
    public void getAllGrupIndicatorsWithEagerRelationshipsIsEnabled() throws Exception {
        GrupIndicatorResource grupIndicatorResource = new GrupIndicatorResource(grupIndicatorServiceMock, grupIndicatorQueryService);
        when(grupIndicatorServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restGrupIndicatorMockMvc = MockMvcBuilders.standaloneSetup(grupIndicatorResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restGrupIndicatorMockMvc.perform(get("/api/grup-indicators?eagerload=true"))
        .andExpect(status().isOk());

        verify(grupIndicatorServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    public void getAllGrupIndicatorsWithEagerRelationshipsIsNotEnabled() throws Exception {
        GrupIndicatorResource grupIndicatorResource = new GrupIndicatorResource(grupIndicatorServiceMock, grupIndicatorQueryService);
            when(grupIndicatorServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restGrupIndicatorMockMvc = MockMvcBuilders.standaloneSetup(grupIndicatorResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restGrupIndicatorMockMvc.perform(get("/api/grup-indicators?eagerload=true"))
        .andExpect(status().isOk());

            verify(grupIndicatorServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getGrupIndicator() throws Exception {
        // Initialize the database
        grupIndicatorRepository.saveAndFlush(grupIndicator);

        // Get the grupIndicator
        restGrupIndicatorMockMvc.perform(get("/api/grup-indicators/{id}", grupIndicator.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(grupIndicator.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.keyWord").value(DEFAULT_KEY_WORD.toString()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.source").value(DEFAULT_SOURCE.toString()))
            .andExpect(jsonPath("$.dateChange").value(DEFAULT_DATE_CHANGE.toString()))
            .andExpect(jsonPath("$.note").value(DEFAULT_NOTE.toString()));
    }

    @Test
    @Transactional
    public void getAllGrupIndicatorsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        grupIndicatorRepository.saveAndFlush(grupIndicator);

        // Get all the grupIndicatorList where name equals to DEFAULT_NAME
        defaultGrupIndicatorShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the grupIndicatorList where name equals to UPDATED_NAME
        defaultGrupIndicatorShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllGrupIndicatorsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        grupIndicatorRepository.saveAndFlush(grupIndicator);

        // Get all the grupIndicatorList where name in DEFAULT_NAME or UPDATED_NAME
        defaultGrupIndicatorShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the grupIndicatorList where name equals to UPDATED_NAME
        defaultGrupIndicatorShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllGrupIndicatorsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        grupIndicatorRepository.saveAndFlush(grupIndicator);

        // Get all the grupIndicatorList where name is not null
        defaultGrupIndicatorShouldBeFound("name.specified=true");

        // Get all the grupIndicatorList where name is null
        defaultGrupIndicatorShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllGrupIndicatorsByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        grupIndicatorRepository.saveAndFlush(grupIndicator);

        // Get all the grupIndicatorList where active equals to DEFAULT_ACTIVE
        defaultGrupIndicatorShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the grupIndicatorList where active equals to UPDATED_ACTIVE
        defaultGrupIndicatorShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllGrupIndicatorsByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        grupIndicatorRepository.saveAndFlush(grupIndicator);

        // Get all the grupIndicatorList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultGrupIndicatorShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the grupIndicatorList where active equals to UPDATED_ACTIVE
        defaultGrupIndicatorShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllGrupIndicatorsByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        grupIndicatorRepository.saveAndFlush(grupIndicator);

        // Get all the grupIndicatorList where active is not null
        defaultGrupIndicatorShouldBeFound("active.specified=true");

        // Get all the grupIndicatorList where active is null
        defaultGrupIndicatorShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllGrupIndicatorsByKeyWordIsEqualToSomething() throws Exception {
        // Initialize the database
        grupIndicatorRepository.saveAndFlush(grupIndicator);

        // Get all the grupIndicatorList where keyWord equals to DEFAULT_KEY_WORD
        defaultGrupIndicatorShouldBeFound("keyWord.equals=" + DEFAULT_KEY_WORD);

        // Get all the grupIndicatorList where keyWord equals to UPDATED_KEY_WORD
        defaultGrupIndicatorShouldNotBeFound("keyWord.equals=" + UPDATED_KEY_WORD);
    }

    @Test
    @Transactional
    public void getAllGrupIndicatorsByKeyWordIsInShouldWork() throws Exception {
        // Initialize the database
        grupIndicatorRepository.saveAndFlush(grupIndicator);

        // Get all the grupIndicatorList where keyWord in DEFAULT_KEY_WORD or UPDATED_KEY_WORD
        defaultGrupIndicatorShouldBeFound("keyWord.in=" + DEFAULT_KEY_WORD + "," + UPDATED_KEY_WORD);

        // Get all the grupIndicatorList where keyWord equals to UPDATED_KEY_WORD
        defaultGrupIndicatorShouldNotBeFound("keyWord.in=" + UPDATED_KEY_WORD);
    }

    @Test
    @Transactional
    public void getAllGrupIndicatorsByKeyWordIsNullOrNotNull() throws Exception {
        // Initialize the database
        grupIndicatorRepository.saveAndFlush(grupIndicator);

        // Get all the grupIndicatorList where keyWord is not null
        defaultGrupIndicatorShouldBeFound("keyWord.specified=true");

        // Get all the grupIndicatorList where keyWord is null
        defaultGrupIndicatorShouldNotBeFound("keyWord.specified=false");
    }

    @Test
    @Transactional
    public void getAllGrupIndicatorsByDateIsEqualToSomething() throws Exception {
        // Initialize the database
        grupIndicatorRepository.saveAndFlush(grupIndicator);

        // Get all the grupIndicatorList where date equals to DEFAULT_DATE
        defaultGrupIndicatorShouldBeFound("date.equals=" + DEFAULT_DATE);

        // Get all the grupIndicatorList where date equals to UPDATED_DATE
        defaultGrupIndicatorShouldNotBeFound("date.equals=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllGrupIndicatorsByDateIsInShouldWork() throws Exception {
        // Initialize the database
        grupIndicatorRepository.saveAndFlush(grupIndicator);

        // Get all the grupIndicatorList where date in DEFAULT_DATE or UPDATED_DATE
        defaultGrupIndicatorShouldBeFound("date.in=" + DEFAULT_DATE + "," + UPDATED_DATE);

        // Get all the grupIndicatorList where date equals to UPDATED_DATE
        defaultGrupIndicatorShouldNotBeFound("date.in=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllGrupIndicatorsByDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        grupIndicatorRepository.saveAndFlush(grupIndicator);

        // Get all the grupIndicatorList where date is not null
        defaultGrupIndicatorShouldBeFound("date.specified=true");

        // Get all the grupIndicatorList where date is null
        defaultGrupIndicatorShouldNotBeFound("date.specified=false");
    }

    @Test
    @Transactional
    public void getAllGrupIndicatorsBySourceIsEqualToSomething() throws Exception {
        // Initialize the database
        grupIndicatorRepository.saveAndFlush(grupIndicator);

        // Get all the grupIndicatorList where source equals to DEFAULT_SOURCE
        defaultGrupIndicatorShouldBeFound("source.equals=" + DEFAULT_SOURCE);

        // Get all the grupIndicatorList where source equals to UPDATED_SOURCE
        defaultGrupIndicatorShouldNotBeFound("source.equals=" + UPDATED_SOURCE);
    }

    @Test
    @Transactional
    public void getAllGrupIndicatorsBySourceIsInShouldWork() throws Exception {
        // Initialize the database
        grupIndicatorRepository.saveAndFlush(grupIndicator);

        // Get all the grupIndicatorList where source in DEFAULT_SOURCE or UPDATED_SOURCE
        defaultGrupIndicatorShouldBeFound("source.in=" + DEFAULT_SOURCE + "," + UPDATED_SOURCE);

        // Get all the grupIndicatorList where source equals to UPDATED_SOURCE
        defaultGrupIndicatorShouldNotBeFound("source.in=" + UPDATED_SOURCE);
    }

    @Test
    @Transactional
    public void getAllGrupIndicatorsBySourceIsNullOrNotNull() throws Exception {
        // Initialize the database
        grupIndicatorRepository.saveAndFlush(grupIndicator);

        // Get all the grupIndicatorList where source is not null
        defaultGrupIndicatorShouldBeFound("source.specified=true");

        // Get all the grupIndicatorList where source is null
        defaultGrupIndicatorShouldNotBeFound("source.specified=false");
    }

    @Test
    @Transactional
    public void getAllGrupIndicatorsByDateChangeIsEqualToSomething() throws Exception {
        // Initialize the database
        grupIndicatorRepository.saveAndFlush(grupIndicator);

        // Get all the grupIndicatorList where dateChange equals to DEFAULT_DATE_CHANGE
        defaultGrupIndicatorShouldBeFound("dateChange.equals=" + DEFAULT_DATE_CHANGE);

        // Get all the grupIndicatorList where dateChange equals to UPDATED_DATE_CHANGE
        defaultGrupIndicatorShouldNotBeFound("dateChange.equals=" + UPDATED_DATE_CHANGE);
    }

    @Test
    @Transactional
    public void getAllGrupIndicatorsByDateChangeIsInShouldWork() throws Exception {
        // Initialize the database
        grupIndicatorRepository.saveAndFlush(grupIndicator);

        // Get all the grupIndicatorList where dateChange in DEFAULT_DATE_CHANGE or UPDATED_DATE_CHANGE
        defaultGrupIndicatorShouldBeFound("dateChange.in=" + DEFAULT_DATE_CHANGE + "," + UPDATED_DATE_CHANGE);

        // Get all the grupIndicatorList where dateChange equals to UPDATED_DATE_CHANGE
        defaultGrupIndicatorShouldNotBeFound("dateChange.in=" + UPDATED_DATE_CHANGE);
    }

    @Test
    @Transactional
    public void getAllGrupIndicatorsByDateChangeIsNullOrNotNull() throws Exception {
        // Initialize the database
        grupIndicatorRepository.saveAndFlush(grupIndicator);

        // Get all the grupIndicatorList where dateChange is not null
        defaultGrupIndicatorShouldBeFound("dateChange.specified=true");

        // Get all the grupIndicatorList where dateChange is null
        defaultGrupIndicatorShouldNotBeFound("dateChange.specified=false");
    }

    @Test
    @Transactional
    public void getAllGrupIndicatorsByMetaDadoIsEqualToSomething() throws Exception {
        // Initialize the database
        MetaDado metaDado = MetaDadoResourceIntTest.createEntity(em);
        em.persist(metaDado);
        em.flush();
        grupIndicator.addMetaDado(metaDado);
        grupIndicatorRepository.saveAndFlush(grupIndicator);
        Long metaDadoId = metaDado.getId();

        // Get all the grupIndicatorList where metaDado equals to metaDadoId
        defaultGrupIndicatorShouldBeFound("metaDadoId.equals=" + metaDadoId);

        // Get all the grupIndicatorList where metaDado equals to metaDadoId + 1
        defaultGrupIndicatorShouldNotBeFound("metaDadoId.equals=" + (metaDadoId + 1));
    }


    @Test
    @Transactional
    public void getAllGrupIndicatorsByOwnerIsEqualToSomething() throws Exception {
        // Initialize the database
        User owner = UserResourceIntTest.createEntity(em);
        em.persist(owner);
        em.flush();
        grupIndicator.setOwner(owner);
        grupIndicatorRepository.saveAndFlush(grupIndicator);
        Long ownerId = owner.getId();

        // Get all the grupIndicatorList where owner equals to ownerId
        defaultGrupIndicatorShouldBeFound("ownerId.equals=" + ownerId);

        // Get all the grupIndicatorList where owner equals to ownerId + 1
        defaultGrupIndicatorShouldNotBeFound("ownerId.equals=" + (ownerId + 1));
    }


    @Test
    @Transactional
    public void getAllGrupIndicatorsByTypePresentationIsEqualToSomething() throws Exception {
        // Initialize the database
        TypePresentation typePresentation = TypePresentationResourceIntTest.createEntity(em);
        em.persist(typePresentation);
        em.flush();
        grupIndicator.setTypePresentation(typePresentation);
        grupIndicatorRepository.saveAndFlush(grupIndicator);
        Long typePresentationId = typePresentation.getId();

        // Get all the grupIndicatorList where typePresentation equals to typePresentationId
        defaultGrupIndicatorShouldBeFound("typePresentationId.equals=" + typePresentationId);

        // Get all the grupIndicatorList where typePresentation equals to typePresentationId + 1
        defaultGrupIndicatorShouldNotBeFound("typePresentationId.equals=" + (typePresentationId + 1));
    }


    @Test
    @Transactional
    public void getAllGrupIndicatorsByCategoryIsEqualToSomething() throws Exception {
        // Initialize the database
        Category category = CategoryResourceIntTest.createEntity(em);
        em.persist(category);
        em.flush();
        grupIndicator.addCategory(category);
        grupIndicatorRepository.saveAndFlush(grupIndicator);
        Long categoryId = category.getId();

        // Get all the grupIndicatorList where category equals to categoryId
        defaultGrupIndicatorShouldBeFound("categoryId.equals=" + categoryId);

        // Get all the grupIndicatorList where category equals to categoryId + 1
        defaultGrupIndicatorShouldNotBeFound("categoryId.equals=" + (categoryId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultGrupIndicatorShouldBeFound(String filter) throws Exception {
        restGrupIndicatorMockMvc.perform(get("/api/grup-indicators?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(grupIndicator.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].keyWord").value(hasItem(DEFAULT_KEY_WORD.toString())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].source").value(hasItem(DEFAULT_SOURCE.toString())))
            .andExpect(jsonPath("$.[*].dateChange").value(hasItem(DEFAULT_DATE_CHANGE.toString())))
            .andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE.toString())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultGrupIndicatorShouldNotBeFound(String filter) throws Exception {
        restGrupIndicatorMockMvc.perform(get("/api/grup-indicators?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    @Transactional
    public void getNonExistingGrupIndicator() throws Exception {
        // Get the grupIndicator
        restGrupIndicatorMockMvc.perform(get("/api/grup-indicators/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGrupIndicator() throws Exception {
        // Initialize the database
        grupIndicatorService.save(grupIndicator);

        int databaseSizeBeforeUpdate = grupIndicatorRepository.findAll().size();

        // Update the grupIndicator
        GrupIndicator updatedGrupIndicator = grupIndicatorRepository.findById(grupIndicator.getId()).get();
        // Disconnect from session so that the updates on updatedGrupIndicator are not directly saved in db
        em.detach(updatedGrupIndicator);
        updatedGrupIndicator
            .name(UPDATED_NAME)
            .active(UPDATED_ACTIVE)
            .description(UPDATED_DESCRIPTION)
            .keyWord(UPDATED_KEY_WORD)
            .date(UPDATED_DATE)
            .source(UPDATED_SOURCE)
            .dateChange(UPDATED_DATE_CHANGE)
            .note(UPDATED_NOTE);

        restGrupIndicatorMockMvc.perform(put("/api/grup-indicators")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedGrupIndicator)))
            .andExpect(status().isOk());

        // Validate the GrupIndicator in the database
        List<GrupIndicator> grupIndicatorList = grupIndicatorRepository.findAll();
        assertThat(grupIndicatorList).hasSize(databaseSizeBeforeUpdate);
        GrupIndicator testGrupIndicator = grupIndicatorList.get(grupIndicatorList.size() - 1);
        assertThat(testGrupIndicator.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testGrupIndicator.isActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testGrupIndicator.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testGrupIndicator.getKeyWord()).isEqualTo(UPDATED_KEY_WORD);
        assertThat(testGrupIndicator.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testGrupIndicator.getSource()).isEqualTo(UPDATED_SOURCE);
        assertThat(testGrupIndicator.getDateChange()).isEqualTo(UPDATED_DATE_CHANGE);
        assertThat(testGrupIndicator.getNote()).isEqualTo(UPDATED_NOTE);
    }

    @Test
    @Transactional
    public void updateNonExistingGrupIndicator() throws Exception {
        int databaseSizeBeforeUpdate = grupIndicatorRepository.findAll().size();

        // Create the GrupIndicator

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restGrupIndicatorMockMvc.perform(put("/api/grup-indicators")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(grupIndicator)))
            .andExpect(status().isBadRequest());

        // Validate the GrupIndicator in the database
        List<GrupIndicator> grupIndicatorList = grupIndicatorRepository.findAll();
        assertThat(grupIndicatorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteGrupIndicator() throws Exception {
        // Initialize the database
        grupIndicatorService.save(grupIndicator);

        int databaseSizeBeforeDelete = grupIndicatorRepository.findAll().size();

        // Get the grupIndicator
        restGrupIndicatorMockMvc.perform(delete("/api/grup-indicators/{id}", grupIndicator.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<GrupIndicator> grupIndicatorList = grupIndicatorRepository.findAll();
        assertThat(grupIndicatorList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GrupIndicator.class);
        GrupIndicator grupIndicator1 = new GrupIndicator();
        grupIndicator1.setId(1L);
        GrupIndicator grupIndicator2 = new GrupIndicator();
        grupIndicator2.setId(grupIndicator1.getId());
        assertThat(grupIndicator1).isEqualTo(grupIndicator2);
        grupIndicator2.setId(2L);
        assertThat(grupIndicator1).isNotEqualTo(grupIndicator2);
        grupIndicator1.setId(null);
        assertThat(grupIndicator1).isNotEqualTo(grupIndicator2);
    }
}
