package br.com.ibict.visao.web.rest;

import br.com.ibict.visao.VisaoApp;

import br.com.ibict.visao.domain.Filter;
import br.com.ibict.visao.domain.User;
import br.com.ibict.visao.domain.Region;
import br.com.ibict.visao.repository.FilterRepository;
import br.com.ibict.visao.service.FilterService;
import br.com.ibict.visao.web.rest.errors.ExceptionTranslator;
import br.com.ibict.visao.service.dto.FilterCriteria;
import br.com.ibict.visao.service.FilterQueryService;

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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;


import static br.com.ibict.visao.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the FilterResource REST controller.
 *
 * @see FilterResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = VisaoApp.class)
public class FilterResourceIntTest {

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

    private static final String DEFAULT_PRODUCER = "AAAAAAAAAA";
    private static final String UPDATED_PRODUCER = "BBBBBBBBBB";

    private static final String DEFAULT_SOURCE = "AAAAAAAAAA";
    private static final String UPDATED_SOURCE = "BBBBBBBBBB";

    private static final Instant DEFAULT_DATE_CHANGE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_CHANGE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_NOTE = "AAAAAAAAAA";
    private static final String UPDATED_NOTE = "BBBBBBBBBB";

    @Autowired
    private FilterRepository filterRepository;
    @Mock
    private FilterRepository filterRepositoryMock;
    
    @Mock
    private FilterService filterServiceMock;

    @Autowired
    private FilterService filterService;

    @Autowired
    private FilterQueryService filterQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restFilterMockMvc;

    private Filter filter;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FilterResource filterResource = new FilterResource(filterService, filterQueryService);
        this.restFilterMockMvc = MockMvcBuilders.standaloneSetup(filterResource)
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
    public static Filter createEntity(EntityManager em) {
        Filter filter = new Filter()
            .name(DEFAULT_NAME)
            .active(DEFAULT_ACTIVE)
            .description(DEFAULT_DESCRIPTION)
            .keyWord(DEFAULT_KEY_WORD)
            .date(DEFAULT_DATE)
            .producer(DEFAULT_PRODUCER)
            .source(DEFAULT_SOURCE)
            .dateChange(DEFAULT_DATE_CHANGE)
            .note(DEFAULT_NOTE);
        return filter;
    }

    @Before
    public void initTest() {
        filter = createEntity(em);
    }

    @Test
    @Transactional
    public void createFilter() throws Exception {
        int databaseSizeBeforeCreate = filterRepository.findAll().size();

        // Create the Filter
        restFilterMockMvc.perform(post("/api/filters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(filter)))
            .andExpect(status().isCreated());

        // Validate the Filter in the database
        List<Filter> filterList = filterRepository.findAll();
        assertThat(filterList).hasSize(databaseSizeBeforeCreate + 1);
        Filter testFilter = filterList.get(filterList.size() - 1);
        assertThat(testFilter.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testFilter.isActive()).isEqualTo(DEFAULT_ACTIVE);
        assertThat(testFilter.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testFilter.getKeyWord()).isEqualTo(DEFAULT_KEY_WORD);
        assertThat(testFilter.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testFilter.getProducer()).isEqualTo(DEFAULT_PRODUCER);
        assertThat(testFilter.getSource()).isEqualTo(DEFAULT_SOURCE);
        assertThat(testFilter.getDateChange()).isEqualTo(DEFAULT_DATE_CHANGE);
        assertThat(testFilter.getNote()).isEqualTo(DEFAULT_NOTE);
    }

    @Test
    @Transactional
    public void createFilterWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = filterRepository.findAll().size();

        // Create the Filter with an existing ID
        filter.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFilterMockMvc.perform(post("/api/filters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(filter)))
            .andExpect(status().isBadRequest());

        // Validate the Filter in the database
        List<Filter> filterList = filterRepository.findAll();
        assertThat(filterList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = filterRepository.findAll().size();
        // set the field null
        filter.setName(null);

        // Create the Filter, which fails.

        restFilterMockMvc.perform(post("/api/filters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(filter)))
            .andExpect(status().isBadRequest());

        List<Filter> filterList = filterRepository.findAll();
        assertThat(filterList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActiveIsRequired() throws Exception {
        int databaseSizeBeforeTest = filterRepository.findAll().size();
        // set the field null
        filter.setActive(null);

        // Create the Filter, which fails.

        restFilterMockMvc.perform(post("/api/filters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(filter)))
            .andExpect(status().isBadRequest());

        List<Filter> filterList = filterRepository.findAll();
        assertThat(filterList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFilters() throws Exception {
        // Initialize the database
        filterRepository.saveAndFlush(filter);

        // Get all the filterList
        restFilterMockMvc.perform(get("/api/filters?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(filter.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].keyWord").value(hasItem(DEFAULT_KEY_WORD.toString())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].producer").value(hasItem(DEFAULT_PRODUCER.toString())))
            .andExpect(jsonPath("$.[*].source").value(hasItem(DEFAULT_SOURCE.toString())))
            .andExpect(jsonPath("$.[*].dateChange").value(hasItem(DEFAULT_DATE_CHANGE.toString())))
            .andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE.toString())));
    }
    
    public void getAllFiltersWithEagerRelationshipsIsEnabled() throws Exception {
        FilterResource filterResource = new FilterResource(filterServiceMock, filterQueryService);
        when(filterServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restFilterMockMvc = MockMvcBuilders.standaloneSetup(filterResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restFilterMockMvc.perform(get("/api/filters?eagerload=true"))
        .andExpect(status().isOk());

        verify(filterServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    public void getAllFiltersWithEagerRelationshipsIsNotEnabled() throws Exception {
        FilterResource filterResource = new FilterResource(filterServiceMock, filterQueryService);
            when(filterServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restFilterMockMvc = MockMvcBuilders.standaloneSetup(filterResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restFilterMockMvc.perform(get("/api/filters?eagerload=true"))
        .andExpect(status().isOk());

            verify(filterServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getFilter() throws Exception {
        // Initialize the database
        filterRepository.saveAndFlush(filter);

        // Get the filter
        restFilterMockMvc.perform(get("/api/filters/{id}", filter.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(filter.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.keyWord").value(DEFAULT_KEY_WORD.toString()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.producer").value(DEFAULT_PRODUCER.toString()))
            .andExpect(jsonPath("$.source").value(DEFAULT_SOURCE.toString()))
            .andExpect(jsonPath("$.dateChange").value(DEFAULT_DATE_CHANGE.toString()))
            .andExpect(jsonPath("$.note").value(DEFAULT_NOTE.toString()));
    }

    @Test
    @Transactional
    public void getAllFiltersByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        filterRepository.saveAndFlush(filter);

        // Get all the filterList where name equals to DEFAULT_NAME
        defaultFilterShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the filterList where name equals to UPDATED_NAME
        defaultFilterShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllFiltersByNameIsInShouldWork() throws Exception {
        // Initialize the database
        filterRepository.saveAndFlush(filter);

        // Get all the filterList where name in DEFAULT_NAME or UPDATED_NAME
        defaultFilterShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the filterList where name equals to UPDATED_NAME
        defaultFilterShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllFiltersByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        filterRepository.saveAndFlush(filter);

        // Get all the filterList where name is not null
        defaultFilterShouldBeFound("name.specified=true");

        // Get all the filterList where name is null
        defaultFilterShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllFiltersByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        filterRepository.saveAndFlush(filter);

        // Get all the filterList where active equals to DEFAULT_ACTIVE
        defaultFilterShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the filterList where active equals to UPDATED_ACTIVE
        defaultFilterShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllFiltersByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        filterRepository.saveAndFlush(filter);

        // Get all the filterList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultFilterShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the filterList where active equals to UPDATED_ACTIVE
        defaultFilterShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllFiltersByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        filterRepository.saveAndFlush(filter);

        // Get all the filterList where active is not null
        defaultFilterShouldBeFound("active.specified=true");

        // Get all the filterList where active is null
        defaultFilterShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllFiltersByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        filterRepository.saveAndFlush(filter);

        // Get all the filterList where description equals to DEFAULT_DESCRIPTION
        defaultFilterShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the filterList where description equals to UPDATED_DESCRIPTION
        defaultFilterShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllFiltersByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        filterRepository.saveAndFlush(filter);

        // Get all the filterList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultFilterShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the filterList where description equals to UPDATED_DESCRIPTION
        defaultFilterShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllFiltersByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        filterRepository.saveAndFlush(filter);

        // Get all the filterList where description is not null
        defaultFilterShouldBeFound("description.specified=true");

        // Get all the filterList where description is null
        defaultFilterShouldNotBeFound("description.specified=false");
    }

    @Test
    @Transactional
    public void getAllFiltersByKeyWordIsEqualToSomething() throws Exception {
        // Initialize the database
        filterRepository.saveAndFlush(filter);

        // Get all the filterList where keyWord equals to DEFAULT_KEY_WORD
        defaultFilterShouldBeFound("keyWord.equals=" + DEFAULT_KEY_WORD);

        // Get all the filterList where keyWord equals to UPDATED_KEY_WORD
        defaultFilterShouldNotBeFound("keyWord.equals=" + UPDATED_KEY_WORD);
    }

    @Test
    @Transactional
    public void getAllFiltersByKeyWordIsInShouldWork() throws Exception {
        // Initialize the database
        filterRepository.saveAndFlush(filter);

        // Get all the filterList where keyWord in DEFAULT_KEY_WORD or UPDATED_KEY_WORD
        defaultFilterShouldBeFound("keyWord.in=" + DEFAULT_KEY_WORD + "," + UPDATED_KEY_WORD);

        // Get all the filterList where keyWord equals to UPDATED_KEY_WORD
        defaultFilterShouldNotBeFound("keyWord.in=" + UPDATED_KEY_WORD);
    }

    @Test
    @Transactional
    public void getAllFiltersByKeyWordIsNullOrNotNull() throws Exception {
        // Initialize the database
        filterRepository.saveAndFlush(filter);

        // Get all the filterList where keyWord is not null
        defaultFilterShouldBeFound("keyWord.specified=true");

        // Get all the filterList where keyWord is null
        defaultFilterShouldNotBeFound("keyWord.specified=false");
    }

    @Test
    @Transactional
    public void getAllFiltersByDateIsEqualToSomething() throws Exception {
        // Initialize the database
        filterRepository.saveAndFlush(filter);

        // Get all the filterList where date equals to DEFAULT_DATE
        defaultFilterShouldBeFound("date.equals=" + DEFAULT_DATE);

        // Get all the filterList where date equals to UPDATED_DATE
        defaultFilterShouldNotBeFound("date.equals=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllFiltersByDateIsInShouldWork() throws Exception {
        // Initialize the database
        filterRepository.saveAndFlush(filter);

        // Get all the filterList where date in DEFAULT_DATE or UPDATED_DATE
        defaultFilterShouldBeFound("date.in=" + DEFAULT_DATE + "," + UPDATED_DATE);

        // Get all the filterList where date equals to UPDATED_DATE
        defaultFilterShouldNotBeFound("date.in=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllFiltersByDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        filterRepository.saveAndFlush(filter);

        // Get all the filterList where date is not null
        defaultFilterShouldBeFound("date.specified=true");

        // Get all the filterList where date is null
        defaultFilterShouldNotBeFound("date.specified=false");
    }

    @Test
    @Transactional
    public void getAllFiltersByProducerIsEqualToSomething() throws Exception {
        // Initialize the database
        filterRepository.saveAndFlush(filter);

        // Get all the filterList where producer equals to DEFAULT_PRODUCER
        defaultFilterShouldBeFound("producer.equals=" + DEFAULT_PRODUCER);

        // Get all the filterList where producer equals to UPDATED_PRODUCER
        defaultFilterShouldNotBeFound("producer.equals=" + UPDATED_PRODUCER);
    }

    @Test
    @Transactional
    public void getAllFiltersByProducerIsInShouldWork() throws Exception {
        // Initialize the database
        filterRepository.saveAndFlush(filter);

        // Get all the filterList where producer in DEFAULT_PRODUCER or UPDATED_PRODUCER
        defaultFilterShouldBeFound("producer.in=" + DEFAULT_PRODUCER + "," + UPDATED_PRODUCER);

        // Get all the filterList where producer equals to UPDATED_PRODUCER
        defaultFilterShouldNotBeFound("producer.in=" + UPDATED_PRODUCER);
    }

    @Test
    @Transactional
    public void getAllFiltersByProducerIsNullOrNotNull() throws Exception {
        // Initialize the database
        filterRepository.saveAndFlush(filter);

        // Get all the filterList where producer is not null
        defaultFilterShouldBeFound("producer.specified=true");

        // Get all the filterList where producer is null
        defaultFilterShouldNotBeFound("producer.specified=false");
    }

    @Test
    @Transactional
    public void getAllFiltersBySourceIsEqualToSomething() throws Exception {
        // Initialize the database
        filterRepository.saveAndFlush(filter);

        // Get all the filterList where source equals to DEFAULT_SOURCE
        defaultFilterShouldBeFound("source.equals=" + DEFAULT_SOURCE);

        // Get all the filterList where source equals to UPDATED_SOURCE
        defaultFilterShouldNotBeFound("source.equals=" + UPDATED_SOURCE);
    }

    @Test
    @Transactional
    public void getAllFiltersBySourceIsInShouldWork() throws Exception {
        // Initialize the database
        filterRepository.saveAndFlush(filter);

        // Get all the filterList where source in DEFAULT_SOURCE or UPDATED_SOURCE
        defaultFilterShouldBeFound("source.in=" + DEFAULT_SOURCE + "," + UPDATED_SOURCE);

        // Get all the filterList where source equals to UPDATED_SOURCE
        defaultFilterShouldNotBeFound("source.in=" + UPDATED_SOURCE);
    }

    @Test
    @Transactional
    public void getAllFiltersBySourceIsNullOrNotNull() throws Exception {
        // Initialize the database
        filterRepository.saveAndFlush(filter);

        // Get all the filterList where source is not null
        defaultFilterShouldBeFound("source.specified=true");

        // Get all the filterList where source is null
        defaultFilterShouldNotBeFound("source.specified=false");
    }

    @Test
    @Transactional
    public void getAllFiltersByDateChangeIsEqualToSomething() throws Exception {
        // Initialize the database
        filterRepository.saveAndFlush(filter);

        // Get all the filterList where dateChange equals to DEFAULT_DATE_CHANGE
        defaultFilterShouldBeFound("dateChange.equals=" + DEFAULT_DATE_CHANGE);

        // Get all the filterList where dateChange equals to UPDATED_DATE_CHANGE
        defaultFilterShouldNotBeFound("dateChange.equals=" + UPDATED_DATE_CHANGE);
    }

    @Test
    @Transactional
    public void getAllFiltersByDateChangeIsInShouldWork() throws Exception {
        // Initialize the database
        filterRepository.saveAndFlush(filter);

        // Get all the filterList where dateChange in DEFAULT_DATE_CHANGE or UPDATED_DATE_CHANGE
        defaultFilterShouldBeFound("dateChange.in=" + DEFAULT_DATE_CHANGE + "," + UPDATED_DATE_CHANGE);

        // Get all the filterList where dateChange equals to UPDATED_DATE_CHANGE
        defaultFilterShouldNotBeFound("dateChange.in=" + UPDATED_DATE_CHANGE);
    }

    @Test
    @Transactional
    public void getAllFiltersByDateChangeIsNullOrNotNull() throws Exception {
        // Initialize the database
        filterRepository.saveAndFlush(filter);

        // Get all the filterList where dateChange is not null
        defaultFilterShouldBeFound("dateChange.specified=true");

        // Get all the filterList where dateChange is null
        defaultFilterShouldNotBeFound("dateChange.specified=false");
    }

    @Test
    @Transactional
    public void getAllFiltersByNoteIsEqualToSomething() throws Exception {
        // Initialize the database
        filterRepository.saveAndFlush(filter);

        // Get all the filterList where note equals to DEFAULT_NOTE
        defaultFilterShouldBeFound("note.equals=" + DEFAULT_NOTE);

        // Get all the filterList where note equals to UPDATED_NOTE
        defaultFilterShouldNotBeFound("note.equals=" + UPDATED_NOTE);
    }

    @Test
    @Transactional
    public void getAllFiltersByNoteIsInShouldWork() throws Exception {
        // Initialize the database
        filterRepository.saveAndFlush(filter);

        // Get all the filterList where note in DEFAULT_NOTE or UPDATED_NOTE
        defaultFilterShouldBeFound("note.in=" + DEFAULT_NOTE + "," + UPDATED_NOTE);

        // Get all the filterList where note equals to UPDATED_NOTE
        defaultFilterShouldNotBeFound("note.in=" + UPDATED_NOTE);
    }

    @Test
    @Transactional
    public void getAllFiltersByNoteIsNullOrNotNull() throws Exception {
        // Initialize the database
        filterRepository.saveAndFlush(filter);

        // Get all the filterList where note is not null
        defaultFilterShouldBeFound("note.specified=true");

        // Get all the filterList where note is null
        defaultFilterShouldNotBeFound("note.specified=false");
    }

    @Test
    @Transactional
    public void getAllFiltersByUserIsEqualToSomething() throws Exception {
        // Initialize the database
        User user = UserResourceIntTest.createEntity(em);
        em.persist(user);
        em.flush();
        filter.setUser(user);
        filterRepository.saveAndFlush(filter);
        Long userId = user.getId();

        // Get all the filterList where user equals to userId
        defaultFilterShouldBeFound("userId.equals=" + userId);

        // Get all the filterList where user equals to userId + 1
        defaultFilterShouldNotBeFound("userId.equals=" + (userId + 1));
    }


    @Test
    @Transactional
    public void getAllFiltersByRegionIsEqualToSomething() throws Exception {
        // Initialize the database
        Region region = RegionResourceIntTest.createEntity(em);
        em.persist(region);
        em.flush();
        filter.addRegion(region);
        filterRepository.saveAndFlush(filter);
        Long regionId = region.getId();

        // Get all the filterList where region equals to regionId
        defaultFilterShouldBeFound("regionId.equals=" + regionId);

        // Get all the filterList where region equals to regionId + 1
        defaultFilterShouldNotBeFound("regionId.equals=" + (regionId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultFilterShouldBeFound(String filter) throws Exception {
        restFilterMockMvc.perform(get("/api/filters?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].keyWord").value(hasItem(DEFAULT_KEY_WORD.toString())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].producer").value(hasItem(DEFAULT_PRODUCER.toString())))
            .andExpect(jsonPath("$.[*].source").value(hasItem(DEFAULT_SOURCE.toString())))
            .andExpect(jsonPath("$.[*].dateChange").value(hasItem(DEFAULT_DATE_CHANGE.toString())))
            .andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE.toString())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultFilterShouldNotBeFound(String filter) throws Exception {
        restFilterMockMvc.perform(get("/api/filters?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    @Transactional
    public void getNonExistingFilter() throws Exception {
        // Get the filter
        restFilterMockMvc.perform(get("/api/filters/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFilter() throws Exception {
        // Initialize the database
        filterService.save(filter);

        int databaseSizeBeforeUpdate = filterRepository.findAll().size();

        // Update the filter
        Filter updatedFilter = filterRepository.findById(filter.getId()).get();
        // Disconnect from session so that the updates on updatedFilter are not directly saved in db
        em.detach(updatedFilter);
        updatedFilter
            .name(UPDATED_NAME)
            .active(UPDATED_ACTIVE)
            .description(UPDATED_DESCRIPTION)
            .keyWord(UPDATED_KEY_WORD)
            .date(UPDATED_DATE)
            .producer(UPDATED_PRODUCER)
            .source(UPDATED_SOURCE)
            .dateChange(UPDATED_DATE_CHANGE)
            .note(UPDATED_NOTE);

        restFilterMockMvc.perform(put("/api/filters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedFilter)))
            .andExpect(status().isOk());

        // Validate the Filter in the database
        List<Filter> filterList = filterRepository.findAll();
        assertThat(filterList).hasSize(databaseSizeBeforeUpdate);
        Filter testFilter = filterList.get(filterList.size() - 1);
        assertThat(testFilter.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testFilter.isActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testFilter.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testFilter.getKeyWord()).isEqualTo(UPDATED_KEY_WORD);
        assertThat(testFilter.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testFilter.getProducer()).isEqualTo(UPDATED_PRODUCER);
        assertThat(testFilter.getSource()).isEqualTo(UPDATED_SOURCE);
        assertThat(testFilter.getDateChange()).isEqualTo(UPDATED_DATE_CHANGE);
        assertThat(testFilter.getNote()).isEqualTo(UPDATED_NOTE);
    }

    @Test
    @Transactional
    public void updateNonExistingFilter() throws Exception {
        int databaseSizeBeforeUpdate = filterRepository.findAll().size();

        // Create the Filter

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restFilterMockMvc.perform(put("/api/filters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(filter)))
            .andExpect(status().isBadRequest());

        // Validate the Filter in the database
        List<Filter> filterList = filterRepository.findAll();
        assertThat(filterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFilter() throws Exception {
        // Initialize the database
        filterService.save(filter);

        int databaseSizeBeforeDelete = filterRepository.findAll().size();

        // Get the filter
        restFilterMockMvc.perform(delete("/api/filters/{id}", filter.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Filter> filterList = filterRepository.findAll();
        assertThat(filterList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Filter.class);
        Filter filter1 = new Filter();
        filter1.setId(1L);
        Filter filter2 = new Filter();
        filter2.setId(filter1.getId());
        assertThat(filter1).isEqualTo(filter2);
        filter2.setId(2L);
        assertThat(filter1).isNotEqualTo(filter2);
        filter1.setId(null);
        assertThat(filter1).isNotEqualTo(filter2);
    }
}
