package br.com.ibict.web.rest;

import br.com.ibict.VisaoApp;

import br.com.ibict.domain.Chart;
import br.com.ibict.domain.User;
import br.com.ibict.domain.User;
import br.com.ibict.repository.ChartRepository;
import br.com.ibict.service.ChartService;
import br.com.ibict.web.rest.errors.ExceptionTranslator;
import br.com.ibict.service.dto.ChartCriteria;
import br.com.ibict.service.ChartQueryService;

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


import static br.com.ibict.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.ibict.domain.enumeration.Permission;
/**
 * Test class for the ChartResource REST controller.
 *
 * @see ChartResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = VisaoApp.class)
public class ChartResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Boolean DEFAULT_EXTERNAL = false;
    private static final Boolean UPDATED_EXTERNAL = true;

    private static final String DEFAULT_HTML = "AAAAAAAAAA";
    private static final String UPDATED_HTML = "BBBBBBBBBB";

    private static final Permission DEFAULT_PERMISSION = Permission.PUBLIC;
    private static final Permission UPDATED_PERMISSION = Permission.PRIVATE;

    @Autowired
    private ChartRepository chartRepository;
    @Mock
    private ChartRepository chartRepositoryMock;
    
    @Mock
    private ChartService chartServiceMock;

    @Autowired
    private ChartService chartService;

    @Autowired
    private ChartQueryService chartQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restChartMockMvc;

    private Chart chart;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ChartResource chartResource = new ChartResource(chartService, chartQueryService);
        this.restChartMockMvc = MockMvcBuilders.standaloneSetup(chartResource)
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
    public static Chart createEntity(EntityManager em) {
        Chart chart = new Chart()
            .name(DEFAULT_NAME)
            .external(DEFAULT_EXTERNAL)
            .html(DEFAULT_HTML)
            .permission(DEFAULT_PERMISSION);
        return chart;
    }

    @Before
    public void initTest() {
        chart = createEntity(em);
    }

    @Test
    @Transactional
    public void createChart() throws Exception {
        int databaseSizeBeforeCreate = chartRepository.findAll().size();

        // Create the Chart
        restChartMockMvc.perform(post("/api/charts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(chart)))
            .andExpect(status().isCreated());

        // Validate the Chart in the database
        List<Chart> chartList = chartRepository.findAll();
        assertThat(chartList).hasSize(databaseSizeBeforeCreate + 1);
        Chart testChart = chartList.get(chartList.size() - 1);
        assertThat(testChart.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testChart.isExternal()).isEqualTo(DEFAULT_EXTERNAL);
        assertThat(testChart.getHtml()).isEqualTo(DEFAULT_HTML);
        assertThat(testChart.getPermission()).isEqualTo(DEFAULT_PERMISSION);
    }

    @Test
    @Transactional
    public void createChartWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = chartRepository.findAll().size();

        // Create the Chart with an existing ID
        chart.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restChartMockMvc.perform(post("/api/charts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(chart)))
            .andExpect(status().isBadRequest());

        // Validate the Chart in the database
        List<Chart> chartList = chartRepository.findAll();
        assertThat(chartList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCharts() throws Exception {
        // Initialize the database
        chartRepository.saveAndFlush(chart);

        // Get all the chartList
        restChartMockMvc.perform(get("/api/charts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(chart.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].external").value(hasItem(DEFAULT_EXTERNAL.booleanValue())))
            .andExpect(jsonPath("$.[*].html").value(hasItem(DEFAULT_HTML.toString())))
            .andExpect(jsonPath("$.[*].permission").value(hasItem(DEFAULT_PERMISSION.toString())));
    }
    
    public void getAllChartsWithEagerRelationshipsIsEnabled() throws Exception {
        ChartResource chartResource = new ChartResource(chartServiceMock, chartQueryService);
        when(chartServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restChartMockMvc = MockMvcBuilders.standaloneSetup(chartResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restChartMockMvc.perform(get("/api/charts?eagerload=true"))
        .andExpect(status().isOk());

        verify(chartServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    public void getAllChartsWithEagerRelationshipsIsNotEnabled() throws Exception {
        ChartResource chartResource = new ChartResource(chartServiceMock, chartQueryService);
            when(chartServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restChartMockMvc = MockMvcBuilders.standaloneSetup(chartResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restChartMockMvc.perform(get("/api/charts?eagerload=true"))
        .andExpect(status().isOk());

            verify(chartServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getChart() throws Exception {
        // Initialize the database
        chartRepository.saveAndFlush(chart);

        // Get the chart
        restChartMockMvc.perform(get("/api/charts/{id}", chart.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(chart.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.external").value(DEFAULT_EXTERNAL.booleanValue()))
            .andExpect(jsonPath("$.html").value(DEFAULT_HTML.toString()))
            .andExpect(jsonPath("$.permission").value(DEFAULT_PERMISSION.toString()));
    }

    @Test
    @Transactional
    public void getAllChartsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        chartRepository.saveAndFlush(chart);

        // Get all the chartList where name equals to DEFAULT_NAME
        defaultChartShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the chartList where name equals to UPDATED_NAME
        defaultChartShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllChartsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        chartRepository.saveAndFlush(chart);

        // Get all the chartList where name in DEFAULT_NAME or UPDATED_NAME
        defaultChartShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the chartList where name equals to UPDATED_NAME
        defaultChartShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllChartsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        chartRepository.saveAndFlush(chart);

        // Get all the chartList where name is not null
        defaultChartShouldBeFound("name.specified=true");

        // Get all the chartList where name is null
        defaultChartShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllChartsByExternalIsEqualToSomething() throws Exception {
        // Initialize the database
        chartRepository.saveAndFlush(chart);

        // Get all the chartList where external equals to DEFAULT_EXTERNAL
        defaultChartShouldBeFound("external.equals=" + DEFAULT_EXTERNAL);

        // Get all the chartList where external equals to UPDATED_EXTERNAL
        defaultChartShouldNotBeFound("external.equals=" + UPDATED_EXTERNAL);
    }

    @Test
    @Transactional
    public void getAllChartsByExternalIsInShouldWork() throws Exception {
        // Initialize the database
        chartRepository.saveAndFlush(chart);

        // Get all the chartList where external in DEFAULT_EXTERNAL or UPDATED_EXTERNAL
        defaultChartShouldBeFound("external.in=" + DEFAULT_EXTERNAL + "," + UPDATED_EXTERNAL);

        // Get all the chartList where external equals to UPDATED_EXTERNAL
        defaultChartShouldNotBeFound("external.in=" + UPDATED_EXTERNAL);
    }

    @Test
    @Transactional
    public void getAllChartsByExternalIsNullOrNotNull() throws Exception {
        // Initialize the database
        chartRepository.saveAndFlush(chart);

        // Get all the chartList where external is not null
        defaultChartShouldBeFound("external.specified=true");

        // Get all the chartList where external is null
        defaultChartShouldNotBeFound("external.specified=false");
    }

    @Test
    @Transactional
    public void getAllChartsByPermissionIsEqualToSomething() throws Exception {
        // Initialize the database
        chartRepository.saveAndFlush(chart);

        // Get all the chartList where permission equals to DEFAULT_PERMISSION
        defaultChartShouldBeFound("permission.equals=" + DEFAULT_PERMISSION);

        // Get all the chartList where permission equals to UPDATED_PERMISSION
        defaultChartShouldNotBeFound("permission.equals=" + UPDATED_PERMISSION);
    }

    @Test
    @Transactional
    public void getAllChartsByPermissionIsInShouldWork() throws Exception {
        // Initialize the database
        chartRepository.saveAndFlush(chart);

        // Get all the chartList where permission in DEFAULT_PERMISSION or UPDATED_PERMISSION
        defaultChartShouldBeFound("permission.in=" + DEFAULT_PERMISSION + "," + UPDATED_PERMISSION);

        // Get all the chartList where permission equals to UPDATED_PERMISSION
        defaultChartShouldNotBeFound("permission.in=" + UPDATED_PERMISSION);
    }

    @Test
    @Transactional
    public void getAllChartsByPermissionIsNullOrNotNull() throws Exception {
        // Initialize the database
        chartRepository.saveAndFlush(chart);

        // Get all the chartList where permission is not null
        defaultChartShouldBeFound("permission.specified=true");

        // Get all the chartList where permission is null
        defaultChartShouldNotBeFound("permission.specified=false");
    }

    @Test
    @Transactional
    public void getAllChartsByOwnerIsEqualToSomething() throws Exception {
        // Initialize the database
        User owner = UserResourceIntTest.createEntity(em);
        em.persist(owner);
        em.flush();
        chart.setOwner(owner);
        chartRepository.saveAndFlush(chart);
        Long ownerId = owner.getId();

        // Get all the chartList where owner equals to ownerId
        defaultChartShouldBeFound("ownerId.equals=" + ownerId);

        // Get all the chartList where owner equals to ownerId + 1
        defaultChartShouldNotBeFound("ownerId.equals=" + (ownerId + 1));
    }


    @Test
    @Transactional
    public void getAllChartsBySharedIsEqualToSomething() throws Exception {
        // Initialize the database
        User shared = UserResourceIntTest.createEntity(em);
        em.persist(shared);
        em.flush();
        chart.addShared(shared);
        chartRepository.saveAndFlush(chart);
        Long sharedId = shared.getId();

        // Get all the chartList where shared equals to sharedId
        defaultChartShouldBeFound("sharedId.equals=" + sharedId);

        // Get all the chartList where shared equals to sharedId + 1
        defaultChartShouldNotBeFound("sharedId.equals=" + (sharedId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultChartShouldBeFound(String filter) throws Exception {
        restChartMockMvc.perform(get("/api/charts?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(chart.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].external").value(hasItem(DEFAULT_EXTERNAL.booleanValue())))
            .andExpect(jsonPath("$.[*].html").value(hasItem(DEFAULT_HTML.toString())))
            .andExpect(jsonPath("$.[*].permission").value(hasItem(DEFAULT_PERMISSION.toString())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultChartShouldNotBeFound(String filter) throws Exception {
        restChartMockMvc.perform(get("/api/charts?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    @Transactional
    public void getNonExistingChart() throws Exception {
        // Get the chart
        restChartMockMvc.perform(get("/api/charts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateChart() throws Exception {
        // Initialize the database
        chartService.save(chart);

        int databaseSizeBeforeUpdate = chartRepository.findAll().size();

        // Update the chart
        Chart updatedChart = chartRepository.findById(chart.getId()).get();
        // Disconnect from session so that the updates on updatedChart are not directly saved in db
        em.detach(updatedChart);
        updatedChart
            .name(UPDATED_NAME)
            .external(UPDATED_EXTERNAL)
            .html(UPDATED_HTML)
            .permission(UPDATED_PERMISSION);

        restChartMockMvc.perform(put("/api/charts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedChart)))
            .andExpect(status().isOk());

        // Validate the Chart in the database
        List<Chart> chartList = chartRepository.findAll();
        assertThat(chartList).hasSize(databaseSizeBeforeUpdate);
        Chart testChart = chartList.get(chartList.size() - 1);
        assertThat(testChart.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testChart.isExternal()).isEqualTo(UPDATED_EXTERNAL);
        assertThat(testChart.getHtml()).isEqualTo(UPDATED_HTML);
        assertThat(testChart.getPermission()).isEqualTo(UPDATED_PERMISSION);
    }

    @Test
    @Transactional
    public void updateNonExistingChart() throws Exception {
        int databaseSizeBeforeUpdate = chartRepository.findAll().size();

        // Create the Chart

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restChartMockMvc.perform(put("/api/charts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(chart)))
            .andExpect(status().isBadRequest());

        // Validate the Chart in the database
        List<Chart> chartList = chartRepository.findAll();
        assertThat(chartList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteChart() throws Exception {
        // Initialize the database
        chartService.save(chart);

        int databaseSizeBeforeDelete = chartRepository.findAll().size();

        // Get the chart
        restChartMockMvc.perform(delete("/api/charts/{id}", chart.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Chart> chartList = chartRepository.findAll();
        assertThat(chartList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Chart.class);
        Chart chart1 = new Chart();
        chart1.setId(1L);
        Chart chart2 = new Chart();
        chart2.setId(chart1.getId());
        assertThat(chart1).isEqualTo(chart2);
        chart2.setId(2L);
        assertThat(chart1).isNotEqualTo(chart2);
        chart1.setId(null);
        assertThat(chart1).isNotEqualTo(chart2);
    }
}
