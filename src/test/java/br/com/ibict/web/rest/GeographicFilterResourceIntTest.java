package br.com.ibict.web.rest;

import br.com.ibict.VisaoApp;

import br.com.ibict.domain.GeographicFilter;
import br.com.ibict.domain.Region;
import br.com.ibict.domain.MetaDado;
import br.com.ibict.domain.User;
import br.com.ibict.domain.Region;
import br.com.ibict.domain.Category;
import br.com.ibict.repository.GeographicFilterRepository;
import br.com.ibict.service.GeographicFilterService;
import br.com.ibict.web.rest.errors.ExceptionTranslator;
import br.com.ibict.service.dto.GeographicFilterCriteria;
import br.com.ibict.service.GeographicFilterQueryService;

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
 * Test class for the GeographicFilterResource REST controller.
 *
 * @see GeographicFilterResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = VisaoApp.class)
public class GeographicFilterResourceIntTest {

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
    private GeographicFilterRepository geographicFilterRepository;
    @Mock
    private GeographicFilterRepository geographicFilterRepositoryMock;
    
    @Mock
    private GeographicFilterService geographicFilterServiceMock;

    @Autowired
    private GeographicFilterService geographicFilterService;

    @Autowired
    private GeographicFilterQueryService geographicFilterQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restGeographicFilterMockMvc;

    private GeographicFilter geographicFilter;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final GeographicFilterResource geographicFilterResource = new GeographicFilterResource(geographicFilterService, geographicFilterQueryService);
        this.restGeographicFilterMockMvc = MockMvcBuilders.standaloneSetup(geographicFilterResource)
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
    public static GeographicFilter createEntity(EntityManager em) {
        GeographicFilter geographicFilter = new GeographicFilter()
            .name(DEFAULT_NAME)
            .active(DEFAULT_ACTIVE)
            .description(DEFAULT_DESCRIPTION)
            .keyWord(DEFAULT_KEY_WORD)
            .date(DEFAULT_DATE)
            .source(DEFAULT_SOURCE)
            .dateChange(DEFAULT_DATE_CHANGE)
            .note(DEFAULT_NOTE);
        return geographicFilter;
    }

    @Before
    public void initTest() {
        geographicFilter = createEntity(em);
    }

    @Test
    @Transactional
    public void createGeographicFilter() throws Exception {
        int databaseSizeBeforeCreate = geographicFilterRepository.findAll().size();

        // Create the GeographicFilter
        restGeographicFilterMockMvc.perform(post("/api/geographic-filters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(geographicFilter)))
            .andExpect(status().isCreated());

        // Validate the GeographicFilter in the database
        List<GeographicFilter> geographicFilterList = geographicFilterRepository.findAll();
        assertThat(geographicFilterList).hasSize(databaseSizeBeforeCreate + 1);
        GeographicFilter testGeographicFilter = geographicFilterList.get(geographicFilterList.size() - 1);
        assertThat(testGeographicFilter.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testGeographicFilter.isActive()).isEqualTo(DEFAULT_ACTIVE);
        assertThat(testGeographicFilter.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testGeographicFilter.getKeyWord()).isEqualTo(DEFAULT_KEY_WORD);
        assertThat(testGeographicFilter.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testGeographicFilter.getSource()).isEqualTo(DEFAULT_SOURCE);
        assertThat(testGeographicFilter.getDateChange()).isEqualTo(DEFAULT_DATE_CHANGE);
        assertThat(testGeographicFilter.getNote()).isEqualTo(DEFAULT_NOTE);
    }

    @Test
    @Transactional
    public void createGeographicFilterWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = geographicFilterRepository.findAll().size();

        // Create the GeographicFilter with an existing ID
        geographicFilter.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGeographicFilterMockMvc.perform(post("/api/geographic-filters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(geographicFilter)))
            .andExpect(status().isBadRequest());

        // Validate the GeographicFilter in the database
        List<GeographicFilter> geographicFilterList = geographicFilterRepository.findAll();
        assertThat(geographicFilterList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = geographicFilterRepository.findAll().size();
        // set the field null
        geographicFilter.setName(null);

        // Create the GeographicFilter, which fails.

        restGeographicFilterMockMvc.perform(post("/api/geographic-filters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(geographicFilter)))
            .andExpect(status().isBadRequest());

        List<GeographicFilter> geographicFilterList = geographicFilterRepository.findAll();
        assertThat(geographicFilterList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActiveIsRequired() throws Exception {
        int databaseSizeBeforeTest = geographicFilterRepository.findAll().size();
        // set the field null
        geographicFilter.setActive(null);

        // Create the GeographicFilter, which fails.

        restGeographicFilterMockMvc.perform(post("/api/geographic-filters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(geographicFilter)))
            .andExpect(status().isBadRequest());

        List<GeographicFilter> geographicFilterList = geographicFilterRepository.findAll();
        assertThat(geographicFilterList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllGeographicFilters() throws Exception {
        // Initialize the database
        geographicFilterRepository.saveAndFlush(geographicFilter);

        // Get all the geographicFilterList
        restGeographicFilterMockMvc.perform(get("/api/geographic-filters?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(geographicFilter.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].keyWord").value(hasItem(DEFAULT_KEY_WORD.toString())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].source").value(hasItem(DEFAULT_SOURCE.toString())))
            .andExpect(jsonPath("$.[*].dateChange").value(hasItem(DEFAULT_DATE_CHANGE.toString())))
            .andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE.toString())));
    }
    
    public void getAllGeographicFiltersWithEagerRelationshipsIsEnabled() throws Exception {
        GeographicFilterResource geographicFilterResource = new GeographicFilterResource(geographicFilterServiceMock, geographicFilterQueryService);
        when(geographicFilterServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restGeographicFilterMockMvc = MockMvcBuilders.standaloneSetup(geographicFilterResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restGeographicFilterMockMvc.perform(get("/api/geographic-filters?eagerload=true"))
        .andExpect(status().isOk());

        verify(geographicFilterServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    public void getAllGeographicFiltersWithEagerRelationshipsIsNotEnabled() throws Exception {
        GeographicFilterResource geographicFilterResource = new GeographicFilterResource(geographicFilterServiceMock, geographicFilterQueryService);
            when(geographicFilterServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restGeographicFilterMockMvc = MockMvcBuilders.standaloneSetup(geographicFilterResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restGeographicFilterMockMvc.perform(get("/api/geographic-filters?eagerload=true"))
        .andExpect(status().isOk());

            verify(geographicFilterServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getGeographicFilter() throws Exception {
        // Initialize the database
        geographicFilterRepository.saveAndFlush(geographicFilter);

        // Get the geographicFilter
        restGeographicFilterMockMvc.perform(get("/api/geographic-filters/{id}", geographicFilter.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(geographicFilter.getId().intValue()))
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
    public void getAllGeographicFiltersByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        geographicFilterRepository.saveAndFlush(geographicFilter);

        // Get all the geographicFilterList where name equals to DEFAULT_NAME
        defaultGeographicFilterShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the geographicFilterList where name equals to UPDATED_NAME
        defaultGeographicFilterShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllGeographicFiltersByNameIsInShouldWork() throws Exception {
        // Initialize the database
        geographicFilterRepository.saveAndFlush(geographicFilter);

        // Get all the geographicFilterList where name in DEFAULT_NAME or UPDATED_NAME
        defaultGeographicFilterShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the geographicFilterList where name equals to UPDATED_NAME
        defaultGeographicFilterShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllGeographicFiltersByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        geographicFilterRepository.saveAndFlush(geographicFilter);

        // Get all the geographicFilterList where name is not null
        defaultGeographicFilterShouldBeFound("name.specified=true");

        // Get all the geographicFilterList where name is null
        defaultGeographicFilterShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllGeographicFiltersByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        geographicFilterRepository.saveAndFlush(geographicFilter);

        // Get all the geographicFilterList where active equals to DEFAULT_ACTIVE
        defaultGeographicFilterShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the geographicFilterList where active equals to UPDATED_ACTIVE
        defaultGeographicFilterShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllGeographicFiltersByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        geographicFilterRepository.saveAndFlush(geographicFilter);

        // Get all the geographicFilterList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultGeographicFilterShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the geographicFilterList where active equals to UPDATED_ACTIVE
        defaultGeographicFilterShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllGeographicFiltersByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        geographicFilterRepository.saveAndFlush(geographicFilter);

        // Get all the geographicFilterList where active is not null
        defaultGeographicFilterShouldBeFound("active.specified=true");

        // Get all the geographicFilterList where active is null
        defaultGeographicFilterShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllGeographicFiltersByKeyWordIsEqualToSomething() throws Exception {
        // Initialize the database
        geographicFilterRepository.saveAndFlush(geographicFilter);

        // Get all the geographicFilterList where keyWord equals to DEFAULT_KEY_WORD
        defaultGeographicFilterShouldBeFound("keyWord.equals=" + DEFAULT_KEY_WORD);

        // Get all the geographicFilterList where keyWord equals to UPDATED_KEY_WORD
        defaultGeographicFilterShouldNotBeFound("keyWord.equals=" + UPDATED_KEY_WORD);
    }

    @Test
    @Transactional
    public void getAllGeographicFiltersByKeyWordIsInShouldWork() throws Exception {
        // Initialize the database
        geographicFilterRepository.saveAndFlush(geographicFilter);

        // Get all the geographicFilterList where keyWord in DEFAULT_KEY_WORD or UPDATED_KEY_WORD
        defaultGeographicFilterShouldBeFound("keyWord.in=" + DEFAULT_KEY_WORD + "," + UPDATED_KEY_WORD);

        // Get all the geographicFilterList where keyWord equals to UPDATED_KEY_WORD
        defaultGeographicFilterShouldNotBeFound("keyWord.in=" + UPDATED_KEY_WORD);
    }

    @Test
    @Transactional
    public void getAllGeographicFiltersByKeyWordIsNullOrNotNull() throws Exception {
        // Initialize the database
        geographicFilterRepository.saveAndFlush(geographicFilter);

        // Get all the geographicFilterList where keyWord is not null
        defaultGeographicFilterShouldBeFound("keyWord.specified=true");

        // Get all the geographicFilterList where keyWord is null
        defaultGeographicFilterShouldNotBeFound("keyWord.specified=false");
    }

    @Test
    @Transactional
    public void getAllGeographicFiltersByDateIsEqualToSomething() throws Exception {
        // Initialize the database
        geographicFilterRepository.saveAndFlush(geographicFilter);

        // Get all the geographicFilterList where date equals to DEFAULT_DATE
        defaultGeographicFilterShouldBeFound("date.equals=" + DEFAULT_DATE);

        // Get all the geographicFilterList where date equals to UPDATED_DATE
        defaultGeographicFilterShouldNotBeFound("date.equals=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllGeographicFiltersByDateIsInShouldWork() throws Exception {
        // Initialize the database
        geographicFilterRepository.saveAndFlush(geographicFilter);

        // Get all the geographicFilterList where date in DEFAULT_DATE or UPDATED_DATE
        defaultGeographicFilterShouldBeFound("date.in=" + DEFAULT_DATE + "," + UPDATED_DATE);

        // Get all the geographicFilterList where date equals to UPDATED_DATE
        defaultGeographicFilterShouldNotBeFound("date.in=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllGeographicFiltersByDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        geographicFilterRepository.saveAndFlush(geographicFilter);

        // Get all the geographicFilterList where date is not null
        defaultGeographicFilterShouldBeFound("date.specified=true");

        // Get all the geographicFilterList where date is null
        defaultGeographicFilterShouldNotBeFound("date.specified=false");
    }

    @Test
    @Transactional
    public void getAllGeographicFiltersBySourceIsEqualToSomething() throws Exception {
        // Initialize the database
        geographicFilterRepository.saveAndFlush(geographicFilter);

        // Get all the geographicFilterList where source equals to DEFAULT_SOURCE
        defaultGeographicFilterShouldBeFound("source.equals=" + DEFAULT_SOURCE);

        // Get all the geographicFilterList where source equals to UPDATED_SOURCE
        defaultGeographicFilterShouldNotBeFound("source.equals=" + UPDATED_SOURCE);
    }

    @Test
    @Transactional
    public void getAllGeographicFiltersBySourceIsInShouldWork() throws Exception {
        // Initialize the database
        geographicFilterRepository.saveAndFlush(geographicFilter);

        // Get all the geographicFilterList where source in DEFAULT_SOURCE or UPDATED_SOURCE
        defaultGeographicFilterShouldBeFound("source.in=" + DEFAULT_SOURCE + "," + UPDATED_SOURCE);

        // Get all the geographicFilterList where source equals to UPDATED_SOURCE
        defaultGeographicFilterShouldNotBeFound("source.in=" + UPDATED_SOURCE);
    }

    @Test
    @Transactional
    public void getAllGeographicFiltersBySourceIsNullOrNotNull() throws Exception {
        // Initialize the database
        geographicFilterRepository.saveAndFlush(geographicFilter);

        // Get all the geographicFilterList where source is not null
        defaultGeographicFilterShouldBeFound("source.specified=true");

        // Get all the geographicFilterList where source is null
        defaultGeographicFilterShouldNotBeFound("source.specified=false");
    }

    @Test
    @Transactional
    public void getAllGeographicFiltersByDateChangeIsEqualToSomething() throws Exception {
        // Initialize the database
        geographicFilterRepository.saveAndFlush(geographicFilter);

        // Get all the geographicFilterList where dateChange equals to DEFAULT_DATE_CHANGE
        defaultGeographicFilterShouldBeFound("dateChange.equals=" + DEFAULT_DATE_CHANGE);

        // Get all the geographicFilterList where dateChange equals to UPDATED_DATE_CHANGE
        defaultGeographicFilterShouldNotBeFound("dateChange.equals=" + UPDATED_DATE_CHANGE);
    }

    @Test
    @Transactional
    public void getAllGeographicFiltersByDateChangeIsInShouldWork() throws Exception {
        // Initialize the database
        geographicFilterRepository.saveAndFlush(geographicFilter);

        // Get all the geographicFilterList where dateChange in DEFAULT_DATE_CHANGE or UPDATED_DATE_CHANGE
        defaultGeographicFilterShouldBeFound("dateChange.in=" + DEFAULT_DATE_CHANGE + "," + UPDATED_DATE_CHANGE);

        // Get all the geographicFilterList where dateChange equals to UPDATED_DATE_CHANGE
        defaultGeographicFilterShouldNotBeFound("dateChange.in=" + UPDATED_DATE_CHANGE);
    }

    @Test
    @Transactional
    public void getAllGeographicFiltersByDateChangeIsNullOrNotNull() throws Exception {
        // Initialize the database
        geographicFilterRepository.saveAndFlush(geographicFilter);

        // Get all the geographicFilterList where dateChange is not null
        defaultGeographicFilterShouldBeFound("dateChange.specified=true");

        // Get all the geographicFilterList where dateChange is null
        defaultGeographicFilterShouldNotBeFound("dateChange.specified=false");
    }

    @Test
    @Transactional
    public void getAllGeographicFiltersByCidadePoloIsEqualToSomething() throws Exception {
        // Initialize the database
        Region cidadePolo = RegionResourceIntTest.createEntity(em);
        em.persist(cidadePolo);
        em.flush();
        geographicFilter.setCidadePolo(cidadePolo);
        geographicFilterRepository.saveAndFlush(geographicFilter);
        Long cidadePoloId = cidadePolo.getId();

        // Get all the geographicFilterList where cidadePolo equals to cidadePoloId
        defaultGeographicFilterShouldBeFound("cidadePoloId.equals=" + cidadePoloId);

        // Get all the geographicFilterList where cidadePolo equals to cidadePoloId + 1
        defaultGeographicFilterShouldNotBeFound("cidadePoloId.equals=" + (cidadePoloId + 1));
    }


    @Test
    @Transactional
    public void getAllGeographicFiltersByMetaDadoIsEqualToSomething() throws Exception {
        // Initialize the database
        MetaDado metaDado = MetaDadoResourceIntTest.createEntity(em);
        em.persist(metaDado);
        em.flush();
        geographicFilter.addMetaDado(metaDado);
        geographicFilterRepository.saveAndFlush(geographicFilter);
        Long metaDadoId = metaDado.getId();

        // Get all the geographicFilterList where metaDado equals to metaDadoId
        defaultGeographicFilterShouldBeFound("metaDadoId.equals=" + metaDadoId);

        // Get all the geographicFilterList where metaDado equals to metaDadoId + 1
        defaultGeographicFilterShouldNotBeFound("metaDadoId.equals=" + (metaDadoId + 1));
    }


    @Test
    @Transactional
    public void getAllGeographicFiltersByOwnerIsEqualToSomething() throws Exception {
        // Initialize the database
        User owner = UserResourceIntTest.createEntity(em);
        em.persist(owner);
        em.flush();
        geographicFilter.setOwner(owner);
        geographicFilterRepository.saveAndFlush(geographicFilter);
        Long ownerId = owner.getId();

        // Get all the geographicFilterList where owner equals to ownerId
        defaultGeographicFilterShouldBeFound("ownerId.equals=" + ownerId);

        // Get all the geographicFilterList where owner equals to ownerId + 1
        defaultGeographicFilterShouldNotBeFound("ownerId.equals=" + (ownerId + 1));
    }


    @Test
    @Transactional
    public void getAllGeographicFiltersByRegionIsEqualToSomething() throws Exception {
        // Initialize the database
        Region region = RegionResourceIntTest.createEntity(em);
        em.persist(region);
        em.flush();
        geographicFilter.addRegion(region);
        geographicFilterRepository.saveAndFlush(geographicFilter);
        Long regionId = region.getId();

        // Get all the geographicFilterList where region equals to regionId
        defaultGeographicFilterShouldBeFound("regionId.equals=" + regionId);

        // Get all the geographicFilterList where region equals to regionId + 1
        defaultGeographicFilterShouldNotBeFound("regionId.equals=" + (regionId + 1));
    }


    @Test
    @Transactional
    public void getAllGeographicFiltersByCategoryIsEqualToSomething() throws Exception {
        // Initialize the database
        Category category = CategoryResourceIntTest.createEntity(em);
        em.persist(category);
        em.flush();
        geographicFilter.addCategory(category);
        geographicFilterRepository.saveAndFlush(geographicFilter);
        Long categoryId = category.getId();

        // Get all the geographicFilterList where category equals to categoryId
        defaultGeographicFilterShouldBeFound("categoryId.equals=" + categoryId);

        // Get all the geographicFilterList where category equals to categoryId + 1
        defaultGeographicFilterShouldNotBeFound("categoryId.equals=" + (categoryId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultGeographicFilterShouldBeFound(String filter) throws Exception {
        restGeographicFilterMockMvc.perform(get("/api/geographic-filters?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(geographicFilter.getId().intValue())))
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
    private void defaultGeographicFilterShouldNotBeFound(String filter) throws Exception {
        restGeographicFilterMockMvc.perform(get("/api/geographic-filters?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    @Transactional
    public void getNonExistingGeographicFilter() throws Exception {
        // Get the geographicFilter
        restGeographicFilterMockMvc.perform(get("/api/geographic-filters/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGeographicFilter() throws Exception {
        // Initialize the database
        geographicFilterService.save(geographicFilter);

        int databaseSizeBeforeUpdate = geographicFilterRepository.findAll().size();

        // Update the geographicFilter
        GeographicFilter updatedGeographicFilter = geographicFilterRepository.findById(geographicFilter.getId()).get();
        // Disconnect from session so that the updates on updatedGeographicFilter are not directly saved in db
        em.detach(updatedGeographicFilter);
        updatedGeographicFilter
            .name(UPDATED_NAME)
            .active(UPDATED_ACTIVE)
            .description(UPDATED_DESCRIPTION)
            .keyWord(UPDATED_KEY_WORD)
            .date(UPDATED_DATE)
            .source(UPDATED_SOURCE)
            .dateChange(UPDATED_DATE_CHANGE)
            .note(UPDATED_NOTE);

        restGeographicFilterMockMvc.perform(put("/api/geographic-filters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedGeographicFilter)))
            .andExpect(status().isOk());

        // Validate the GeographicFilter in the database
        List<GeographicFilter> geographicFilterList = geographicFilterRepository.findAll();
        assertThat(geographicFilterList).hasSize(databaseSizeBeforeUpdate);
        GeographicFilter testGeographicFilter = geographicFilterList.get(geographicFilterList.size() - 1);
        assertThat(testGeographicFilter.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testGeographicFilter.isActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testGeographicFilter.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testGeographicFilter.getKeyWord()).isEqualTo(UPDATED_KEY_WORD);
        assertThat(testGeographicFilter.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testGeographicFilter.getSource()).isEqualTo(UPDATED_SOURCE);
        assertThat(testGeographicFilter.getDateChange()).isEqualTo(UPDATED_DATE_CHANGE);
        assertThat(testGeographicFilter.getNote()).isEqualTo(UPDATED_NOTE);
    }

    @Test
    @Transactional
    public void updateNonExistingGeographicFilter() throws Exception {
        int databaseSizeBeforeUpdate = geographicFilterRepository.findAll().size();

        // Create the GeographicFilter

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restGeographicFilterMockMvc.perform(put("/api/geographic-filters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(geographicFilter)))
            .andExpect(status().isBadRequest());

        // Validate the GeographicFilter in the database
        List<GeographicFilter> geographicFilterList = geographicFilterRepository.findAll();
        assertThat(geographicFilterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteGeographicFilter() throws Exception {
        // Initialize the database
        geographicFilterService.save(geographicFilter);

        int databaseSizeBeforeDelete = geographicFilterRepository.findAll().size();

        // Get the geographicFilter
        restGeographicFilterMockMvc.perform(delete("/api/geographic-filters/{id}", geographicFilter.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<GeographicFilter> geographicFilterList = geographicFilterRepository.findAll();
        assertThat(geographicFilterList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GeographicFilter.class);
        GeographicFilter geographicFilter1 = new GeographicFilter();
        geographicFilter1.setId(1L);
        GeographicFilter geographicFilter2 = new GeographicFilter();
        geographicFilter2.setId(geographicFilter1.getId());
        assertThat(geographicFilter1).isEqualTo(geographicFilter2);
        geographicFilter2.setId(2L);
        assertThat(geographicFilter1).isNotEqualTo(geographicFilter2);
        geographicFilter1.setId(null);
        assertThat(geographicFilter1).isNotEqualTo(geographicFilter2);
    }
}
