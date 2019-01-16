package br.com.ibict.visao.web.rest;

import br.com.ibict.visao.VisaoApp;

import br.com.ibict.visao.domain.Name;
import br.com.ibict.visao.domain.Category;
import br.com.ibict.visao.repository.NameRepository;
import br.com.ibict.visao.service.NameService;
import br.com.ibict.visao.web.rest.errors.ExceptionTranslator;
import br.com.ibict.visao.service.dto.NameCriteria;
import br.com.ibict.visao.service.NameQueryService;

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
 * Test class for the NameResource REST controller.
 *
 * @see NameResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = VisaoApp.class)
public class NameResourceIntTest {

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    @Autowired
    private NameRepository nameRepository;

    

    @Autowired
    private NameService nameService;

    @Autowired
    private NameQueryService nameQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restNameMockMvc;

    private Name name;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final NameResource nameResource = new NameResource(nameService, nameQueryService);
        this.restNameMockMvc = MockMvcBuilders.standaloneSetup(nameResource)
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
    public static Name createEntity(EntityManager em) {
        Name name = new Name()
            .value(DEFAULT_VALUE);
        return name;
    }

    @Before
    public void initTest() {
        name = createEntity(em);
    }

    @Test
    @Transactional
    public void createName() throws Exception {
        int databaseSizeBeforeCreate = nameRepository.findAll().size();

        // Create the Name
        restNameMockMvc.perform(post("/api/names")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(name)))
            .andExpect(status().isCreated());

        // Validate the Name in the database
        List<Name> nameList = nameRepository.findAll();
        assertThat(nameList).hasSize(databaseSizeBeforeCreate + 1);
        Name testName = nameList.get(nameList.size() - 1);
        assertThat(testName.getValue()).isEqualTo(DEFAULT_VALUE);
    }

    @Test
    @Transactional
    public void createNameWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = nameRepository.findAll().size();

        // Create the Name with an existing ID
        name.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restNameMockMvc.perform(post("/api/names")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(name)))
            .andExpect(status().isBadRequest());

        // Validate the Name in the database
        List<Name> nameList = nameRepository.findAll();
        assertThat(nameList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = nameRepository.findAll().size();
        // set the field null
        name.setValue(null);

        // Create the Name, which fails.

        restNameMockMvc.perform(post("/api/names")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(name)))
            .andExpect(status().isBadRequest());

        List<Name> nameList = nameRepository.findAll();
        assertThat(nameList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllNames() throws Exception {
        // Initialize the database
        nameRepository.saveAndFlush(name);

        // Get all the nameList
        restNameMockMvc.perform(get("/api/names?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(name.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.toString())));
    }
    

    @Test
    @Transactional
    public void getName() throws Exception {
        // Initialize the database
        nameRepository.saveAndFlush(name);

        // Get the name
        restNameMockMvc.perform(get("/api/names/{id}", name.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(name.getId().intValue()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.toString()));
    }

    @Test
    @Transactional
    public void getAllNamesByValueIsEqualToSomething() throws Exception {
        // Initialize the database
        nameRepository.saveAndFlush(name);

        // Get all the nameList where value equals to DEFAULT_VALUE
        defaultNameShouldBeFound("value.equals=" + DEFAULT_VALUE);

        // Get all the nameList where value equals to UPDATED_VALUE
        defaultNameShouldNotBeFound("value.equals=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void getAllNamesByValueIsInShouldWork() throws Exception {
        // Initialize the database
        nameRepository.saveAndFlush(name);

        // Get all the nameList where value in DEFAULT_VALUE or UPDATED_VALUE
        defaultNameShouldBeFound("value.in=" + DEFAULT_VALUE + "," + UPDATED_VALUE);

        // Get all the nameList where value equals to UPDATED_VALUE
        defaultNameShouldNotBeFound("value.in=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void getAllNamesByValueIsNullOrNotNull() throws Exception {
        // Initialize the database
        nameRepository.saveAndFlush(name);

        // Get all the nameList where value is not null
        defaultNameShouldBeFound("value.specified=true");

        // Get all the nameList where value is null
        defaultNameShouldNotBeFound("value.specified=false");
    }

    @Test
    @Transactional
    public void getAllNamesByCategoryIsEqualToSomething() throws Exception {
        // Initialize the database
        Category category = CategoryResourceIntTest.createEntity(em);
        em.persist(category);
        em.flush();
        name.setCategory(category);
        nameRepository.saveAndFlush(name);
        Long categoryId = category.getId();

        // Get all the nameList where category equals to categoryId
        defaultNameShouldBeFound("categoryId.equals=" + categoryId);

        // Get all the nameList where category equals to categoryId + 1
        defaultNameShouldNotBeFound("categoryId.equals=" + (categoryId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultNameShouldBeFound(String filter) throws Exception {
        restNameMockMvc.perform(get("/api/names?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(name.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.toString())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultNameShouldNotBeFound(String filter) throws Exception {
        restNameMockMvc.perform(get("/api/names?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    @Transactional
    public void getNonExistingName() throws Exception {
        // Get the name
        restNameMockMvc.perform(get("/api/names/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateName() throws Exception {
        // Initialize the database
        nameService.save(name);

        int databaseSizeBeforeUpdate = nameRepository.findAll().size();

        // Update the name
        Name updatedName = nameRepository.findById(name.getId()).get();
        // Disconnect from session so that the updates on updatedName are not directly saved in db
        em.detach(updatedName);
        updatedName
            .value(UPDATED_VALUE);

        restNameMockMvc.perform(put("/api/names")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedName)))
            .andExpect(status().isOk());

        // Validate the Name in the database
        List<Name> nameList = nameRepository.findAll();
        assertThat(nameList).hasSize(databaseSizeBeforeUpdate);
        Name testName = nameList.get(nameList.size() - 1);
        assertThat(testName.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void updateNonExistingName() throws Exception {
        int databaseSizeBeforeUpdate = nameRepository.findAll().size();

        // Create the Name

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restNameMockMvc.perform(put("/api/names")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(name)))
            .andExpect(status().isBadRequest());

        // Validate the Name in the database
        List<Name> nameList = nameRepository.findAll();
        assertThat(nameList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteName() throws Exception {
        // Initialize the database
        nameService.save(name);

        int databaseSizeBeforeDelete = nameRepository.findAll().size();

        // Get the name
        restNameMockMvc.perform(delete("/api/names/{id}", name.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Name> nameList = nameRepository.findAll();
        assertThat(nameList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Name.class);
        Name name1 = new Name();
        name1.setId(1L);
        Name name2 = new Name();
        name2.setId(name1.getId());
        assertThat(name1).isEqualTo(name2);
        name2.setId(2L);
        assertThat(name1).isNotEqualTo(name2);
        name1.setId(null);
        assertThat(name1).isNotEqualTo(name2);
    }
}
