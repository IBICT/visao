package br.com.ibict.web.rest;

import com.codahale.metrics.annotation.Timed;
import br.com.ibict.domain.GrupCategory;
import br.com.ibict.service.GrupCategoryService;
import br.com.ibict.web.rest.errors.BadRequestAlertException;
import br.com.ibict.web.rest.util.HeaderUtil;
import br.com.ibict.web.rest.util.PaginationUtil;
import br.com.ibict.service.dto.GrupCategoryCriteria;
import br.com.ibict.service.GrupCategoryQueryService;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing GrupCategory.
 */
@RestController
@RequestMapping("/api")
public class GrupCategoryResource {

    private final Logger log = LoggerFactory.getLogger(GrupCategoryResource.class);

    private static final String ENTITY_NAME = "grupCategory";

    private final GrupCategoryService grupCategoryService;

    private final GrupCategoryQueryService grupCategoryQueryService;

    public GrupCategoryResource(GrupCategoryService grupCategoryService, GrupCategoryQueryService grupCategoryQueryService) {
        this.grupCategoryService = grupCategoryService;
        this.grupCategoryQueryService = grupCategoryQueryService;
    }

    /**
     * POST  /grup-categories : Create a new grupCategory.
     *
     * @param grupCategory the grupCategory to create
     * @return the ResponseEntity with status 201 (Created) and with body the new grupCategory, or with status 400 (Bad Request) if the grupCategory has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/grup-categories")
    @Timed
    public ResponseEntity<GrupCategory> createGrupCategory(@RequestBody GrupCategory grupCategory) throws URISyntaxException {
        log.debug("REST request to save GrupCategory : {}", grupCategory);
        if (grupCategory.getId() != null) {
            throw new BadRequestAlertException("A new grupCategory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GrupCategory result = grupCategoryService.save(grupCategory);
        return ResponseEntity.created(new URI("/api/grup-categories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /grup-categories : Updates an existing grupCategory.
     *
     * @param grupCategory the grupCategory to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated grupCategory,
     * or with status 400 (Bad Request) if the grupCategory is not valid,
     * or with status 500 (Internal Server Error) if the grupCategory couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/grup-categories")
    @Timed
    public ResponseEntity<GrupCategory> updateGrupCategory(@RequestBody GrupCategory grupCategory) throws URISyntaxException {
        log.debug("REST request to update GrupCategory : {}", grupCategory);
        if (grupCategory.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        GrupCategory result = grupCategoryService.save(grupCategory);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, grupCategory.getId().toString()))
            .body(result);
    }

    /**
     * GET  /grup-categories : get all the grupCategories.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of grupCategories in body
     */
    @GetMapping("/grup-categories")
    @Timed
    public ResponseEntity<List<GrupCategory>> getAllGrupCategories(GrupCategoryCriteria criteria, Pageable pageable) {
        log.debug("REST request to get GrupCategories by criteria: {}", criteria);
        Page<GrupCategory> page = grupCategoryQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/grup-categories");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /grup-categories/:id : get the "id" grupCategory.
     *
     * @param id the id of the grupCategory to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the grupCategory, or with status 404 (Not Found)
     */
    @GetMapping("/grup-categories/{id}")
    @Timed
    public ResponseEntity<GrupCategory> getGrupCategory(@PathVariable Long id) {
        log.debug("REST request to get GrupCategory : {}", id);
        Optional<GrupCategory> grupCategory = grupCategoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(grupCategory);
    }

    /**
     * DELETE  /grup-categories/:id : delete the "id" grupCategory.
     *
     * @param id the id of the grupCategory to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/grup-categories/{id}")
    @Timed
    public ResponseEntity<Void> deleteGrupCategory(@PathVariable Long id) {
        log.debug("REST request to delete GrupCategory : {}", id);
        grupCategoryService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
