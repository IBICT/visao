package br.com.ibict.visao.web.rest;

import br.com.ibict.visao.VisaoApp;

import br.com.ibict.visao.domain.MetaDado;
import br.com.ibict.visao.domain.Name;
import br.com.ibict.visao.repository.MetaDadoRepository;
import br.com.ibict.visao.service.MetaDadoService;
import br.com.ibict.visao.web.rest.errors.ExceptionTranslator;
import br.com.ibict.visao.service.dto.MetaDadoCriteria;
import br.com.ibict.visao.service.MetaDadoQueryService;

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


import static br.com.ibict.visao.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
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
    @Mock
    private MetaDadoRepository metaDadoRepositoryMock;
    
    @Mock
    private MetaDadoService metaDadoServiceMock;

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
    
    public void getAllMetaDadosWithEagerRelationshipsIsEnabled() throws Exception {
        MetaDadoResource metaDadoResource = new MetaDadoResource(metaDadoServiceMock, metaDadoQueryService);
        when(metaDadoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restMetaDadoMockMvc = MockMvcBuilders.standaloneSetup(metaDadoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restMetaDadoMockMvc.perform(get("/api/meta-dados?eagerload=true"))
        .andExpect(status().isOk());

        verify(metaDadoServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    public void getAllMetaDadosWithEagerRelationshipsIsNotEnabled() throws Exception {
        MetaDadoResource metaDadoResource = new MetaDadoResource(metaDadoServiceMock, metaDadoQueryService);
            when(metaDadoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restMetaDadoMockMvc = MockMvcBuilders.standaloneSetup(metaDadoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restMetaDadoMockMvc.perform(get("/api/meta-dados?eagerload=true"))
        .andExpect(status().isOk());

            verify(metaDadoServiceMock, times(1)).findAllWithEagerRelationships(any());
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
    public void getAllMetaDadosByNomeIsEqualToSomething() throws Exception {
        // Initialize the database
        Name nome = NameResourceIntTest.createEntity(em);
        em.persist(nome);
        em.flush();
        metaDado.addNome(nome);
        metaDadoRepository.saveAndFlush(metaDado);
        Long nomeId = nome.getId();

        // Get all the metaDadoList where nome equals to nomeId
        defaultMetaDadoShouldBeFound("nomeId.equals=" + nomeId);

        // Get all the metaDadoList where nome equals to nomeId + 1
        defaultMetaDadoShouldNotBeFound("nomeId.equals=" + (nomeId + 1));
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
