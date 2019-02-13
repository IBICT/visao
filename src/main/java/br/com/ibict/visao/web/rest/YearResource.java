package br.com.ibict.visao.web.rest;

import com.codahale.metrics.annotation.Timed;
import br.com.ibict.visao.domain.Year;
import br.com.ibict.visao.service.YearService;
import br.com.ibict.visao.web.rest.errors.BadRequestAlertException;
import br.com.ibict.visao.web.rest.util.HeaderUtil;
import br.com.ibict.visao.web.rest.util.PaginationUtil;
import br.com.ibict.visao.service.dto.YearCriteria;
import br.com.ibict.visao.service.YearQueryService;
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
 * REST controller for managing Year.
 */
@RestController
@RequestMapping("/api")
public class YearResource {

    private final Logger log = LoggerFactory.getLogger(YearResource.class);

    private static final String ENTITY_NAME = "year";

    private final YearService yearService;

    private final YearQueryService yearQueryService;

    public YearResource(YearService yearService, YearQueryService yearQueryService) {
        this.yearService = yearService;
        this.yearQueryService = yearQueryService;
    }

    /**
     * POST  /years : Create a new year.
     *
     * @param year the year to create
     * @return the ResponseEntity with status 201 (Created) and with body the new year, or with status 400 (Bad Request) if the year has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/years")
    @Timed
    public ResponseEntity<Year> createYear(@Valid @RequestBody Year year) throws URISyntaxException {
        log.debug("REST request to save Year : {}", year);
        if (year.getId() != null) {
            throw new BadRequestAlertException("A new year cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Year result = yearService.save(year);
        return ResponseEntity.created(new URI("/api/years/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /years : Updates an existing year.
     *
     * @param year the year to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated year,
     * or with status 400 (Bad Request) if the year is not valid,
     * or with status 500 (Internal Server Error) if the year couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/years")
    @Timed
    public ResponseEntity<Year> updateYear(@Valid @RequestBody Year year) throws URISyntaxException {
        log.debug("REST request to update Year : {}", year);
        if (year.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Year result = yearService.save(year);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, year.getId().toString()))
            .body(result);
    }

    /**
     * GET  /years : get all the years.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of years in body
     */
    @GetMapping("/years")
    @Timed
    public ResponseEntity<List<Year>> getAllYears(YearCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Years by criteria: {}", criteria);
        Page<Year> page = yearQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/years");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /years/:id : get the "id" year.
     *
     * @param id the id of the year to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the year, or with status 404 (Not Found)
     */
    @GetMapping("/years/{id}")
    @Timed
    public ResponseEntity<Year> getYear(@PathVariable Long id) {
        log.debug("REST request to get Year : {}", id);
        Optional<Year> year = yearService.findOne(id);
        return ResponseUtil.wrapOrNotFound(year);
    }

    /**
     * DELETE  /years/:id : delete the "id" year.
     *
     * @param id the id of the year to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/years/{id}")
    @Timed
    public ResponseEntity<Void> deleteYear(@PathVariable Long id) {
        log.debug("REST request to delete Year : {}", id);
        yearService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
