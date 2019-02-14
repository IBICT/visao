package br.com.ibict.visao.web.rest;

import br.com.ibict.visao.dto.RegionFilterProjection;
import com.codahale.metrics.annotation.Timed;
import br.com.ibict.visao.domain.Region;
import br.com.ibict.visao.service.RegionService;
import br.com.ibict.visao.web.rest.errors.BadRequestAlertException;
import br.com.ibict.visao.web.rest.util.HeaderUtil;
import br.com.ibict.visao.web.rest.util.PaginationUtil;
import br.com.ibict.visao.service.dto.RegionCriteria;
import br.com.ibict.visao.service.RegionQueryService;
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
 * REST controller for managing Region.
 */
@RestController
@RequestMapping("/api")
public class RegionResource {

    private final Logger log = LoggerFactory.getLogger(RegionResource.class);

    private static final String ENTITY_NAME = "region";

    private final RegionService regionService;

    private final RegionQueryService regionQueryService;

    public RegionResource(RegionService regionService, RegionQueryService regionQueryService) {
        this.regionService = regionService;
        this.regionQueryService = regionQueryService;
    }

    /**
     * POST  /regions : Create a new region.
     *
     * @param region the region to create
     * @return the ResponseEntity with status 201 (Created) and with body the new region, or with status 400 (Bad Request) if the region has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/regions")
    @Timed
    public ResponseEntity<Region> createRegion(@Valid @RequestBody Region region) throws URISyntaxException {
        log.debug("REST request to save Region : {}", region);
        if (region.getId() != null) {
            throw new BadRequestAlertException("A new region cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Region result = regionService.save(region);
        return ResponseEntity.created(new URI("/api/regions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /regions : Updates an existing region.
     *
     * @param region the region to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated region,
     * or with status 400 (Bad Request) if the region is not valid,
     * or with status 500 (Internal Server Error) if the region couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/regions")
    @Timed
    public ResponseEntity<Region> updateRegion(@Valid @RequestBody Region region) throws URISyntaxException {
        log.debug("REST request to update Region : {}", region);
        if (region.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Region result = regionService.save(region);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, region.getId().toString()))
            .body(result);
    }

    /**
     * GET  /regions : get all the regions.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of regions in body
     */
    @GetMapping("/regions")
    @Timed
    public ResponseEntity<List<Region>> getAllRegions(RegionCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Regions by criteria: {}", criteria);
        Page<Region> page = regionQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/regions");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /regions : get all the regions.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of regions in body
     */
    @GetMapping("/regionsWithFilter")
    @Timed
    public ResponseEntity<List<RegionFilterProjection>> getRegionsWithFilter(RegionCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Regions by criteria: {}", criteria);
        Page<RegionFilterProjection> page = regionQueryService.findByFilter(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/regionsWithFilter");
        return new ResponseEntity(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /regions/:id : get the "id" region.
     *
     * @param id the id of the region to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the region, or with status 404 (Not Found)
     */
    @GetMapping("/regions/{id}")
    @Timed
    public ResponseEntity<Region> getRegion(@PathVariable Long id) {
        log.debug("REST request to get Region : {}", id);
        Optional<Region> region = regionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(region);
    }

    /**
     * DELETE  /regions/:id : delete the "id" region.
     *
     * @param id the id of the region to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/regions/{id}")
    @Timed
    public ResponseEntity<Void> deleteRegion(@PathVariable Long id) {
        log.debug("REST request to delete Region : {}", id);
        regionService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
