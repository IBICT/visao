package br.com.ibict.visao.web.rest;

import br.com.ibict.visao.VisaoApp;

import br.com.ibict.visao.domain.Region;
import br.com.ibict.visao.domain.Region;
import br.com.ibict.visao.repository.RegionRepository;
import br.com.ibict.visao.service.RegionService;
import br.com.ibict.visao.web.rest.errors.ExceptionTranslator;
import br.com.ibict.visao.service.dto.RegionCriteria;
import br.com.ibict.visao.service.RegionQueryService;

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

import br.com.ibict.visao.domain.enumeration.TypeRegion;
/**
 * Test class for the RegionResource REST controller.
 *
 * @see RegionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = VisaoApp.class)
public class RegionResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_UF = "AAAAAAAAAA";
    private static final String UPDATED_UF = "BBBBBBBBBB";

    private static final Integer DEFAULT_GEO_CODE = 1;
    private static final Integer UPDATED_GEO_CODE = 2;

    private static final TypeRegion DEFAULT_TYPE_REGION = TypeRegion.ESTADO;
    private static final TypeRegion UPDATED_TYPE_REGION = TypeRegion.MESORREGIAO;

    @Autowired
    private RegionRepository regionRepository;
    @Mock
    private RegionRepository regionRepositoryMock;
    
    @Mock
    private RegionService regionServiceMock;

    @Autowired
    private RegionService regionService;

    @Autowired
    private RegionQueryService regionQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restRegionMockMvc;

    private Region region;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RegionResource regionResource = new RegionResource(regionService, regionQueryService);
        this.restRegionMockMvc = MockMvcBuilders.standaloneSetup(regionResource)
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
    public static Region createEntity(EntityManager em) {
        Region region = new Region()
            .name(DEFAULT_NAME)
            .uf(DEFAULT_UF)
            .geoCode(DEFAULT_GEO_CODE)
            .typeRegion(DEFAULT_TYPE_REGION);
        return region;
    }

    @Before
    public void initTest() {
        region = createEntity(em);
    }

    @Test
    @Transactional
    public void createRegion() throws Exception {
        int databaseSizeBeforeCreate = regionRepository.findAll().size();

        // Create the Region
        restRegionMockMvc.perform(post("/api/regions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(region)))
            .andExpect(status().isCreated());

        // Validate the Region in the database
        List<Region> regionList = regionRepository.findAll();
        assertThat(regionList).hasSize(databaseSizeBeforeCreate + 1);
        Region testRegion = regionList.get(regionList.size() - 1);
        assertThat(testRegion.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testRegion.getUf()).isEqualTo(DEFAULT_UF);
        assertThat(testRegion.getGeoCode()).isEqualTo(DEFAULT_GEO_CODE);
        assertThat(testRegion.getTypeRegion()).isEqualTo(DEFAULT_TYPE_REGION);
    }

    @Test
    @Transactional
    public void createRegionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = regionRepository.findAll().size();

        // Create the Region with an existing ID
        region.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRegionMockMvc.perform(post("/api/regions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(region)))
            .andExpect(status().isBadRequest());

        // Validate the Region in the database
        List<Region> regionList = regionRepository.findAll();
        assertThat(regionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = regionRepository.findAll().size();
        // set the field null
        region.setName(null);

        // Create the Region, which fails.

        restRegionMockMvc.perform(post("/api/regions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(region)))
            .andExpect(status().isBadRequest());

        List<Region> regionList = regionRepository.findAll();
        assertThat(regionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkGeoCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = regionRepository.findAll().size();
        // set the field null
        region.setGeoCode(null);

        // Create the Region, which fails.

        restRegionMockMvc.perform(post("/api/regions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(region)))
            .andExpect(status().isBadRequest());

        List<Region> regionList = regionRepository.findAll();
        assertThat(regionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTypeRegionIsRequired() throws Exception {
        int databaseSizeBeforeTest = regionRepository.findAll().size();
        // set the field null
        region.setTypeRegion(null);

        // Create the Region, which fails.

        restRegionMockMvc.perform(post("/api/regions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(region)))
            .andExpect(status().isBadRequest());

        List<Region> regionList = regionRepository.findAll();
        assertThat(regionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRegions() throws Exception {
        // Initialize the database
        regionRepository.saveAndFlush(region);

        // Get all the regionList
        restRegionMockMvc.perform(get("/api/regions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(region.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].uf").value(hasItem(DEFAULT_UF.toString())))
            .andExpect(jsonPath("$.[*].geoCode").value(hasItem(DEFAULT_GEO_CODE)))
            .andExpect(jsonPath("$.[*].typeRegion").value(hasItem(DEFAULT_TYPE_REGION.toString())));
    }
    
    public void getAllRegionsWithEagerRelationshipsIsEnabled() throws Exception {
        RegionResource regionResource = new RegionResource(regionServiceMock, regionQueryService);
        when(regionServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restRegionMockMvc = MockMvcBuilders.standaloneSetup(regionResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restRegionMockMvc.perform(get("/api/regions?eagerload=true"))
        .andExpect(status().isOk());

        verify(regionServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    public void getAllRegionsWithEagerRelationshipsIsNotEnabled() throws Exception {
        RegionResource regionResource = new RegionResource(regionServiceMock, regionQueryService);
            when(regionServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restRegionMockMvc = MockMvcBuilders.standaloneSetup(regionResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restRegionMockMvc.perform(get("/api/regions?eagerload=true"))
        .andExpect(status().isOk());

            verify(regionServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getRegion() throws Exception {
        // Initialize the database
        regionRepository.saveAndFlush(region);

        // Get the region
        restRegionMockMvc.perform(get("/api/regions/{id}", region.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(region.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.uf").value(DEFAULT_UF.toString()))
            .andExpect(jsonPath("$.geoCode").value(DEFAULT_GEO_CODE))
            .andExpect(jsonPath("$.typeRegion").value(DEFAULT_TYPE_REGION.toString()));
    }

    @Test
    @Transactional
    public void getAllRegionsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        regionRepository.saveAndFlush(region);

        // Get all the regionList where name equals to DEFAULT_NAME
        defaultRegionShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the regionList where name equals to UPDATED_NAME
        defaultRegionShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllRegionsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        regionRepository.saveAndFlush(region);

        // Get all the regionList where name in DEFAULT_NAME or UPDATED_NAME
        defaultRegionShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the regionList where name equals to UPDATED_NAME
        defaultRegionShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllRegionsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        regionRepository.saveAndFlush(region);

        // Get all the regionList where name is not null
        defaultRegionShouldBeFound("name.specified=true");

        // Get all the regionList where name is null
        defaultRegionShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllRegionsByUfIsEqualToSomething() throws Exception {
        // Initialize the database
        regionRepository.saveAndFlush(region);

        // Get all the regionList where uf equals to DEFAULT_UF
        defaultRegionShouldBeFound("uf.equals=" + DEFAULT_UF);

        // Get all the regionList where uf equals to UPDATED_UF
        defaultRegionShouldNotBeFound("uf.equals=" + UPDATED_UF);
    }

    @Test
    @Transactional
    public void getAllRegionsByUfIsInShouldWork() throws Exception {
        // Initialize the database
        regionRepository.saveAndFlush(region);

        // Get all the regionList where uf in DEFAULT_UF or UPDATED_UF
        defaultRegionShouldBeFound("uf.in=" + DEFAULT_UF + "," + UPDATED_UF);

        // Get all the regionList where uf equals to UPDATED_UF
        defaultRegionShouldNotBeFound("uf.in=" + UPDATED_UF);
    }

    @Test
    @Transactional
    public void getAllRegionsByUfIsNullOrNotNull() throws Exception {
        // Initialize the database
        regionRepository.saveAndFlush(region);

        // Get all the regionList where uf is not null
        defaultRegionShouldBeFound("uf.specified=true");

        // Get all the regionList where uf is null
        defaultRegionShouldNotBeFound("uf.specified=false");
    }

    @Test
    @Transactional
    public void getAllRegionsByGeoCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        regionRepository.saveAndFlush(region);

        // Get all the regionList where geoCode equals to DEFAULT_GEO_CODE
        defaultRegionShouldBeFound("geoCode.equals=" + DEFAULT_GEO_CODE);

        // Get all the regionList where geoCode equals to UPDATED_GEO_CODE
        defaultRegionShouldNotBeFound("geoCode.equals=" + UPDATED_GEO_CODE);
    }

    @Test
    @Transactional
    public void getAllRegionsByGeoCodeIsInShouldWork() throws Exception {
        // Initialize the database
        regionRepository.saveAndFlush(region);

        // Get all the regionList where geoCode in DEFAULT_GEO_CODE or UPDATED_GEO_CODE
        defaultRegionShouldBeFound("geoCode.in=" + DEFAULT_GEO_CODE + "," + UPDATED_GEO_CODE);

        // Get all the regionList where geoCode equals to UPDATED_GEO_CODE
        defaultRegionShouldNotBeFound("geoCode.in=" + UPDATED_GEO_CODE);
    }

    @Test
    @Transactional
    public void getAllRegionsByGeoCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        regionRepository.saveAndFlush(region);

        // Get all the regionList where geoCode is not null
        defaultRegionShouldBeFound("geoCode.specified=true");

        // Get all the regionList where geoCode is null
        defaultRegionShouldNotBeFound("geoCode.specified=false");
    }

    @Test
    @Transactional
    public void getAllRegionsByGeoCodeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        regionRepository.saveAndFlush(region);

        // Get all the regionList where geoCode greater than or equals to DEFAULT_GEO_CODE
        defaultRegionShouldBeFound("geoCode.greaterOrEqualThan=" + DEFAULT_GEO_CODE);

        // Get all the regionList where geoCode greater than or equals to UPDATED_GEO_CODE
        defaultRegionShouldNotBeFound("geoCode.greaterOrEqualThan=" + UPDATED_GEO_CODE);
    }

    @Test
    @Transactional
    public void getAllRegionsByGeoCodeIsLessThanSomething() throws Exception {
        // Initialize the database
        regionRepository.saveAndFlush(region);

        // Get all the regionList where geoCode less than or equals to DEFAULT_GEO_CODE
        defaultRegionShouldNotBeFound("geoCode.lessThan=" + DEFAULT_GEO_CODE);

        // Get all the regionList where geoCode less than or equals to UPDATED_GEO_CODE
        defaultRegionShouldBeFound("geoCode.lessThan=" + UPDATED_GEO_CODE);
    }


    @Test
    @Transactional
    public void getAllRegionsByTypeRegionIsEqualToSomething() throws Exception {
        // Initialize the database
        regionRepository.saveAndFlush(region);

        // Get all the regionList where typeRegion equals to DEFAULT_TYPE_REGION
        defaultRegionShouldBeFound("typeRegion.equals=" + DEFAULT_TYPE_REGION);

        // Get all the regionList where typeRegion equals to UPDATED_TYPE_REGION
        defaultRegionShouldNotBeFound("typeRegion.equals=" + UPDATED_TYPE_REGION);
    }

    @Test
    @Transactional
    public void getAllRegionsByTypeRegionIsInShouldWork() throws Exception {
        // Initialize the database
        regionRepository.saveAndFlush(region);

        // Get all the regionList where typeRegion in DEFAULT_TYPE_REGION or UPDATED_TYPE_REGION
        defaultRegionShouldBeFound("typeRegion.in=" + DEFAULT_TYPE_REGION + "," + UPDATED_TYPE_REGION);

        // Get all the regionList where typeRegion equals to UPDATED_TYPE_REGION
        defaultRegionShouldNotBeFound("typeRegion.in=" + UPDATED_TYPE_REGION);
    }

    @Test
    @Transactional
    public void getAllRegionsByTypeRegionIsNullOrNotNull() throws Exception {
        // Initialize the database
        regionRepository.saveAndFlush(region);

        // Get all the regionList where typeRegion is not null
        defaultRegionShouldBeFound("typeRegion.specified=true");

        // Get all the regionList where typeRegion is null
        defaultRegionShouldNotBeFound("typeRegion.specified=false");
    }

    @Test
    @Transactional
    public void getAllRegionsByRelacaoIsEqualToSomething() throws Exception {
        // Initialize the database
        Region relacao = RegionResourceIntTest.createEntity(em);
        em.persist(relacao);
        em.flush();
        region.addRelacao(relacao);
        regionRepository.saveAndFlush(region);
        Long relacaoId = relacao.getId();

        // Get all the regionList where relacao equals to relacaoId
        defaultRegionShouldBeFound("relacaoId.equals=" + relacaoId);

        // Get all the regionList where relacao equals to relacaoId + 1
        defaultRegionShouldNotBeFound("relacaoId.equals=" + (relacaoId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultRegionShouldBeFound(String filter) throws Exception {
        restRegionMockMvc.perform(get("/api/regions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(region.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].uf").value(hasItem(DEFAULT_UF.toString())))
            .andExpect(jsonPath("$.[*].geoCode").value(hasItem(DEFAULT_GEO_CODE)))
            .andExpect(jsonPath("$.[*].typeRegion").value(hasItem(DEFAULT_TYPE_REGION.toString())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultRegionShouldNotBeFound(String filter) throws Exception {
        restRegionMockMvc.perform(get("/api/regions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    @Transactional
    public void getNonExistingRegion() throws Exception {
        // Get the region
        restRegionMockMvc.perform(get("/api/regions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRegion() throws Exception {
        // Initialize the database
        regionService.save(region);

        int databaseSizeBeforeUpdate = regionRepository.findAll().size();

        // Update the region
        Region updatedRegion = regionRepository.findById(region.getId()).get();
        // Disconnect from session so that the updates on updatedRegion are not directly saved in db
        em.detach(updatedRegion);
        updatedRegion
            .name(UPDATED_NAME)
            .uf(UPDATED_UF)
            .geoCode(UPDATED_GEO_CODE)
            .typeRegion(UPDATED_TYPE_REGION);

        restRegionMockMvc.perform(put("/api/regions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedRegion)))
            .andExpect(status().isOk());

        // Validate the Region in the database
        List<Region> regionList = regionRepository.findAll();
        assertThat(regionList).hasSize(databaseSizeBeforeUpdate);
        Region testRegion = regionList.get(regionList.size() - 1);
        assertThat(testRegion.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testRegion.getUf()).isEqualTo(UPDATED_UF);
        assertThat(testRegion.getGeoCode()).isEqualTo(UPDATED_GEO_CODE);
        assertThat(testRegion.getTypeRegion()).isEqualTo(UPDATED_TYPE_REGION);
    }

    @Test
    @Transactional
    public void updateNonExistingRegion() throws Exception {
        int databaseSizeBeforeUpdate = regionRepository.findAll().size();

        // Create the Region

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restRegionMockMvc.perform(put("/api/regions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(region)))
            .andExpect(status().isBadRequest());

        // Validate the Region in the database
        List<Region> regionList = regionRepository.findAll();
        assertThat(regionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRegion() throws Exception {
        // Initialize the database
        regionService.save(region);

        int databaseSizeBeforeDelete = regionRepository.findAll().size();

        // Get the region
        restRegionMockMvc.perform(delete("/api/regions/{id}", region.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Region> regionList = regionRepository.findAll();
        assertThat(regionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Region.class);
        Region region1 = new Region();
        region1.setId(1L);
        Region region2 = new Region();
        region2.setId(region1.getId());
        assertThat(region1).isEqualTo(region2);
        region2.setId(2L);
        assertThat(region1).isNotEqualTo(region2);
        region1.setId(null);
        assertThat(region1).isNotEqualTo(region2);
    }
}
