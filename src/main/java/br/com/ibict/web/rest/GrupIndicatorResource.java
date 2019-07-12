package br.com.ibict.web.rest;

import com.codahale.metrics.annotation.Timed;
import br.com.ibict.domain.GrupIndicator;
import br.com.ibict.service.GrupIndicatorService;
import br.com.ibict.web.rest.errors.BadRequestAlertException;
import br.com.ibict.web.rest.util.HeaderUtil;
import br.com.ibict.web.rest.util.PaginationUtil;
import br.com.ibict.service.dto.GrupIndicatorCriteria;
import br.com.ibict.service.GrupIndicatorQueryService;
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
 * REST controller for managing GrupIndicator.
 */
@RestController
@RequestMapping("/api")
public class GrupIndicatorResource {

    private final Logger log = LoggerFactory.getLogger(GrupIndicatorResource.class);

    private static final String ENTITY_NAME = "grupIndicator";

    private final GrupIndicatorService grupIndicatorService;

    private final GrupIndicatorQueryService grupIndicatorQueryService;

    public GrupIndicatorResource(GrupIndicatorService grupIndicatorService, GrupIndicatorQueryService grupIndicatorQueryService) {
        this.grupIndicatorService = grupIndicatorService;
        this.grupIndicatorQueryService = grupIndicatorQueryService;
    }

    /**
     * POST  /grup-indicators : Create a new grupIndicator.
     *
     * @param grupIndicator the grupIndicator to create
     * @return the ResponseEntity with status 201 (Created) and with body the new grupIndicator, or with status 400 (Bad Request) if the grupIndicator has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/grup-indicators")
    @Timed
    public ResponseEntity<GrupIndicator> createGrupIndicator(@Valid @RequestBody GrupIndicator grupIndicator) throws URISyntaxException {
        log.debug("REST request to save GrupIndicator : {}", grupIndicator);
        if (grupIndicator.getId() != null) {
            throw new BadRequestAlertException("A new grupIndicator cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GrupIndicator result = grupIndicatorService.save(grupIndicator);
        return ResponseEntity.created(new URI("/api/grup-indicators/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /grup-indicators : Updates an existing grupIndicator.
     *
     * @param grupIndicator the grupIndicator to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated grupIndicator,
     * or with status 400 (Bad Request) if the grupIndicator is not valid,
     * or with status 500 (Internal Server Error) if the grupIndicator couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/grup-indicators")
    @Timed
    public ResponseEntity<GrupIndicator> updateGrupIndicator(@Valid @RequestBody GrupIndicator grupIndicator) throws URISyntaxException {
        log.debug("REST request to update GrupIndicator : {}", grupIndicator);
        if (grupIndicator.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        GrupIndicator result = grupIndicatorService.save(grupIndicator);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, grupIndicator.getId().toString()))
            .body(result);
    }

    /**
     * GET  /grup-indicators : get all the grupIndicators.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of grupIndicators in body
     */
    @GetMapping("/grup-indicators")
    @Timed
    public ResponseEntity<List<GrupIndicator>> getAllGrupIndicators(GrupIndicatorCriteria criteria, Pageable pageable) {
        log.debug("REST request to get GrupIndicators by criteria: {}", criteria);
        Page<GrupIndicator> page = grupIndicatorQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/grup-indicators");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /grup-indicators/:id : get the "id" grupIndicator.
     *
     * @param id the id of the grupIndicator to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the grupIndicator, or with status 404 (Not Found)
     */
    @GetMapping("/grup-indicators/{id}")
    @Timed
    public ResponseEntity<GrupIndicator> getGrupIndicator(@PathVariable Long id) {
        log.debug("REST request to get GrupIndicator : {}", id);
        Optional<GrupIndicator> grupIndicator = grupIndicatorService.findOne(id);
        return ResponseUtil.wrapOrNotFound(grupIndicator);
    }

    /**
     * DELETE  /grup-indicators/:id : delete the "id" grupIndicator.
     *
     * @param id the id of the grupIndicator to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/grup-indicators/{id}")
    @Timed
    public ResponseEntity<Void> deleteGrupIndicator(@PathVariable Long id) {
        log.debug("REST request to delete GrupIndicator : {}", id);
        grupIndicatorService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
