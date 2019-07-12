package br.com.ibict.web.rest;

import br.com.ibict.VisaoApp;

import br.com.ibict.domain.Year;
import br.com.ibict.repository.YearRepository;
import br.com.ibict.service.YearService;
import br.com.ibict.web.rest.errors.ExceptionTranslator;
import br.com.ibict.service.dto.YearCriteria;
import br.com.ibict.service.YearQueryService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;


import static br.com.ibict.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the YearResource REST controller.
 *
 * @see YearResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = VisaoApp.class)
public class YearResourceIntTest {

    private static final String DEFAULT_DATE = "AAAAAAAAAA";
    private static final String UPDATED_DATE = "BBBBBBBBBB";

    @Autowired
    private YearRepository yearRepository;

    

    @Autowired
    private YearService yearService;

    @Autowired
    private YearQueryService yearQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restYearMockMvc;

    private Year year;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final YearResource yearResource = new YearResource(yearService, yearQueryService);
        this.restYearMockMvc = MockMvcBuilders.standaloneSetup(yearResource)
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
    public static Year createEntity(EntityManager em) {
        Year year = new Year()
            .date(DEFAULT_DATE);
        return year;
    }

    @Before
    public void initTest() {
        year = createEntity(em);
    }

    @Test
    @Transactional
    public void createYear() throws Exception {
        int databaseSizeBeforeCreate = yearRepository.findAll().size();

        // Create the Year
        restYearMockMvc.perform(post("/api/years")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(year)))
            .andExpect(status().isCreated());

        // Validate the Year in the database
        List<Year> yearList = yearRepository.findAll();
        assertThat(yearList).hasSize(databaseSizeBeforeCreate + 1);
        Year testYear = yearList.get(yearList.size() - 1);
        assertThat(testYear.getDate()).isEqualTo(DEFAULT_DATE);
    }

    @Test
    @Transactional
    public void createYearWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = yearRepository.findAll().size();

        // Create the Year with an existing ID
        year.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restYearMockMvc.perform(post("/api/years")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(year)))
            .andExpect(status().isBadRequest());

        // Validate the Year in the database
        List<Year> yearList = yearRepository.findAll();
        assertThat(yearList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = yearRepository.findAll().size();
        // set the field null
        year.setDate(null);

        // Create the Year, which fails.

        restYearMockMvc.perform(post("/api/years")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(year)))
            .andExpect(status().isBadRequest());

        List<Year> yearList = yearRepository.findAll();
        assertThat(yearList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllYears() throws Exception {
        // Initialize the database
        yearRepository.saveAndFlush(year);

        // Get all the yearList
        restYearMockMvc.perform(get("/api/years?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(year.getId().intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())));
    }
    

    @Test
    @Transactional
    public void getYear() throws Exception {
        // Initialize the database
        yearRepository.saveAndFlush(year);

        // Get the year
        restYearMockMvc.perform(get("/api/years/{id}", year.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(year.getId().intValue()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()));
    }

    @Test
    @Transactional
    public void getAllYearsByDateIsEqualToSomething() throws Exception {
        // Initialize the database
        yearRepository.saveAndFlush(year);

        // Get all the yearList where date equals to DEFAULT_DATE
        defaultYearShouldBeFound("date.equals=" + DEFAULT_DATE);

        // Get all the yearList where date equals to UPDATED_DATE
        defaultYearShouldNotBeFound("date.equals=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllYearsByDateIsInShouldWork() throws Exception {
        // Initialize the database
        yearRepository.saveAndFlush(year);

        // Get all the yearList where date in DEFAULT_DATE or UPDATED_DATE
        defaultYearShouldBeFound("date.in=" + DEFAULT_DATE + "," + UPDATED_DATE);

        // Get all the yearList where date equals to UPDATED_DATE
        defaultYearShouldNotBeFound("date.in=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllYearsByDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        yearRepository.saveAndFlush(year);

        // Get all the yearList where date is not null
        defaultYearShouldBeFound("date.specified=true");

        // Get all the yearList where date is null
        defaultYearShouldNotBeFound("date.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultYearShouldBeFound(String filter) throws Exception {
        restYearMockMvc.perform(get("/api/years?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(year.getId().intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultYearShouldNotBeFound(String filter) throws Exception {
        restYearMockMvc.perform(get("/api/years?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    @Transactional
    public void getNonExistingYear() throws Exception {
        // Get the year
        restYearMockMvc.perform(get("/api/years/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateYear() throws Exception {
        // Initialize the database
        yearService.save(year);

        int databaseSizeBeforeUpdate = yearRepository.findAll().size();

        // Update the year
        Year updatedYear = yearRepository.findById(year.getId()).get();
        // Disconnect from session so that the updates on updatedYear are not directly saved in db
        em.detach(updatedYear);
        updatedYear
            .date(UPDATED_DATE);

        restYearMockMvc.perform(put("/api/years")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedYear)))
            .andExpect(status().isOk());

        // Validate the Year in the database
        List<Year> yearList = yearRepository.findAll();
        assertThat(yearList).hasSize(databaseSizeBeforeUpdate);
        Year testYear = yearList.get(yearList.size() - 1);
        assertThat(testYear.getDate()).isEqualTo(UPDATED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingYear() throws Exception {
        int databaseSizeBeforeUpdate = yearRepository.findAll().size();

        // Create the Year

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restYearMockMvc.perform(put("/api/years")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(year)))
            .andExpect(status().isBadRequest());

        // Validate the Year in the database
        List<Year> yearList = yearRepository.findAll();
        assertThat(yearList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteYear() throws Exception {
        // Initialize the database
        yearService.save(year);

        int databaseSizeBeforeDelete = yearRepository.findAll().size();

        // Get the year
        restYearMockMvc.perform(delete("/api/years/{id}", year.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Year> yearList = yearRepository.findAll();
        assertThat(yearList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Year.class);
        Year year1 = new Year();
        year1.setId(1L);
        Year year2 = new Year();
        year2.setId(year1.getId());
        assertThat(year1).isEqualTo(year2);
        year2.setId(2L);
        assertThat(year1).isNotEqualTo(year2);
        year1.setId(null);
        assertThat(year1).isNotEqualTo(year2);
    }
}
