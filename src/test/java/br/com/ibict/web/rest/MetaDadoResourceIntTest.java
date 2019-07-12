package br.com.ibict.web.rest;

import br.com.ibict.VisaoApp;

import br.com.ibict.domain.MetaDado;
import br.com.ibict.domain.Indicator;
import br.com.ibict.domain.GrupIndicator;
import br.com.ibict.domain.GeographicFilter;
import br.com.ibict.domain.Layer;
import br.com.ibict.domain.GroupLayer;
import br.com.ibict.repository.MetaDadoRepository;
import br.com.ibict.service.MetaDadoService;
import br.com.ibict.web.rest.errors.ExceptionTranslator;
import br.com.ibict.service.dto.MetaDadoCriteria;
import br.com.ibict.service.MetaDadoQueryService;

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
 * Test class for the MetaDadoResource REST controller.
 *
 * @see MetaDadoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = VisaoApp.class)
public class MetaDadoResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    @Autowired
    private MetaDadoRepository metaDadoRepository;

    

    @Autowired
    private MetaDadoService metaDadoService;

    @Autowired
    private MetaDadoQueryService metaDadoQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMetaDadoMockMvc;

    private MetaDado metaDado;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MetaDadoResource metaDadoResource = new MetaDadoResource(metaDadoService, metaDadoQueryService);
        this.restMetaDadoMockMvc = MockMvcBuilders.standaloneSetup(metaDadoResource)
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
    public static MetaDado createEntity(EntityManager em) {
        MetaDado metaDado = new MetaDado()
            .name(DEFAULT_NAME)
            .value(DEFAULT_VALUE);
        return metaDado;
    }

    @Before
    public void initTest() {
        metaDado = createEntity(em);
    }

    @Test
    @Transactional
    public void createMetaDado() throws Exception {
        int databaseSizeBeforeCreate = metaDadoRepository.findAll().size();

        // Create the MetaDado
        restMetaDadoMockMvc.perform(post("/api/meta-dados")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(metaDado)))
            .andExpect(status().isCreated());

        // Validate the MetaDado in the database
        List<MetaDado> metaDadoList = metaDadoRepository.findAll();
        assertThat(metaDadoList).hasSize(databaseSizeBeforeCreate + 1);
        MetaDado testMetaDado = metaDadoList.get(metaDadoList.size() - 1);
        assertThat(testMetaDado.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testMetaDado.getValue()).isEqualTo(DEFAULT_VALUE);
    }

    @Test
    @Transactional
    public void createMetaDadoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = metaDadoRepository.findAll().size();

        // Create the MetaDado with an existing ID
        metaDado.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMetaDadoMockMvc.perform(post("/api/meta-dados")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(metaDado)))
            .andExpect(status().isBadRequest());

        // Validate the MetaDado in the database
        List<MetaDado> metaDadoList = metaDadoRepository.findAll();
        assertThat(metaDadoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = metaDadoRepository.findAll().size();
        // set the field null
        metaDado.setName(null);

        // Create the MetaDado, which fails.

        restMetaDadoMockMvc.perform(post("/api/meta-dados")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(metaDado)))
            .andExpect(status().isBadRequest());

        List<MetaDado> metaDadoList = metaDadoRepository.findAll();
        assertThat(metaDadoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = metaDadoRepository.findAll().size();
        // set the field null
        metaDado.setValue(null);

        // Create the MetaDado, which fails.

        restMetaDadoMockMvc.perform(post("/api/meta-dados")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(metaDado)))
            .andExpect(status().isBadRequest());

        List<MetaDado> metaDadoList = metaDadoRepository.findAll();
        assertThat(metaDadoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMetaDados() throws Exception {
        // Initialize the database
        metaDadoRepository.saveAndFlush(metaDado);

        // Get all the metaDadoList
        restMetaDadoMockMvc.perform(get("/api/meta-dados?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(metaDado.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.toString())));
    }
    

    @Test
    @Transactional
    public void getMetaDado() throws Exception {
        // Initialize the database
        metaDadoRepository.saveAndFlush(metaDado);

        // Get the metaDado
        restMetaDadoMockMvc.perform(get("/api/meta-dados/{id}", metaDado.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(metaDado.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.toString()));
    }

    @Test
    @Transactional
    public void getAllMetaDadosByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        metaDadoRepository.saveAndFlush(metaDado);

        // Get all the metaDadoList where name equals to DEFAULT_NAME
        defaultMetaDadoShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the metaDadoList where name equals to UPDATED_NAME
        defaultMetaDadoShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllMetaDadosByNameIsInShouldWork() throws Exception {
        // Initialize the database
        metaDadoRepository.saveAndFlush(metaDado);

        // Get all the metaDadoList where name in DEFAULT_NAME or UPDATED_NAME
        defaultMetaDadoShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the metaDadoList where name equals to UPDATED_NAME
        defaultMetaDadoShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllMetaDadosByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        metaDadoRepository.saveAndFlush(metaDado);

        // Get all the metaDadoList where name is not null
        defaultMetaDadoShouldBeFound("name.specified=true");

        // Get all the metaDadoList where name is null
        defaultMetaDadoShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllMetaDadosByValueIsEqualToSomething() throws Exception {
        // Initialize the database
        metaDadoRepository.saveAndFlush(metaDado);

        // Get all the metaDadoList where value equals to DEFAULT_VALUE
        defaultMetaDadoShouldBeFound("value.equals=" + DEFAULT_VALUE);

        // Get all the metaDadoList where value equals to UPDATED_VALUE
        defaultMetaDadoShouldNotBeFound("value.equals=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void getAllMetaDadosByValueIsInShouldWork() throws Exception {
        // Initialize the database
        metaDadoRepository.saveAndFlush(metaDado);

        // Get all the metaDadoList where value in DEFAULT_VALUE or UPDATED_VALUE
        defaultMetaDadoShouldBeFound("value.in=" + DEFAULT_VALUE + "," + UPDATED_VALUE);

        // Get all the metaDadoList where value equals to UPDATED_VALUE
        defaultMetaDadoShouldNotBeFound("value.in=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void getAllMetaDadosByValueIsNullOrNotNull() throws Exception {
        // Initialize the database
        metaDadoRepository.saveAndFlush(metaDado);

        // Get all the metaDadoList where value is not null
        defaultMetaDadoShouldBeFound("value.specified=true");

        // Get all the metaDadoList where value is null
        defaultMetaDadoShouldNotBeFound("value.specified=false");
    }

    @Test
    @Transactional
    public void getAllMetaDadosByIndicatorIsEqualToSomething() throws Exception {
        // Initialize the database
        Indicator indicator = IndicatorResourceIntTest.createEntity(em);
        em.persist(indicator);
        em.flush();
        metaDado.setIndicator(indicator);
        metaDadoRepository.saveAndFlush(metaDado);
        Long indicatorId = indicator.getId();

        // Get all the metaDadoList where indicator equals to indicatorId
        defaultMetaDadoShouldBeFound("indicatorId.equals=" + indicatorId);

        // Get all the metaDadoList where indicator equals to indicatorId + 1
        defaultMetaDadoShouldNotBeFound("indicatorId.equals=" + (indicatorId + 1));
    }


    @Test
    @Transactional
    public void getAllMetaDadosByGrupIndicatorIsEqualToSomething() throws Exception {
        // Initialize the database
        GrupIndicator grupIndicator = GrupIndicatorResourceIntTest.createEntity(em);
        em.persist(grupIndicator);
        em.flush();
        metaDado.setGrupIndicator(grupIndicator);
        metaDadoRepository.saveAndFlush(metaDado);
        Long grupIndicatorId = grupIndicator.getId();

        // Get all the metaDadoList where grupIndicator equals to grupIndicatorId
        defaultMetaDadoShouldBeFound("grupIndicatorId.equals=" + grupIndicatorId);

        // Get all the metaDadoList where grupIndicator equals to grupIndicatorId + 1
        defaultMetaDadoShouldNotBeFound("grupIndicatorId.equals=" + (grupIndicatorId + 1));
    }


    @Test
    @Transactional
    public void getAllMetaDadosByGeographicFilterIsEqualToSomething() throws Exception {
        // Initialize the database
        GeographicFilter geographicFilter = GeographicFilterResourceIntTest.createEntity(em);
        em.persist(geographicFilter);
        em.flush();
        metaDado.setGeographicFilter(geographicFilter);
        metaDadoRepository.saveAndFlush(metaDado);
        Long geographicFilterId = geographicFilter.getId();

        // Get all the metaDadoList where geographicFilter equals to geographicFilterId
        defaultMetaDadoShouldBeFound("geographicFilterId.equals=" + geographicFilterId);

        // Get all the metaDadoList where geographicFilter equals to geographicFilterId + 1
        defaultMetaDadoShouldNotBeFound("geographicFilterId.equals=" + (geographicFilterId + 1));
    }


    @Test
    @Transactional
    public void getAllMetaDadosByLayerIsEqualToSomething() throws Exception {
        // Initialize the database
        Layer layer = LayerResourceIntTest.createEntity(em);
        em.persist(layer);
        em.flush();
        metaDado.setLayer(layer);
        metaDadoRepository.saveAndFlush(metaDado);
        Long layerId = layer.getId();

        // Get all the metaDadoList where layer equals to layerId
        defaultMetaDadoShouldBeFound("layerId.equals=" + layerId);

        // Get all the metaDadoList where layer equals to layerId + 1
        defaultMetaDadoShouldNotBeFound("layerId.equals=" + (layerId + 1));
    }


    @Test
    @Transactional
    public void getAllMetaDadosByGroupLayerIsEqualToSomething() throws Exception {
        // Initialize the database
        GroupLayer groupLayer = GroupLayerResourceIntTest.createEntity(em);
        em.persist(groupLayer);
        em.flush();
        metaDado.setGroupLayer(groupLayer);
        metaDadoRepository.saveAndFlush(metaDado);
        Long groupLayerId = groupLayer.getId();

        // Get all the metaDadoList where groupLayer equals to groupLayerId
        defaultMetaDadoShouldBeFound("groupLayerId.equals=" + groupLayerId);

        // Get all the metaDadoList where groupLayer equals to groupLayerId + 1
        defaultMetaDadoShouldNotBeFound("groupLayerId.equals=" + (groupLayerId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultMetaDadoShouldBeFound(String filter) throws Exception {
        restMetaDadoMockMvc.perform(get("/api/meta-dados?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(metaDado.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.toString())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultMetaDadoShouldNotBeFound(String filter) throws Exception {
        restMetaDadoMockMvc.perform(get("/api/meta-dados?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    @Transactional
    public void getNonExistingMetaDado() throws Exception {
        // Get the metaDado
        restMetaDadoMockMvc.perform(get("/api/meta-dados/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMetaDado() throws Exception {
        // Initialize the database
        metaDadoService.save(metaDado);

        int databaseSizeBeforeUpdate = metaDadoRepository.findAll().size();

        // Update the metaDado
        MetaDado updatedMetaDado = metaDadoRepository.findById(metaDado.getId()).get();
        // Disconnect from session so that the updates on updatedMetaDado are not directly saved in db
        em.detach(updatedMetaDado);
        updatedMetaDado
            .name(UPDATED_NAME)
            .value(UPDATED_VALUE);

        restMetaDadoMockMvc.perform(put("/api/meta-dados")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMetaDado)))
            .andExpect(status().isOk());

        // Validate the MetaDado in the database
        List<MetaDado> metaDadoList = metaDadoRepository.findAll();
        assertThat(metaDadoList).hasSize(databaseSizeBeforeUpdate);
        MetaDado testMetaDado = metaDadoList.get(metaDadoList.size() - 1);
        assertThat(testMetaDado.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testMetaDado.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void updateNonExistingMetaDado() throws Exception {
        int databaseSizeBeforeUpdate = metaDadoRepository.findAll().size();

        // Create the MetaDado

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMetaDadoMockMvc.perform(put("/api/meta-dados")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(metaDado)))
            .andExpect(status().isBadRequest());

        // Validate the MetaDado in the database
        List<MetaDado> metaDadoList = metaDadoRepository.findAll();
        assertThat(metaDadoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMetaDado() throws Exception {
        // Initialize the database
        metaDadoService.save(metaDado);

        int databaseSizeBeforeDelete = metaDadoRepository.findAll().size();

        // Get the metaDado
        restMetaDadoMockMvc.perform(delete("/api/meta-dados/{id}", metaDado.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<MetaDado> metaDadoList = metaDadoRepository.findAll();
        assertThat(metaDadoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MetaDado.class);
        MetaDado metaDado1 = new MetaDado();
        metaDado1.setId(1L);
        MetaDado metaDado2 = new MetaDado();
        metaDado2.setId(metaDado1.getId());
        assertThat(metaDado1).isEqualTo(metaDado2);
        metaDado2.setId(2L);
        assertThat(metaDado1).isNotEqualTo(metaDado2);
        metaDado1.setId(null);
        assertThat(metaDado1).isNotEqualTo(metaDado2);
    }
}
