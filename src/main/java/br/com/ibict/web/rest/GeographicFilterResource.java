package br.com.ibict.web.rest;

import com.codahale.metrics.annotation.Timed;
import br.com.ibict.domain.GeographicFilter;
import br.com.ibict.service.GeographicFilterService;
import br.com.ibict.web.rest.errors.BadRequestAlertException;
import br.com.ibict.web.rest.util.HeaderUtil;
import br.com.ibict.web.rest.util.PaginationUtil;
import br.com.ibict.service.dto.GeographicFilterCriteria;
import br.com.ibict.service.GeographicFilterQueryService;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing GeographicFilter.
 */
@RestController
@RequestMapping("/api")
public class GeographicFilterResource {

    private final Logger log = LoggerFactory.getLogger(GeographicFilterResource.class);

    private static final String ENTITY_NAME = "geographicFilter";

    private final GeographicFilterService geographicFilterService;

    private final GeographicFilterQueryService geographicFilterQueryService;

    public GeographicFilterResource(GeographicFilterService geographicFilterService, GeographicFilterQueryService geographicFilterQueryService) {
        this.geographicFilterService = geographicFilterService;
        this.geographicFilterQueryService = geographicFilterQueryService;
    }

    /**
     * POST  /geographic-filters : Create a new geographicFilter.
     *
     * @param geographicFilter the geographicFilter to create
     * @return the ResponseEntity with status 201 (Created) and with body the new geographicFilter, or with status 400 (Bad Request) if the geographicFilter has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/geographic-filters")
    @Timed
    public ResponseEntity<GeographicFilter> createGeographicFilter(@Valid @RequestBody GeographicFilter geographicFilter) throws URISyntaxException {
        log.debug("REST request to save GeographicFilter : {}", geographicFilter);
        if (geographicFilter.getId() != null) {
            throw new BadRequestAlertException("A new geographicFilter cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GeographicFilter result = geographicFilterService.save(geographicFilter);
        return ResponseEntity.created(new URI("/api/geographic-filters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /geographic-filters : Updates an existing geographicFilter.
     *
     * @param geographicFilter the geographicFilter to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated geographicFilter,
     * or with status 400 (Bad Request) if the geographicFilter is not valid,
     * or with status 500 (Internal Server Error) if the geographicFilter couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/geographic-filters")
    @Timed
    public ResponseEntity<GeographicFilter> updateGeographicFilter(@Valid @RequestBody GeographicFilter geographicFilter) throws URISyntaxException {
        log.debug("REST request to update GeographicFilter : {}", geographicFilter);
        if (geographicFilter.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        GeographicFilter result = geographicFilterService.save(geographicFilter);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, geographicFilter.getId().toString()))
            .body(result);
    }

    /**
     * GET  /geographic-filters : get all the geographicFilters.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of geographicFilters in body
     */
    @GetMapping("/geographic-filters")
    @Timed
    public ResponseEntity<List<GeographicFilter>> getAllGeographicFilters(GeographicFilterCriteria criteria, Pageable pageable) {
        log.debug("REST request to get GeographicFilters by criteria: {}", criteria);
        Page<GeographicFilter> page = geographicFilterQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/geographic-filters");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /geographic-filters/:id : get the "id" geographicFilter.
     *
     * @param id the id of the geographicFilter to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the geographicFilter, or with status 404 (Not Found)
     */
    @GetMapping("/geographic-filters/{id}")
    @Timed
    public ResponseEntity<GeographicFilter> getGeographicFilter(@PathVariable Long id) {
        log.debug("REST request to get GeographicFilter : {}", id);
        Optional<GeographicFilter> geographicFilter = geographicFilterService.findOne(id);
        return ResponseUtil.wrapOrNotFound(geographicFilter);
    }

    /**
     * DELETE  /geographic-filters/:id : delete the "id" geographicFilter.
     *
     * @param id the id of the geographicFilter to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/geographic-filters/{id}")
    @Timed
    public ResponseEntity<Void> deleteGeographicFilter(@PathVariable Long id) {
        log.debug("REST request to delete GeographicFilter : {}", id);
        geographicFilterService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
