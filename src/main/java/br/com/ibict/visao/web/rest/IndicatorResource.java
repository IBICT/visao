package br.com.ibict.visao.web.rest;

import br.com.ibict.visao.domain.Indicator;
import br.com.ibict.visao.security.AuthoritiesConstants;
import br.com.ibict.visao.service.IndicatorQueryService;
import br.com.ibict.visao.service.IndicatorService;
import br.com.ibict.visao.service.dto.IndicatorCriteria;
import br.com.ibict.visao.web.rest.errors.BadRequestAlertException;
import br.com.ibict.visao.web.rest.util.HeaderUtil;
import br.com.ibict.visao.web.rest.util.PaginationUtil;
import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Indicator.
 */
@RestController
@RequestMapping("/api")
public class IndicatorResource {

    private final Logger log = LoggerFactory.getLogger(IndicatorResource.class);

    private static final String ENTITY_NAME = "indicator";

    private final IndicatorService indicatorService;

    private final IndicatorQueryService indicatorQueryService;

    public IndicatorResource(IndicatorService indicatorService, IndicatorQueryService indicatorQueryService) {
        this.indicatorService = indicatorService;
        this.indicatorQueryService = indicatorQueryService;
    }

    /**
     * POST  /indicators : Create a new indicator.
     *
     * @param indicator the indicator to create
     * @return the ResponseEntity with status 201 (Created) and with body the new indicator, or with status 400 (Bad Request) if the indicator has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/indicators")
    @Timed
    public ResponseEntity<Indicator> createIndicator(@Valid @RequestBody Indicator indicator) throws URISyntaxException {
        log.debug("REST request to save Indicator : {}", indicator);
        if (indicator.getId() != null) {
            throw new BadRequestAlertException("A new indicator cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Indicator result = indicatorService.save(indicator);
        return ResponseEntity.created(new URI("/api/indicators/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /indicators : Updates an existing indicator.
     *
     * @param indicator the indicator to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated indicator,
     * or with status 400 (Bad Request) if the indicator is not valid,
     * or with status 500 (Internal Server Error) if the indicator couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/indicators")
    @Timed
    @Secured(AuthoritiesConstants.ADMIN)
    public ResponseEntity<Indicator> updateIndicator(@Valid @RequestBody Indicator indicator) throws URISyntaxException {
        log.debug("REST request to update Indicator : {}", indicator);
        if (indicator.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Indicator result = indicatorService.save(indicator);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, indicator.getId().toString()))
            .body(result);
    }

    /**
     * GET  /indicators : get all the indicators.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of indicators in body
     */
    @GetMapping("/indicators")
    @Timed
    public ResponseEntity<List<Indicator>> getAllIndicators(IndicatorCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Indicators by criteria: {}", criteria);
        findMostRecentYearFromIndicator(criteria);
        Page<Indicator> page = indicatorQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/indicators");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /indicatorsFilters : get all the indicators with filter.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of indicators in body
     */
    @GetMapping("/indicatorsFilter")
    @Timed
    public ResponseEntity<List<Indicator>> getAllIndicatorsWithFilter(IndicatorCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Indicators by criteria: {}", criteria);
        findMostRecentYearFromIndicator(criteria);
        Page<Indicator> page = indicatorQueryService.findIndicatorWithFilters(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/indicatorsFilter");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    private void findMostRecentYearFromIndicator(IndicatorCriteria indicatorCriteria){
        if (indicatorCriteria.getYearId() == null && indicatorCriteria.getNameId() != null){
            LongFilter longFilter = new LongFilter();
            longFilter.setEquals(indicatorQueryService.findMostRecentYearFromIndicator(indicatorCriteria.getNameId().getEquals()));
            indicatorCriteria.setYearId(longFilter);
        }
    }

    /**
     * GET  /indicators/:id : get the "id" indicator.
     *
     * @param id the id of the indicator to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the indicator, or with status 404 (Not Found)
     */
    @GetMapping("/indicators/{id}")
    @Timed
    public ResponseEntity<Indicator> getIndicator(@PathVariable Long id) {
        log.debug("REST request to get Indicator : {}", id);
        Optional<Indicator> indicator = indicatorService.findOne(id);
        return ResponseUtil.wrapOrNotFound(indicator);
    }

    /**
     * DELETE  /indicators/:id : delete the "id" indicator.
     *
     * @param id the id of the indicator to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/indicators/{id}")
    @Timed
    @Secured(AuthoritiesConstants.ADMIN)
    public ResponseEntity<Void> deleteIndicator(@PathVariable Long id) {
        log.debug("REST request to delete Indicator : {}", id);
        indicatorService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
