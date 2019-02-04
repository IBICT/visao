package br.com.ibict.visao.web.rest;

import br.com.ibict.visao.VisaoApp;

import br.com.ibict.visao.domain.Indicator;
import br.com.ibict.visao.domain.Region;
import br.com.ibict.visao.domain.Name;
import br.com.ibict.visao.domain.Year;
import br.com.ibict.visao.repository.IndicatorRepository;
import br.com.ibict.visao.service.IndicatorService;
import br.com.ibict.visao.web.rest.errors.ExceptionTranslator;
import br.com.ibict.visao.service.dto.IndicatorCriteria;
import br.com.ibict.visao.service.IndicatorQueryService;

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


import static br.com.ibict.visao.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the IndicatorResource REST controller.
 *
 * @see IndicatorResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = VisaoApp.class)
public class IndicatorResourceIntTest {

    private static final Double DEFAULT_VALUE = 1D;
    private static final Double UPDATED_VALUE = 2D;

    @Autowired
    private IndicatorRepository indicatorRepository;

    

    @Autowired
    private IndicatorService indicatorService;

    @Autowired
    private IndicatorQueryService indicatorQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restIndicatorMockMvc;

    private Indicator indicator;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final IndicatorResource indicatorResource = new IndicatorResource(indicatorService, indicatorQueryService);
        this.restIndicatorMockMvc = MockMvcBuilders.standaloneSetup(indicatorResource)
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
    public static Indicator createEntity(EntityManager em) {
        Indicator indicator = new Indicator()
            .value(DEFAULT_VALUE);
        return indicator;
    }

    @Before
    public void initTest() {
        indicator = createEntity(em);
    }

    @Test
    @Transactional
    public void createIndicator() throws Exception {
        int databaseSizeBeforeCreate = indicatorRepository.findAll().size();

        // Create the Indicator
        restIndicatorMockMvc.perform(post("/api/indicators")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(indicator)))
            .andExpect(status().isCreated());

        // Validate the Indicator in the database
        List<Indicator> indicatorList = indicatorRepository.findAll();
        assertThat(indicatorList).hasSize(databaseSizeBeforeCreate + 1);
        Indicator testIndicator = indicatorList.get(indicatorList.size() - 1);
        assertThat(testIndicator.getValue()).isEqualTo(DEFAULT_VALUE);
    }

    @Test
    @Transactional
    public void createIndicatorWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = indicatorRepository.findAll().size();

        // Create the Indicator with an existing ID
        indicator.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restIndicatorMockMvc.perform(post("/api/indicators")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(indicator)))
            .andExpect(status().isBadRequest());

        // Validate the Indicator in the database
        List<Indicator> indicatorList = indicatorRepository.findAll();
        assertThat(indicatorList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = indicatorRepository.findAll().size();
        // set the field null
        indicator.setValue(null);

        // Create the Indicator, which fails.

        restIndicatorMockMvc.perform(post("/api/indicators")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(indicator)))
            .andExpect(status().isBadRequest());

        List<Indicator> indicatorList = indicatorRepository.findAll();
        assertThat(indicatorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllIndicators() throws Exception {
        // Initialize the database
        indicatorRepository.saveAndFlush(indicator);

        // Get all the indicatorList
        restIndicatorMockMvc.perform(get("/api/indicators?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(indicator.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.doubleValue())));
    }
    

    @Test
    @Transactional
    public void getIndicator() throws Exception {
        // Initialize the database
        indicatorRepository.saveAndFlush(indicator);

        // Get the indicator
        restIndicatorMockMvc.perform(get("/api/indicators/{id}", indicator.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(indicator.getId().intValue()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.doubleValue()));
    }

    @Test
    @Transactional
    public void getAllIndicatorsByValueIsEqualToSomething() throws Exception {
        // Initialize the database
        indicatorRepository.saveAndFlush(indicator);

        // Get all the indicatorList where value equals to DEFAULT_VALUE
        defaultIndicatorShouldBeFound("value.equals=" + DEFAULT_VALUE);

        // Get all the indicatorList where value equals to UPDATED_VALUE
        defaultIndicatorShouldNotBeFound("value.equals=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void getAllIndicatorsByValueIsInShouldWork() throws Exception {
        // Initialize the database
        indicatorRepository.saveAndFlush(indicator);

        // Get all the indicatorList where value in DEFAULT_VALUE or UPDATED_VALUE
        defaultIndicatorShouldBeFound("value.in=" + DEFAULT_VALUE + "," + UPDATED_VALUE);

        // Get all the indicatorList where value equals to UPDATED_VALUE
        defaultIndicatorShouldNotBeFound("value.in=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void getAllIndicatorsByValueIsNullOrNotNull() throws Exception {
        // Initialize the database
        indicatorRepository.saveAndFlush(indicator);

        // Get all the indicatorList where value is not null
        defaultIndicatorShouldBeFound("value.specified=true");

        // Get all the indicatorList where value is null
        defaultIndicatorShouldNotBeFound("value.specified=false");
    }

    @Test
    @Transactional
    public void getAllIndicatorsByRegionIsEqualToSomething() throws Exception {
        // Initialize the database
        Region region = RegionResourceIntTest.createEntity(em);
        em.persist(region);
        em.flush();
        indicator.setRegion(region);
        indicatorRepository.saveAndFlush(indicator);
        Long regionId = region.getId();

        // Get all the indicatorList where region equals to regionId
        defaultIndicatorShouldBeFound("regionId.equals=" + regionId);

        // Get all the indicatorList where region equals to regionId + 1
        defaultIndicatorShouldNotBeFound("regionId.equals=" + (regionId + 1));
    }


    @Test
    @Transactional
    public void getAllIndicatorsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        Name name = NameResourceIntTest.createEntity(em);
        em.persist(name);
        em.flush();
        indicator.setName(name);
        indicatorRepository.saveAndFlush(indicator);
        Long nameId = name.getId();

        // Get all the indicatorList where name equals to nameId
        defaultIndicatorShouldBeFound("nameId.equals=" + nameId);

        // Get all the indicatorList where name equals to nameId + 1
        defaultIndicatorShouldNotBeFound("nameId.equals=" + (nameId + 1));
    }


    @Test
    @Transactional
    public void getAllIndicatorsByAnoIsEqualToSomething() throws Exception {
        // Initialize the database
        Year ano = YearResourceIntTest.createEntity(em);
        em.persist(ano);
        em.flush();
        indicator.setAno(ano);
        indicatorRepository.saveAndFlush(indicator);
        Long anoId = ano.getId();

        // Get all the indicatorList where ano equals to anoId
        defaultIndicatorShouldBeFound("anoId.equals=" + anoId);

        // Get all the indicatorList where ano equals to anoId + 1
        defaultIndicatorShouldNotBeFound("anoId.equals=" + (anoId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultIndicatorShouldBeFound(String filter) throws Exception {
        restIndicatorMockMvc.perform(get("/api/indicators?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(indicator.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.doubleValue())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultIndicatorShouldNotBeFound(String filter) throws Exception {
        restIndicatorMockMvc.perform(get("/api/indicators?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    @Transactional
    public void getNonExistingIndicator() throws Exception {
        // Get the indicator
        restIndicatorMockMvc.perform(get("/api/indicators/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateIndicator() throws Exception {
        // Initialize the database
        indicatorService.save(indicator);

        int databaseSizeBeforeUpdate = indicatorRepository.findAll().size();

        // Update the indicator
        Indicator updatedIndicator = indicatorRepository.findById(indicator.getId()).get();
        // Disconnect from session so that the updates on updatedIndicator are not directly saved in db
        em.detach(updatedIndicator);
        updatedIndicator
            .value(UPDATED_VALUE);

        restIndicatorMockMvc.perform(put("/api/indicators")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedIndicator)))
            .andExpect(status().isOk());

        // Validate the Indicator in the database
        List<Indicator> indicatorList = indicatorRepository.findAll();
        assertThat(indicatorList).hasSize(databaseSizeBeforeUpdate);
        Indicator testIndicator = indicatorList.get(indicatorList.size() - 1);
        assertThat(testIndicator.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void updateNonExistingIndicator() throws Exception {
        int databaseSizeBeforeUpdate = indicatorRepository.findAll().size();

        // Create the Indicator

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restIndicatorMockMvc.perform(put("/api/indicators")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(indicator)))
            .andExpect(status().isBadRequest());

        // Validate the Indicator in the database
        List<Indicator> indicatorList = indicatorRepository.findAll();
        assertThat(indicatorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteIndicator() throws Exception {
        // Initialize the database
        indicatorService.save(indicator);

        int databaseSizeBeforeDelete = indicatorRepository.findAll().size();

        // Get the indicator
        restIndicatorMockMvc.perform(delete("/api/indicators/{id}", indicator.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Indicator> indicatorList = indicatorRepository.findAll();
        assertThat(indicatorList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Indicator.class);
        Indicator indicator1 = new Indicator();
        indicator1.setId(1L);
        Indicator indicator2 = new Indicator();
        indicator2.setId(indicator1.getId());
        assertThat(indicator1).isEqualTo(indicator2);
        indicator2.setId(2L);
        assertThat(indicator1).isNotEqualTo(indicator2);
        indicator1.setId(null);
        assertThat(indicator1).isNotEqualTo(indicator2);
    }
}
