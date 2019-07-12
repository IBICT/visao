package br.com.ibict.web.rest;

import br.com.ibict.VisaoApp;

import br.com.ibict.domain.MarkerIcon;
import br.com.ibict.repository.MarkerIconRepository;
import br.com.ibict.service.MarkerIconService;
import br.com.ibict.web.rest.errors.ExceptionTranslator;
import br.com.ibict.service.dto.MarkerIconCriteria;
import br.com.ibict.service.MarkerIconQueryService;

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
 * Test class for the MarkerIconResource REST controller.
 *
 * @see MarkerIconResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = VisaoApp.class)
public class MarkerIconResourceIntTest {

    private static final byte[] DEFAULT_ICON = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_ICON = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_ICON_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_ICON_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_SHADOW = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_SHADOW = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_SHADOW_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_SHADOW_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_ICON_SIZE = "AAAAAAAAAA";
    private static final String UPDATED_ICON_SIZE = "BBBBBBBBBB";

    private static final String DEFAULT_SHADOW_SIZE = "AAAAAAAAAA";
    private static final String UPDATED_SHADOW_SIZE = "BBBBBBBBBB";

    private static final String DEFAULT_ICON_ANCHOR = "AAAAAAAAAA";
    private static final String UPDATED_ICON_ANCHOR = "BBBBBBBBBB";

    private static final String DEFAULT_SHADOW_ANCHOR = "AAAAAAAAAA";
    private static final String UPDATED_SHADOW_ANCHOR = "BBBBBBBBBB";

    private static final String DEFAULT_POPUP_ANCHOR = "AAAAAAAAAA";
    private static final String UPDATED_POPUP_ANCHOR = "BBBBBBBBBB";

    @Autowired
    private MarkerIconRepository markerIconRepository;

    

    @Autowired
    private MarkerIconService markerIconService;

    @Autowired
    private MarkerIconQueryService markerIconQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMarkerIconMockMvc;

    private MarkerIcon markerIcon;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MarkerIconResource markerIconResource = new MarkerIconResource(markerIconService, markerIconQueryService);
        this.restMarkerIconMockMvc = MockMvcBuilders.standaloneSetup(markerIconResource)
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
    public static MarkerIcon createEntity(EntityManager em) {
        MarkerIcon markerIcon = new MarkerIcon()
            .icon(DEFAULT_ICON)
            .iconContentType(DEFAULT_ICON_CONTENT_TYPE)
            .shadow(DEFAULT_SHADOW)
            .shadowContentType(DEFAULT_SHADOW_CONTENT_TYPE)
            .iconSize(DEFAULT_ICON_SIZE)
            .shadowSize(DEFAULT_SHADOW_SIZE)
            .iconAnchor(DEFAULT_ICON_ANCHOR)
            .shadowAnchor(DEFAULT_SHADOW_ANCHOR)
            .popupAnchor(DEFAULT_POPUP_ANCHOR);
        return markerIcon;
    }

    @Before
    public void initTest() {
        markerIcon = createEntity(em);
    }

    @Test
    @Transactional
    public void createMarkerIcon() throws Exception {
        int databaseSizeBeforeCreate = markerIconRepository.findAll().size();

        // Create the MarkerIcon
        restMarkerIconMockMvc.perform(post("/api/marker-icons")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(markerIcon)))
            .andExpect(status().isCreated());

        // Validate the MarkerIcon in the database
        List<MarkerIcon> markerIconList = markerIconRepository.findAll();
        assertThat(markerIconList).hasSize(databaseSizeBeforeCreate + 1);
        MarkerIcon testMarkerIcon = markerIconList.get(markerIconList.size() - 1);
        assertThat(testMarkerIcon.getIcon()).isEqualTo(DEFAULT_ICON);
        assertThat(testMarkerIcon.getIconContentType()).isEqualTo(DEFAULT_ICON_CONTENT_TYPE);
        assertThat(testMarkerIcon.getShadow()).isEqualTo(DEFAULT_SHADOW);
        assertThat(testMarkerIcon.getShadowContentType()).isEqualTo(DEFAULT_SHADOW_CONTENT_TYPE);
        assertThat(testMarkerIcon.getIconSize()).isEqualTo(DEFAULT_ICON_SIZE);
        assertThat(testMarkerIcon.getShadowSize()).isEqualTo(DEFAULT_SHADOW_SIZE);
        assertThat(testMarkerIcon.getIconAnchor()).isEqualTo(DEFAULT_ICON_ANCHOR);
        assertThat(testMarkerIcon.getShadowAnchor()).isEqualTo(DEFAULT_SHADOW_ANCHOR);
        assertThat(testMarkerIcon.getPopupAnchor()).isEqualTo(DEFAULT_POPUP_ANCHOR);
    }

    @Test
    @Transactional
    public void createMarkerIconWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = markerIconRepository.findAll().size();

        // Create the MarkerIcon with an existing ID
        markerIcon.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMarkerIconMockMvc.perform(post("/api/marker-icons")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(markerIcon)))
            .andExpect(status().isBadRequest());

        // Validate the MarkerIcon in the database
        List<MarkerIcon> markerIconList = markerIconRepository.findAll();
        assertThat(markerIconList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllMarkerIcons() throws Exception {
        // Initialize the database
        markerIconRepository.saveAndFlush(markerIcon);

        // Get all the markerIconList
        restMarkerIconMockMvc.perform(get("/api/marker-icons?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(markerIcon.getId().intValue())))
            .andExpect(jsonPath("$.[*].iconContentType").value(hasItem(DEFAULT_ICON_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].icon").value(hasItem(Base64Utils.encodeToString(DEFAULT_ICON))))
            .andExpect(jsonPath("$.[*].shadowContentType").value(hasItem(DEFAULT_SHADOW_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].shadow").value(hasItem(Base64Utils.encodeToString(DEFAULT_SHADOW))))
            .andExpect(jsonPath("$.[*].iconSize").value(hasItem(DEFAULT_ICON_SIZE.toString())))
            .andExpect(jsonPath("$.[*].shadowSize").value(hasItem(DEFAULT_SHADOW_SIZE.toString())))
            .andExpect(jsonPath("$.[*].iconAnchor").value(hasItem(DEFAULT_ICON_ANCHOR.toString())))
            .andExpect(jsonPath("$.[*].shadowAnchor").value(hasItem(DEFAULT_SHADOW_ANCHOR.toString())))
            .andExpect(jsonPath("$.[*].popupAnchor").value(hasItem(DEFAULT_POPUP_ANCHOR.toString())));
    }
    

    @Test
    @Transactional
    public void getMarkerIcon() throws Exception {
        // Initialize the database
        markerIconRepository.saveAndFlush(markerIcon);

        // Get the markerIcon
        restMarkerIconMockMvc.perform(get("/api/marker-icons/{id}", markerIcon.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(markerIcon.getId().intValue()))
            .andExpect(jsonPath("$.iconContentType").value(DEFAULT_ICON_CONTENT_TYPE))
            .andExpect(jsonPath("$.icon").value(Base64Utils.encodeToString(DEFAULT_ICON)))
            .andExpect(jsonPath("$.shadowContentType").value(DEFAULT_SHADOW_CONTENT_TYPE))
            .andExpect(jsonPath("$.shadow").value(Base64Utils.encodeToString(DEFAULT_SHADOW)))
            .andExpect(jsonPath("$.iconSize").value(DEFAULT_ICON_SIZE.toString()))
            .andExpect(jsonPath("$.shadowSize").value(DEFAULT_SHADOW_SIZE.toString()))
            .andExpect(jsonPath("$.iconAnchor").value(DEFAULT_ICON_ANCHOR.toString()))
            .andExpect(jsonPath("$.shadowAnchor").value(DEFAULT_SHADOW_ANCHOR.toString()))
            .andExpect(jsonPath("$.popupAnchor").value(DEFAULT_POPUP_ANCHOR.toString()));
    }

    @Test
    @Transactional
    public void getAllMarkerIconsByIconSizeIsEqualToSomething() throws Exception {
        // Initialize the database
        markerIconRepository.saveAndFlush(markerIcon);

        // Get all the markerIconList where iconSize equals to DEFAULT_ICON_SIZE
        defaultMarkerIconShouldBeFound("iconSize.equals=" + DEFAULT_ICON_SIZE);

        // Get all the markerIconList where iconSize equals to UPDATED_ICON_SIZE
        defaultMarkerIconShouldNotBeFound("iconSize.equals=" + UPDATED_ICON_SIZE);
    }

    @Test
    @Transactional
    public void getAllMarkerIconsByIconSizeIsInShouldWork() throws Exception {
        // Initialize the database
        markerIconRepository.saveAndFlush(markerIcon);

        // Get all the markerIconList where iconSize in DEFAULT_ICON_SIZE or UPDATED_ICON_SIZE
        defaultMarkerIconShouldBeFound("iconSize.in=" + DEFAULT_ICON_SIZE + "," + UPDATED_ICON_SIZE);

        // Get all the markerIconList where iconSize equals to UPDATED_ICON_SIZE
        defaultMarkerIconShouldNotBeFound("iconSize.in=" + UPDATED_ICON_SIZE);
    }

    @Test
    @Transactional
    public void getAllMarkerIconsByIconSizeIsNullOrNotNull() throws Exception {
        // Initialize the database
        markerIconRepository.saveAndFlush(markerIcon);

        // Get all the markerIconList where iconSize is not null
        defaultMarkerIconShouldBeFound("iconSize.specified=true");

        // Get all the markerIconList where iconSize is null
        defaultMarkerIconShouldNotBeFound("iconSize.specified=false");
    }

    @Test
    @Transactional
    public void getAllMarkerIconsByShadowSizeIsEqualToSomething() throws Exception {
        // Initialize the database
        markerIconRepository.saveAndFlush(markerIcon);

        // Get all the markerIconList where shadowSize equals to DEFAULT_SHADOW_SIZE
        defaultMarkerIconShouldBeFound("shadowSize.equals=" + DEFAULT_SHADOW_SIZE);

        // Get all the markerIconList where shadowSize equals to UPDATED_SHADOW_SIZE
        defaultMarkerIconShouldNotBeFound("shadowSize.equals=" + UPDATED_SHADOW_SIZE);
    }

    @Test
    @Transactional
    public void getAllMarkerIconsByShadowSizeIsInShouldWork() throws Exception {
        // Initialize the database
        markerIconRepository.saveAndFlush(markerIcon);

        // Get all the markerIconList where shadowSize in DEFAULT_SHADOW_SIZE or UPDATED_SHADOW_SIZE
        defaultMarkerIconShouldBeFound("shadowSize.in=" + DEFAULT_SHADOW_SIZE + "," + UPDATED_SHADOW_SIZE);

        // Get all the markerIconList where shadowSize equals to UPDATED_SHADOW_SIZE
        defaultMarkerIconShouldNotBeFound("shadowSize.in=" + UPDATED_SHADOW_SIZE);
    }

    @Test
    @Transactional
    public void getAllMarkerIconsByShadowSizeIsNullOrNotNull() throws Exception {
        // Initialize the database
        markerIconRepository.saveAndFlush(markerIcon);

        // Get all the markerIconList where shadowSize is not null
        defaultMarkerIconShouldBeFound("shadowSize.specified=true");

        // Get all the markerIconList where shadowSize is null
        defaultMarkerIconShouldNotBeFound("shadowSize.specified=false");
    }

    @Test
    @Transactional
    public void getAllMarkerIconsByIconAnchorIsEqualToSomething() throws Exception {
        // Initialize the database
        markerIconRepository.saveAndFlush(markerIcon);

        // Get all the markerIconList where iconAnchor equals to DEFAULT_ICON_ANCHOR
        defaultMarkerIconShouldBeFound("iconAnchor.equals=" + DEFAULT_ICON_ANCHOR);

        // Get all the markerIconList where iconAnchor equals to UPDATED_ICON_ANCHOR
        defaultMarkerIconShouldNotBeFound("iconAnchor.equals=" + UPDATED_ICON_ANCHOR);
    }

    @Test
    @Transactional
    public void getAllMarkerIconsByIconAnchorIsInShouldWork() throws Exception {
        // Initialize the database
        markerIconRepository.saveAndFlush(markerIcon);

        // Get all the markerIconList where iconAnchor in DEFAULT_ICON_ANCHOR or UPDATED_ICON_ANCHOR
        defaultMarkerIconShouldBeFound("iconAnchor.in=" + DEFAULT_ICON_ANCHOR + "," + UPDATED_ICON_ANCHOR);

        // Get all the markerIconList where iconAnchor equals to UPDATED_ICON_ANCHOR
        defaultMarkerIconShouldNotBeFound("iconAnchor.in=" + UPDATED_ICON_ANCHOR);
    }

    @Test
    @Transactional
    public void getAllMarkerIconsByIconAnchorIsNullOrNotNull() throws Exception {
        // Initialize the database
        markerIconRepository.saveAndFlush(markerIcon);

        // Get all the markerIconList where iconAnchor is not null
        defaultMarkerIconShouldBeFound("iconAnchor.specified=true");

        // Get all the markerIconList where iconAnchor is null
        defaultMarkerIconShouldNotBeFound("iconAnchor.specified=false");
    }

    @Test
    @Transactional
    public void getAllMarkerIconsByShadowAnchorIsEqualToSomething() throws Exception {
        // Initialize the database
        markerIconRepository.saveAndFlush(markerIcon);

        // Get all the markerIconList where shadowAnchor equals to DEFAULT_SHADOW_ANCHOR
        defaultMarkerIconShouldBeFound("shadowAnchor.equals=" + DEFAULT_SHADOW_ANCHOR);

        // Get all the markerIconList where shadowAnchor equals to UPDATED_SHADOW_ANCHOR
        defaultMarkerIconShouldNotBeFound("shadowAnchor.equals=" + UPDATED_SHADOW_ANCHOR);
    }

    @Test
    @Transactional
    public void getAllMarkerIconsByShadowAnchorIsInShouldWork() throws Exception {
        // Initialize the database
        markerIconRepository.saveAndFlush(markerIcon);

        // Get all the markerIconList where shadowAnchor in DEFAULT_SHADOW_ANCHOR or UPDATED_SHADOW_ANCHOR
        defaultMarkerIconShouldBeFound("shadowAnchor.in=" + DEFAULT_SHADOW_ANCHOR + "," + UPDATED_SHADOW_ANCHOR);

        // Get all the markerIconList where shadowAnchor equals to UPDATED_SHADOW_ANCHOR
        defaultMarkerIconShouldNotBeFound("shadowAnchor.in=" + UPDATED_SHADOW_ANCHOR);
    }

    @Test
    @Transactional
    public void getAllMarkerIconsByShadowAnchorIsNullOrNotNull() throws Exception {
        // Initialize the database
        markerIconRepository.saveAndFlush(markerIcon);

        // Get all the markerIconList where shadowAnchor is not null
        defaultMarkerIconShouldBeFound("shadowAnchor.specified=true");

        // Get all the markerIconList where shadowAnchor is null
        defaultMarkerIconShouldNotBeFound("shadowAnchor.specified=false");
    }

    @Test
    @Transactional
    public void getAllMarkerIconsByPopupAnchorIsEqualToSomething() throws Exception {
        // Initialize the database
        markerIconRepository.saveAndFlush(markerIcon);

        // Get all the markerIconList where popupAnchor equals to DEFAULT_POPUP_ANCHOR
        defaultMarkerIconShouldBeFound("popupAnchor.equals=" + DEFAULT_POPUP_ANCHOR);

        // Get all the markerIconList where popupAnchor equals to UPDATED_POPUP_ANCHOR
        defaultMarkerIconShouldNotBeFound("popupAnchor.equals=" + UPDATED_POPUP_ANCHOR);
    }

    @Test
    @Transactional
    public void getAllMarkerIconsByPopupAnchorIsInShouldWork() throws Exception {
        // Initialize the database
        markerIconRepository.saveAndFlush(markerIcon);

        // Get all the markerIconList where popupAnchor in DEFAULT_POPUP_ANCHOR or UPDATED_POPUP_ANCHOR
        defaultMarkerIconShouldBeFound("popupAnchor.in=" + DEFAULT_POPUP_ANCHOR + "," + UPDATED_POPUP_ANCHOR);

        // Get all the markerIconList where popupAnchor equals to UPDATED_POPUP_ANCHOR
        defaultMarkerIconShouldNotBeFound("popupAnchor.in=" + UPDATED_POPUP_ANCHOR);
    }

    @Test
    @Transactional
    public void getAllMarkerIconsByPopupAnchorIsNullOrNotNull() throws Exception {
        // Initialize the database
        markerIconRepository.saveAndFlush(markerIcon);

        // Get all the markerIconList where popupAnchor is not null
        defaultMarkerIconShouldBeFound("popupAnchor.specified=true");

        // Get all the markerIconList where popupAnchor is null
        defaultMarkerIconShouldNotBeFound("popupAnchor.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultMarkerIconShouldBeFound(String filter) throws Exception {
        restMarkerIconMockMvc.perform(get("/api/marker-icons?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(markerIcon.getId().intValue())))
            .andExpect(jsonPath("$.[*].iconContentType").value(hasItem(DEFAULT_ICON_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].icon").value(hasItem(Base64Utils.encodeToString(DEFAULT_ICON))))
            .andExpect(jsonPath("$.[*].shadowContentType").value(hasItem(DEFAULT_SHADOW_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].shadow").value(hasItem(Base64Utils.encodeToString(DEFAULT_SHADOW))))
            .andExpect(jsonPath("$.[*].iconSize").value(hasItem(DEFAULT_ICON_SIZE.toString())))
            .andExpect(jsonPath("$.[*].shadowSize").value(hasItem(DEFAULT_SHADOW_SIZE.toString())))
            .andExpect(jsonPath("$.[*].iconAnchor").value(hasItem(DEFAULT_ICON_ANCHOR.toString())))
            .andExpect(jsonPath("$.[*].shadowAnchor").value(hasItem(DEFAULT_SHADOW_ANCHOR.toString())))
            .andExpect(jsonPath("$.[*].popupAnchor").value(hasItem(DEFAULT_POPUP_ANCHOR.toString())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultMarkerIconShouldNotBeFound(String filter) throws Exception {
        restMarkerIconMockMvc.perform(get("/api/marker-icons?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    @Transactional
    public void getNonExistingMarkerIcon() throws Exception {
        // Get the markerIcon
        restMarkerIconMockMvc.perform(get("/api/marker-icons/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMarkerIcon() throws Exception {
        // Initialize the database
        markerIconService.save(markerIcon);

        int databaseSizeBeforeUpdate = markerIconRepository.findAll().size();

        // Update the markerIcon
        MarkerIcon updatedMarkerIcon = markerIconRepository.findById(markerIcon.getId()).get();
        // Disconnect from session so that the updates on updatedMarkerIcon are not directly saved in db
        em.detach(updatedMarkerIcon);
        updatedMarkerIcon
            .icon(UPDATED_ICON)
            .iconContentType(UPDATED_ICON_CONTENT_TYPE)
            .shadow(UPDATED_SHADOW)
            .shadowContentType(UPDATED_SHADOW_CONTENT_TYPE)
            .iconSize(UPDATED_ICON_SIZE)
            .shadowSize(UPDATED_SHADOW_SIZE)
            .iconAnchor(UPDATED_ICON_ANCHOR)
            .shadowAnchor(UPDATED_SHADOW_ANCHOR)
            .popupAnchor(UPDATED_POPUP_ANCHOR);

        restMarkerIconMockMvc.perform(put("/api/marker-icons")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMarkerIcon)))
            .andExpect(status().isOk());

        // Validate the MarkerIcon in the database
        List<MarkerIcon> markerIconList = markerIconRepository.findAll();
        assertThat(markerIconList).hasSize(databaseSizeBeforeUpdate);
        MarkerIcon testMarkerIcon = markerIconList.get(markerIconList.size() - 1);
        assertThat(testMarkerIcon.getIcon()).isEqualTo(UPDATED_ICON);
        assertThat(testMarkerIcon.getIconContentType()).isEqualTo(UPDATED_ICON_CONTENT_TYPE);
        assertThat(testMarkerIcon.getShadow()).isEqualTo(UPDATED_SHADOW);
        assertThat(testMarkerIcon.getShadowContentType()).isEqualTo(UPDATED_SHADOW_CONTENT_TYPE);
        assertThat(testMarkerIcon.getIconSize()).isEqualTo(UPDATED_ICON_SIZE);
        assertThat(testMarkerIcon.getShadowSize()).isEqualTo(UPDATED_SHADOW_SIZE);
        assertThat(testMarkerIcon.getIconAnchor()).isEqualTo(UPDATED_ICON_ANCHOR);
        assertThat(testMarkerIcon.getShadowAnchor()).isEqualTo(UPDATED_SHADOW_ANCHOR);
        assertThat(testMarkerIcon.getPopupAnchor()).isEqualTo(UPDATED_POPUP_ANCHOR);
    }

    @Test
    @Transactional
    public void updateNonExistingMarkerIcon() throws Exception {
        int databaseSizeBeforeUpdate = markerIconRepository.findAll().size();

        // Create the MarkerIcon

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMarkerIconMockMvc.perform(put("/api/marker-icons")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(markerIcon)))
            .andExpect(status().isBadRequest());

        // Validate the MarkerIcon in the database
        List<MarkerIcon> markerIconList = markerIconRepository.findAll();
        assertThat(markerIconList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMarkerIcon() throws Exception {
        // Initialize the database
        markerIconService.save(markerIcon);

        int databaseSizeBeforeDelete = markerIconRepository.findAll().size();

        // Get the markerIcon
        restMarkerIconMockMvc.perform(delete("/api/marker-icons/{id}", markerIcon.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<MarkerIcon> markerIconList = markerIconRepository.findAll();
        assertThat(markerIconList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MarkerIcon.class);
        MarkerIcon markerIcon1 = new MarkerIcon();
        markerIcon1.setId(1L);
        MarkerIcon markerIcon2 = new MarkerIcon();
        markerIcon2.setId(markerIcon1.getId());
        assertThat(markerIcon1).isEqualTo(markerIcon2);
        markerIcon2.setId(2L);
        assertThat(markerIcon1).isNotEqualTo(markerIcon2);
        markerIcon1.setId(null);
        assertThat(markerIcon1).isNotEqualTo(markerIcon2);
    }
}
