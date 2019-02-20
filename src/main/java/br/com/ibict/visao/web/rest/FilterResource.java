package br.com.ibict.visao.web.rest;

import br.com.ibict.visao.dto.FilterInfoProjection;
import com.codahale.metrics.annotation.Timed;
import br.com.ibict.visao.domain.Filter;
import br.com.ibict.visao.service.FilterService;
import br.com.ibict.visao.web.rest.errors.BadRequestAlertException;
import br.com.ibict.visao.web.rest.util.HeaderUtil;
import br.com.ibict.visao.web.rest.util.PaginationUtil;
import br.com.ibict.visao.service.dto.FilterCriteria;
import br.com.ibict.visao.service.FilterQueryService;
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
 * REST controller for managing Filter.
 */
@RestController
@RequestMapping("/api")
public class FilterResource {

    private final Logger log = LoggerFactory.getLogger(FilterResource.class);

    private static final String ENTITY_NAME = "filter";

    private final FilterService filterService;

    private final FilterQueryService filterQueryService;

    public FilterResource(FilterService filterService, FilterQueryService filterQueryService) {
        this.filterService = filterService;
        this.filterQueryService = filterQueryService;
    }

    /**
     * POST  /filters : Create a new filter.
     *
     * @param filter the filter to create
     * @return the ResponseEntity with status 201 (Created) and with body the new filter, or with status 400 (Bad Request) if the filter has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/filters")
    @Timed
    public ResponseEntity<Filter> createFilter(@Valid @RequestBody Filter filter) throws URISyntaxException {
        log.debug("REST request to save Filter : {}", filter);
        if (filter.getId() != null) {
            throw new BadRequestAlertException("A new filter cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Filter result = filterService.save(filter);
        return ResponseEntity.created(new URI("/api/filters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /filters : Updates an existing filter.
     *
     * @param filter the filter to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated filter,
     * or with status 400 (Bad Request) if the filter is not valid,
     * or with status 500 (Internal Server Error) if the filter couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/filters")
    @Timed
    public ResponseEntity<Filter> updateFilter(@Valid @RequestBody Filter filter) throws URISyntaxException {
        log.debug("REST request to update Filter : {}", filter);
        if (filter.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Filter result = filterService.save(filter);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, filter.getId().toString()))
            .body(result);
    }

    /**
     * GET  /filters : get all the filters.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of filters in body
     */
    @GetMapping("/filters")
    @Timed
    public ResponseEntity<List<Filter>> getAllFilters(FilterCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Filters by criteria: {}", criteria);
        Page<Filter> page = filterQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/filters");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /filters : get all the filters.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of filters in body
     */
    @GetMapping("/filtersDTO")
    @Timed
    public ResponseEntity<List<FilterInfoProjection>>  findFiltersDTOWithId(FilterCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Filters by criteria: {}", criteria);
        Page<FilterInfoProjection> page = filterQueryService.findFiltersDTOWithId(criteria.getId().getIn(), pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/filtersDTO");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /filters/:id : get the "id" filter.
     *
     * @param id the id of the filter to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the filter, or with status 404 (Not Found)
     */
    @GetMapping("/filters/{id}")
    @Timed
    public ResponseEntity<Filter> getFilter(@PathVariable Long id) {
        log.debug("REST request to get Filter : {}", id);
        Optional<Filter> filter = filterService.findOne(id);
        return ResponseUtil.wrapOrNotFound(filter);
    }

    /**
     * DELETE  /filters/:id : delete the "id" filter.
     *
     * @param id the id of the filter to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/filters/{id}")
    @Timed
    public ResponseEntity<Void> deleteFilter(@PathVariable Long id) {
        log.debug("REST request to delete Filter : {}", id);
        filterService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
