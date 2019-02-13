package br.com.ibict.visao.web.rest;

import br.com.ibict.visao.VisaoApp;

import br.com.ibict.visao.domain.Layer;
import br.com.ibict.visao.domain.Category;
import br.com.ibict.visao.domain.MarkerIcon;
import br.com.ibict.visao.domain.GroupLayer;
import br.com.ibict.visao.repository.LayerRepository;
import br.com.ibict.visao.service.LayerService;
import br.com.ibict.visao.web.rest.errors.ExceptionTranslator;
import br.com.ibict.visao.service.dto.LayerCriteria;
import br.com.ibict.visao.service.LayerQueryService;

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
import org.springframework.util.Base64Utils;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;


import static br.com.ibict.visao.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.ibict.visao.domain.enumeration.TypeLayer;
/**
 * Test class for the LayerResource REST controller.
 *
 * @see LayerResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = VisaoApp.class)
public class LayerResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_GEO_JSON = "AAAAAAAAAA";
    private static final String UPDATED_GEO_JSON = "BBBBBBBBBB";

    private static final TypeLayer DEFAULT_TYPE = TypeLayer.MARKER;
    private static final TypeLayer UPDATED_TYPE = TypeLayer.CIRCLE;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Instant DEFAULT_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_SOURCE = "AAAAAAAAAA";
    private static final String UPDATED_SOURCE = "BBBBBBBBBB";

    private static final Instant DEFAULT_DATE_CHANGE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_CHANGE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_NOTE = "AAAAAAAAAA";
    private static final String UPDATED_NOTE = "BBBBBBBBBB";

    @Autowired
    private LayerRepository layerRepository;

    

    @Autowired
    private LayerService layerService;

    @Autowired
    private LayerQueryService layerQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restLayerMockMvc;

    private Layer layer;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LayerResource layerResource = new LayerResource(layerService, layerQueryService);
        this.restLayerMockMvc = MockMvcBuilders.standaloneSetup(layerResource)
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
    public static Layer createEntity(EntityManager em) {
        Layer layer = new Layer()
            .name(DEFAULT_NAME)
            .geoJson(DEFAULT_GEO_JSON)
            .type(DEFAULT_TYPE)
            .description(DEFAULT_DESCRIPTION)
            .date(DEFAULT_DATE)
            .source(DEFAULT_SOURCE)
            .dateChange(DEFAULT_DATE_CHANGE)
            .note(DEFAULT_NOTE);
        return layer;
    }

    @Before
    public void initTest() {
        layer = createEntity(em);
    }

    @Test
    @Transactional
    public void createLayer() throws Exception {
        int databaseSizeBeforeCreate = layerRepository.findAll().size();

        // Create the Layer
        restLayerMockMvc.perform(post("/api/layers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(layer)))
            .andExpect(status().isCreated());

        // Validate the Layer in the database
        List<Layer> layerList = layerRepository.findAll();
        assertThat(layerList).hasSize(databaseSizeBeforeCreate + 1);
        Layer testLayer = layerList.get(layerList.size() - 1);
        assertThat(testLayer.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testLayer.getGeoJson()).isEqualTo(DEFAULT_GEO_JSON);
        assertThat(testLayer.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testLayer.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testLayer.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testLayer.getSource()).isEqualTo(DEFAULT_SOURCE);
        assertThat(testLayer.getDateChange()).isEqualTo(DEFAULT_DATE_CHANGE);
        assertThat(testLayer.getNote()).isEqualTo(DEFAULT_NOTE);
    }

    @Test
    @Transactional
    public void createLayerWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = layerRepository.findAll().size();

        // Create the Layer with an existing ID
        layer.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLayerMockMvc.perform(post("/api/layers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(layer)))
            .andExpect(status().isBadRequest());

        // Validate the Layer in the database
        List<Layer> layerList = layerRepository.findAll();
        assertThat(layerList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = layerRepository.findAll().size();
        // set the field null
        layer.setName(null);

        // Create the Layer, which fails.

        restLayerMockMvc.perform(post("/api/layers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(layer)))
            .andExpect(status().isBadRequest());

        List<Layer> layerList = layerRepository.findAll();
        assertThat(layerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = layerRepository.findAll().size();
        // set the field null
        layer.setType(null);

        // Create the Layer, which fails.

        restLayerMockMvc.perform(post("/api/layers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(layer)))
            .andExpect(status().isBadRequest());

        List<Layer> layerList = layerRepository.findAll();
        assertThat(layerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLayers() throws Exception {
        // Initialize the database
        layerRepository.saveAndFlush(layer);

        // Get all the layerList
        restLayerMockMvc.perform(get("/api/layers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(layer.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].geoJson").value(hasItem(DEFAULT_GEO_JSON.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].source").value(hasItem(DEFAULT_SOURCE.toString())))
            .andExpect(jsonPath("$.[*].dateChange").value(hasItem(DEFAULT_DATE_CHANGE.toString())))
            .andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE.toString())));
    }
    

    @Test
    @Transactional
    public void getLayer() throws Exception {
        // Initialize the database
        layerRepository.saveAndFlush(layer);

        // Get the layer
        restLayerMockMvc.perform(get("/api/layers/{id}", layer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(layer.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.geoJson").value(DEFAULT_GEO_JSON.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.source").value(DEFAULT_SOURCE.toString()))
            .andExpect(jsonPath("$.dateChange").value(DEFAULT_DATE_CHANGE.toString()))
            .andExpect(jsonPath("$.note").value(DEFAULT_NOTE.toString()));
    }

    @Test
    @Transactional
    public void getAllLayersByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        layerRepository.saveAndFlush(layer);

        // Get all the layerList where name equals to DEFAULT_NAME
        defaultLayerShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the layerList where name equals to UPDATED_NAME
        defaultLayerShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllLayersByNameIsInShouldWork() throws Exception {
        // Initialize the database
        layerRepository.saveAndFlush(layer);

        // Get all the layerList where name in DEFAULT_NAME or UPDATED_NAME
        defaultLayerShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the layerList where name equals to UPDATED_NAME
        defaultLayerShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllLayersByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        layerRepository.saveAndFlush(layer);

        // Get all the layerList where name is not null
        defaultLayerShouldBeFound("name.specified=true");

        // Get all the layerList where name is null
        defaultLayerShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllLayersByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        layerRepository.saveAndFlush(layer);

        // Get all the layerList where type equals to DEFAULT_TYPE
        defaultLayerShouldBeFound("type.equals=" + DEFAULT_TYPE);

        // Get all the layerList where type equals to UPDATED_TYPE
        defaultLayerShouldNotBeFound("type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllLayersByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        layerRepository.saveAndFlush(layer);

        // Get all the layerList where type in DEFAULT_TYPE or UPDATED_TYPE
        defaultLayerShouldBeFound("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE);

        // Get all the layerList where type equals to UPDATED_TYPE
        defaultLayerShouldNotBeFound("type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllLayersByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        layerRepository.saveAndFlush(layer);

        // Get all the layerList where type is not null
        defaultLayerShouldBeFound("type.specified=true");

        // Get all the layerList where type is null
        defaultLayerShouldNotBeFound("type.specified=false");
    }

    @Test
    @Transactional
    public void getAllLayersByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        layerRepository.saveAndFlush(layer);

        // Get all the layerList where description equals to DEFAULT_DESCRIPTION
        defaultLayerShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the layerList where description equals to UPDATED_DESCRIPTION
        defaultLayerShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllLayersByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        layerRepository.saveAndFlush(layer);

        // Get all the layerList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultLayerShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the layerList where description equals to UPDATED_DESCRIPTION
        defaultLayerShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllLayersByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        layerRepository.saveAndFlush(layer);

        // Get all the layerList where description is not null
        defaultLayerShouldBeFound("description.specified=true");

        // Get all the layerList where description is null
        defaultLayerShouldNotBeFound("description.specified=false");
    }

    @Test
    @Transactional
    public void getAllLayersByDateIsEqualToSomething() throws Exception {
        // Initialize the database
        layerRepository.saveAndFlush(layer);

        // Get all the layerList where date equals to DEFAULT_DATE
        defaultLayerShouldBeFound("date.equals=" + DEFAULT_DATE);

        // Get all the layerList where date equals to UPDATED_DATE
        defaultLayerShouldNotBeFound("date.equals=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllLayersByDateIsInShouldWork() throws Exception {
        // Initialize the database
        layerRepository.saveAndFlush(layer);

        // Get all the layerList where date in DEFAULT_DATE or UPDATED_DATE
        defaultLayerShouldBeFound("date.in=" + DEFAULT_DATE + "," + UPDATED_DATE);

        // Get all the layerList where date equals to UPDATED_DATE
        defaultLayerShouldNotBeFound("date.in=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllLayersByDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        layerRepository.saveAndFlush(layer);

        // Get all the layerList where date is not null
        defaultLayerShouldBeFound("date.specified=true");

        // Get all the layerList where date is null
        defaultLayerShouldNotBeFound("date.specified=false");
    }

    @Test
    @Transactional
    public void getAllLayersBySourceIsEqualToSomething() throws Exception {
        // Initialize the database
        layerRepository.saveAndFlush(layer);

        // Get all the layerList where source equals to DEFAULT_SOURCE
        defaultLayerShouldBeFound("source.equals=" + DEFAULT_SOURCE);

        // Get all the layerList where source equals to UPDATED_SOURCE
        defaultLayerShouldNotBeFound("source.equals=" + UPDATED_SOURCE);
    }

    @Test
    @Transactional
    public void getAllLayersBySourceIsInShouldWork() throws Exception {
        // Initialize the database
        layerRepository.saveAndFlush(layer);

        // Get all the layerList where source in DEFAULT_SOURCE or UPDATED_SOURCE
        defaultLayerShouldBeFound("source.in=" + DEFAULT_SOURCE + "," + UPDATED_SOURCE);

        // Get all the layerList where source equals to UPDATED_SOURCE
        defaultLayerShouldNotBeFound("source.in=" + UPDATED_SOURCE);
    }

    @Test
    @Transactional
    public void getAllLayersBySourceIsNullOrNotNull() throws Exception {
        // Initialize the database
        layerRepository.saveAndFlush(layer);

        // Get all the layerList where source is not null
        defaultLayerShouldBeFound("source.specified=true");

        // Get all the layerList where source is null
        defaultLayerShouldNotBeFound("source.specified=false");
    }

    @Test
    @Transactional
    public void getAllLayersByDateChangeIsEqualToSomething() throws Exception {
        // Initialize the database
        layerRepository.saveAndFlush(layer);

        // Get all the layerList where dateChange equals to DEFAULT_DATE_CHANGE
        defaultLayerShouldBeFound("dateChange.equals=" + DEFAULT_DATE_CHANGE);

        // Get all the layerList where dateChange equals to UPDATED_DATE_CHANGE
        defaultLayerShouldNotBeFound("dateChange.equals=" + UPDATED_DATE_CHANGE);
    }

    @Test
    @Transactional
    public void getAllLayersByDateChangeIsInShouldWork() throws Exception {
        // Initialize the database
        layerRepository.saveAndFlush(layer);

        // Get all the layerList where dateChange in DEFAULT_DATE_CHANGE or UPDATED_DATE_CHANGE
        defaultLayerShouldBeFound("dateChange.in=" + DEFAULT_DATE_CHANGE + "," + UPDATED_DATE_CHANGE);

        // Get all the layerList where dateChange equals to UPDATED_DATE_CHANGE
        defaultLayerShouldNotBeFound("dateChange.in=" + UPDATED_DATE_CHANGE);
    }

    @Test
    @Transactional
    public void getAllLayersByDateChangeIsNullOrNotNull() throws Exception {
        // Initialize the database
        layerRepository.saveAndFlush(layer);

        // Get all the layerList where dateChange is not null
        defaultLayerShouldBeFound("dateChange.specified=true");

        // Get all the layerList where dateChange is null
        defaultLayerShouldNotBeFound("dateChange.specified=false");
    }

    @Test
    @Transactional
    public void getAllLayersByNoteIsEqualToSomething() throws Exception {
        // Initialize the database
        layerRepository.saveAndFlush(layer);

        // Get all the layerList where note equals to DEFAULT_NOTE
        defaultLayerShouldBeFound("note.equals=" + DEFAULT_NOTE);

        // Get all the layerList where note equals to UPDATED_NOTE
        defaultLayerShouldNotBeFound("note.equals=" + UPDATED_NOTE);
    }

    @Test
    @Transactional
    public void getAllLayersByNoteIsInShouldWork() throws Exception {
        // Initialize the database
        layerRepository.saveAndFlush(layer);

        // Get all the layerList where note in DEFAULT_NOTE or UPDATED_NOTE
        defaultLayerShouldBeFound("note.in=" + DEFAULT_NOTE + "," + UPDATED_NOTE);

        // Get all the layerList where note equals to UPDATED_NOTE
        defaultLayerShouldNotBeFound("note.in=" + UPDATED_NOTE);
    }

    @Test
    @Transactional
    public void getAllLayersByNoteIsNullOrNotNull() throws Exception {
        // Initialize the database
        layerRepository.saveAndFlush(layer);

        // Get all the layerList where note is not null
        defaultLayerShouldBeFound("note.specified=true");

        // Get all the layerList where note is null
        defaultLayerShouldNotBeFound("note.specified=false");
    }

    @Test
    @Transactional
    public void getAllLayersByCategoryIsEqualToSomething() throws Exception {
        // Initialize the database
        Category category = CategoryResourceIntTest.createEntity(em);
        em.persist(category);
        em.flush();
        layer.setCategory(category);
        layerRepository.saveAndFlush(layer);
        Long categoryId = category.getId();

        // Get all the layerList where category equals to categoryId
        defaultLayerShouldBeFound("categoryId.equals=" + categoryId);

        // Get all the layerList where category equals to categoryId + 1
        defaultLayerShouldNotBeFound("categoryId.equals=" + (categoryId + 1));
    }


    @Test
    @Transactional
    public void getAllLayersByIconIsEqualToSomething() throws Exception {
        // Initialize the database
        MarkerIcon icon = MarkerIconResourceIntTest.createEntity(em);
        em.persist(icon);
        em.flush();
        layer.setIcon(icon);
        layerRepository.saveAndFlush(layer);
        Long iconId = icon.getId();

        // Get all the layerList where icon equals to iconId
        defaultLayerShouldBeFound("iconId.equals=" + iconId);

        // Get all the layerList where icon equals to iconId + 1
        defaultLayerShouldNotBeFound("iconId.equals=" + (iconId + 1));
    }


    @Test
    @Transactional
    public void getAllLayersByGroupIsEqualToSomething() throws Exception {
        // Initialize the database
        GroupLayer group = GroupLayerResourceIntTest.createEntity(em);
        em.persist(group);
        em.flush();
        layer.setGroup(group);
        layerRepository.saveAndFlush(layer);
        Long groupId = group.getId();

        // Get all the layerList where group equals to groupId
        defaultLayerShouldBeFound("groupId.equals=" + groupId);

        // Get all the layerList where group equals to groupId + 1
        defaultLayerShouldNotBeFound("groupId.equals=" + (groupId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultLayerShouldBeFound(String filter) throws Exception {
        restLayerMockMvc.perform(get("/api/layers?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(layer.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].geoJson").value(hasItem(DEFAULT_GEO_JSON.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].source").value(hasItem(DEFAULT_SOURCE.toString())))
            .andExpect(jsonPath("$.[*].dateChange").value(hasItem(DEFAULT_DATE_CHANGE.toString())))
            .andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE.toString())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultLayerShouldNotBeFound(String filter) throws Exception {
        restLayerMockMvc.perform(get("/api/layers?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    @Transactional
    public void getNonExistingLayer() throws Exception {
        // Get the layer
        restLayerMockMvc.perform(get("/api/layers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLayer() throws Exception {
        // Initialize the database
        layerService.save(layer);

        int databaseSizeBeforeUpdate = layerRepository.findAll().size();

        // Update the layer
        Layer updatedLayer = layerRepository.findById(layer.getId()).get();
        // Disconnect from session so that the updates on updatedLayer are not directly saved in db
        em.detach(updatedLayer);
        updatedLayer
            .name(UPDATED_NAME)
            .geoJson(UPDATED_GEO_JSON)
            .type(UPDATED_TYPE)
            .description(UPDATED_DESCRIPTION)
            .date(UPDATED_DATE)
            .source(UPDATED_SOURCE)
            .dateChange(UPDATED_DATE_CHANGE)
            .note(UPDATED_NOTE);

        restLayerMockMvc.perform(put("/api/layers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedLayer)))
            .andExpect(status().isOk());

        // Validate the Layer in the database
        List<Layer> layerList = layerRepository.findAll();
        assertThat(layerList).hasSize(databaseSizeBeforeUpdate);
        Layer testLayer = layerList.get(layerList.size() - 1);
        assertThat(testLayer.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testLayer.getGeoJson()).isEqualTo(UPDATED_GEO_JSON);
        assertThat(testLayer.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testLayer.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testLayer.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testLayer.getSource()).isEqualTo(UPDATED_SOURCE);
        assertThat(testLayer.getDateChange()).isEqualTo(UPDATED_DATE_CHANGE);
        assertThat(testLayer.getNote()).isEqualTo(UPDATED_NOTE);
    }

    @Test
    @Transactional
    public void updateNonExistingLayer() throws Exception {
        int databaseSizeBeforeUpdate = layerRepository.findAll().size();

        // Create the Layer

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restLayerMockMvc.perform(put("/api/layers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(layer)))
            .andExpect(status().isBadRequest());

        // Validate the Layer in the database
        List<Layer> layerList = layerRepository.findAll();
        assertThat(layerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteLayer() throws Exception {
        // Initialize the database
        layerService.save(layer);

        int databaseSizeBeforeDelete = layerRepository.findAll().size();

        // Get the layer
        restLayerMockMvc.perform(delete("/api/layers/{id}", layer.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Layer> layerList = layerRepository.findAll();
        assertThat(layerList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Layer.class);
        Layer layer1 = new Layer();
        layer1.setId(1L);
        Layer layer2 = new Layer();
        layer2.setId(layer1.getId());
        assertThat(layer1).isEqualTo(layer2);
        layer2.setId(2L);
        assertThat(layer1).isNotEqualTo(layer2);
        layer1.setId(null);
        assertThat(layer1).isNotEqualTo(layer2);
    }
}
