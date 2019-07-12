package br.com.ibict.web.rest;

import com.codahale.metrics.annotation.Timed;
import br.com.ibict.domain.Chart;
import br.com.ibict.service.ChartService;
import br.com.ibict.web.rest.errors.BadRequestAlertException;
import br.com.ibict.web.rest.util.HeaderUtil;
import br.com.ibict.web.rest.util.PaginationUtil;
import br.com.ibict.service.dto.ChartCriteria;
import br.com.ibict.service.ChartQueryService;
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
 * REST controller for managing Chart.
 */
@RestController
@RequestMapping("/api")
public class ChartResource {

    private final Logger log = LoggerFactory.getLogger(ChartResource.class);

    private static final String ENTITY_NAME = "chart";

    private final ChartService chartService;

    private final ChartQueryService chartQueryService;

    public ChartResource(ChartService chartService, ChartQueryService chartQueryService) {
        this.chartService = chartService;
        this.chartQueryService = chartQueryService;
    }

    /**
     * POST  /charts : Create a new chart.
     *
     * @param chart the chart to create
     * @return the ResponseEntity with status 201 (Created) and with body the new chart, or with status 400 (Bad Request) if the chart has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/charts")
    @Timed
    public ResponseEntity<Chart> createChart(@RequestBody Chart chart) throws URISyntaxException {
        log.debug("REST request to save Chart : {}", chart);
        if (chart.getId() != null) {
            throw new BadRequestAlertException("A new chart cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Chart result = chartService.save(chart);
        return ResponseEntity.created(new URI("/api/charts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /charts : Updates an existing chart.
     *
     * @param chart the chart to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated chart,
     * or with status 400 (Bad Request) if the chart is not valid,
     * or with status 500 (Internal Server Error) if the chart couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/charts")
    @Timed
    public ResponseEntity<Chart> updateChart(@RequestBody Chart chart) throws URISyntaxException {
        log.debug("REST request to update Chart : {}", chart);
        if (chart.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Chart result = chartService.save(chart);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, chart.getId().toString()))
            .body(result);
    }

    /**
     * GET  /charts : get all the charts.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of charts in body
     */
    @GetMapping("/charts")
    @Timed
    public ResponseEntity<List<Chart>> getAllCharts(ChartCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Charts by criteria: {}", criteria);
        Page<Chart> page = chartQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/charts");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /charts/:id : get the "id" chart.
     *
     * @param id the id of the chart to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the chart, or with status 404 (Not Found)
     */
    @GetMapping("/charts/{id}")
    @Timed
    public ResponseEntity<Chart> getChart(@PathVariable Long id) {
        log.debug("REST request to get Chart : {}", id);
        Optional<Chart> chart = chartService.findOne(id);
        return ResponseUtil.wrapOrNotFound(chart);
    }

    /**
     * DELETE  /charts/:id : delete the "id" chart.
     *
     * @param id the id of the chart to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/charts/{id}")
    @Timed
    public ResponseEntity<Void> deleteChart(@PathVariable Long id) {
        log.debug("REST request to delete Chart : {}", id);
        chartService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
