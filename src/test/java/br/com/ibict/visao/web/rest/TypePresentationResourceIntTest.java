package br.com.ibict.visao.web.rest;

import br.com.ibict.visao.VisaoApp;

import br.com.ibict.visao.domain.TypePresentation;
import br.com.ibict.visao.repository.TypePresentationRepository;
import br.com.ibict.visao.service.TypePresentationService;
import br.com.ibict.visao.web.rest.errors.ExceptionTranslator;
import br.com.ibict.visao.service.dto.TypePresentationCriteria;
import br.com.ibict.visao.service.TypePresentationQueryService;

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
 * Test class for the TypePresentationResource REST controller.
 *
 * @see TypePresentationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = VisaoApp.class)
public class TypePresentationResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DISPLAY = "AAAAAAAAAA";
    private static final String UPDATED_DISPLAY = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private TypePresentationRepository typePresentationRepository;

    

    @Autowired
    private TypePresentationService typePresentationService;

    @Autowired
    private TypePresentationQueryService typePresentationQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTypePresentationMockMvc;

    private TypePresentation typePresentation;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TypePresentationResource typePresentationResource = new TypePresentationResource(typePresentationService, typePresentationQueryService);
        this.restTypePresentationMockMvc = MockMvcBuilders.standaloneSetup(typePresentationResource)
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
    public static TypePresentation createEntity(EntityManager em) {
        TypePresentation typePresentation = new TypePresentation()
            .name(DEFAULT_NAME)
            .display(DEFAULT_DISPLAY)
            .description(DEFAULT_DESCRIPTION);
        return typePresentation;
    }

    @Before
    public void initTest() {
        typePresentation = createEntity(em);
    }

    @Test
    @Transactional
    public void createTypePresentation() throws Exception {
        int databaseSizeBeforeCreate = typePresentationRepository.findAll().size();

        // Create the TypePresentation
        restTypePresentationMockMvc.perform(post("/api/type-presentations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typePresentation)))
            .andExpect(status().isCreated());

        // Validate the TypePresentation in the database
        List<TypePresentation> typePresentationList = typePresentationRepository.findAll();
        assertThat(typePresentationList).hasSize(databaseSizeBeforeCreate + 1);
        TypePresentation testTypePresentation = typePresentationList.get(typePresentationList.size() - 1);
        assertThat(testTypePresentation.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTypePresentation.getDisplay()).isEqualTo(DEFAULT_DISPLAY);
        assertThat(testTypePresentation.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createTypePresentationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = typePresentationRepository.findAll().size();

        // Create the TypePresentation with an existing ID
        typePresentation.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTypePresentationMockMvc.perform(post("/api/type-presentations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typePresentation)))
            .andExpect(status().isBadRequest());

        // Validate the TypePresentation in the database
        List<TypePresentation> typePresentationList = typePresentationRepository.findAll();
        assertThat(typePresentationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllTypePresentations() throws Exception {
        // Initialize the database
        typePresentationRepository.saveAndFlush(typePresentation);

        // Get all the typePresentationList
        restTypePresentationMockMvc.perform(get("/api/type-presentations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(typePresentation.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].display").value(hasItem(DEFAULT_DISPLAY.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }
    

    @Test
    @Transactional
    public void getTypePresentation() throws Exception {
        // Initialize the database
        typePresentationRepository.saveAndFlush(typePresentation);

        // Get the typePresentation
        restTypePresentationMockMvc.perform(get("/api/type-presentations/{id}", typePresentation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(typePresentation.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.display").value(DEFAULT_DISPLAY.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getAllTypePresentationsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        typePresentationRepository.saveAndFlush(typePresentation);

        // Get all the typePresentationList where name equals to DEFAULT_NAME
        defaultTypePresentationShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the typePresentationList where name equals to UPDATED_NAME
        defaultTypePresentationShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllTypePresentationsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        typePresentationRepository.saveAndFlush(typePresentation);

        // Get all the typePresentationList where name in DEFAULT_NAME or UPDATED_NAME
        defaultTypePresentationShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the typePresentationList where name equals to UPDATED_NAME
        defaultTypePresentationShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllTypePresentationsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        typePresentationRepository.saveAndFlush(typePresentation);

        // Get all the typePresentationList where name is not null
        defaultTypePresentationShouldBeFound("name.specified=true");

        // Get all the typePresentationList where name is null
        defaultTypePresentationShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllTypePresentationsByDisplayIsEqualToSomething() throws Exception {
        // Initialize the database
        typePresentationRepository.saveAndFlush(typePresentation);

        // Get all the typePresentationList where display equals to DEFAULT_DISPLAY
        defaultTypePresentationShouldBeFound("display.equals=" + DEFAULT_DISPLAY);

        // Get all the typePresentationList where display equals to UPDATED_DISPLAY
        defaultTypePresentationShouldNotBeFound("display.equals=" + UPDATED_DISPLAY);
    }

    @Test
    @Transactional
    public void getAllTypePresentationsByDisplayIsInShouldWork() throws Exception {
        // Initialize the database
        typePresentationRepository.saveAndFlush(typePresentation);

        // Get all the typePresentationList where display in DEFAULT_DISPLAY or UPDATED_DISPLAY
        defaultTypePresentationShouldBeFound("display.in=" + DEFAULT_DISPLAY + "," + UPDATED_DISPLAY);

        // Get all the typePresentationList where display equals to UPDATED_DISPLAY
        defaultTypePresentationShouldNotBeFound("display.in=" + UPDATED_DISPLAY);
    }

    @Test
    @Transactional
    public void getAllTypePresentationsByDisplayIsNullOrNotNull() throws Exception {
        // Initialize the database
        typePresentationRepository.saveAndFlush(typePresentation);

        // Get all the typePresentationList where display is not null
        defaultTypePresentationShouldBeFound("display.specified=true");

        // Get all the typePresentationList where display is null
        defaultTypePresentationShouldNotBeFound("display.specified=false");
    }

    @Test
    @Transactional
    public void getAllTypePresentationsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        typePresentationRepository.saveAndFlush(typePresentation);

        // Get all the typePresentationList where description equals to DEFAULT_DESCRIPTION
        defaultTypePresentationShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the typePresentationList where description equals to UPDATED_DESCRIPTION
        defaultTypePresentationShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllTypePresentationsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        typePresentationRepository.saveAndFlush(typePresentation);

        // Get all the typePresentationList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultTypePresentationShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the typePresentationList where description equals to UPDATED_DESCRIPTION
        defaultTypePresentationShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllTypePresentationsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        typePresentationRepository.saveAndFlush(typePresentation);

        // Get all the typePresentationList where description is not null
        defaultTypePresentationShouldBeFound("description.specified=true");

        // Get all the typePresentationList where description is null
        defaultTypePresentationShouldNotBeFound("description.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultTypePresentationShouldBeFound(String filter) throws Exception {
        restTypePresentationMockMvc.perform(get("/api/type-presentations?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(typePresentation.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].display").value(hasItem(DEFAULT_DISPLAY.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultTypePresentationShouldNotBeFound(String filter) throws Exception {
        restTypePresentationMockMvc.perform(get("/api/type-presentations?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    @Transactional
    public void getNonExistingTypePresentation() throws Exception {
        // Get the typePresentation
        restTypePresentationMockMvc.perform(get("/api/type-presentations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTypePresentation() throws Exception {
        // Initialize the database
        typePresentationService.save(typePresentation);

        int databaseSizeBeforeUpdate = typePresentationRepository.findAll().size();

        // Update the typePresentation
        TypePresentation updatedTypePresentation = typePresentationRepository.findById(typePresentation.getId()).get();
        // Disconnect from session so that the updates on updatedTypePresentation are not directly saved in db
        em.detach(updatedTypePresentation);
        updatedTypePresentation
            .name(UPDATED_NAME)
            .display(UPDATED_DISPLAY)
            .description(UPDATED_DESCRIPTION);

        restTypePresentationMockMvc.perform(put("/api/type-presentations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTypePresentation)))
            .andExpect(status().isOk());

        // Validate the TypePresentation in the database
        List<TypePresentation> typePresentationList = typePresentationRepository.findAll();
        assertThat(typePresentationList).hasSize(databaseSizeBeforeUpdate);
        TypePresentation testTypePresentation = typePresentationList.get(typePresentationList.size() - 1);
        assertThat(testTypePresentation.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTypePresentation.getDisplay()).isEqualTo(UPDATED_DISPLAY);
        assertThat(testTypePresentation.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingTypePresentation() throws Exception {
        int databaseSizeBeforeUpdate = typePresentationRepository.findAll().size();

        // Create the TypePresentation

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTypePresentationMockMvc.perform(put("/api/type-presentations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typePresentation)))
            .andExpect(status().isBadRequest());

        // Validate the TypePresentation in the database
        List<TypePresentation> typePresentationList = typePresentationRepository.findAll();
        assertThat(typePresentationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTypePresentation() throws Exception {
        // Initialize the database
        typePresentationService.save(typePresentation);

        int databaseSizeBeforeDelete = typePresentationRepository.findAll().size();

        // Get the typePresentation
        restTypePresentationMockMvc.perform(delete("/api/type-presentations/{id}", typePresentation.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TypePresentation> typePresentationList = typePresentationRepository.findAll();
        assertThat(typePresentationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TypePresentation.class);
        TypePresentation typePresentation1 = new TypePresentation();
        typePresentation1.setId(1L);
        TypePresentation typePresentation2 = new TypePresentation();
        typePresentation2.setId(typePresentation1.getId());
        assertThat(typePresentation1).isEqualTo(typePresentation2);
        typePresentation2.setId(2L);
        assertThat(typePresentation1).isNotEqualTo(typePresentation2);
        typePresentation1.setId(null);
        assertThat(typePresentation1).isNotEqualTo(typePresentation2);
    }
}
