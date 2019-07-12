package br.com.ibict.web.rest;

import br.com.ibict.VisaoApp;

import br.com.ibict.domain.TypePresentation;
import br.com.ibict.repository.TypePresentationRepository;
import br.com.ibict.web.rest.errors.ExceptionTranslator;

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
import java.util.List;


import static br.com.ibict.web.rest.TestUtil.createFormattingConversionService;
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
        final TypePresentationResource typePresentationResource = new TypePresentationResource(typePresentationRepository);
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
    public void getNonExistingTypePresentation() throws Exception {
        // Get the typePresentation
        restTypePresentationMockMvc.perform(get("/api/type-presentations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTypePresentation() throws Exception {
        // Initialize the database
        typePresentationRepository.saveAndFlush(typePresentation);

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
        typePresentationRepository.saveAndFlush(typePresentation);

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
