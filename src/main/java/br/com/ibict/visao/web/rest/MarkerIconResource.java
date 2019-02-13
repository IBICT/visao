package br.com.ibict.visao.web.rest;

import com.codahale.metrics.annotation.Timed;
import br.com.ibict.visao.domain.MarkerIcon;
import br.com.ibict.visao.service.MarkerIconService;
import br.com.ibict.visao.web.rest.errors.BadRequestAlertException;
import br.com.ibict.visao.web.rest.util.HeaderUtil;
import br.com.ibict.visao.web.rest.util.PaginationUtil;
import br.com.ibict.visao.service.dto.MarkerIconCriteria;
import br.com.ibict.visao.service.MarkerIconQueryService;
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
 * REST controller for managing MarkerIcon.
 */
@RestController
@RequestMapping("/api")
public class MarkerIconResource {

    private final Logger log = LoggerFactory.getLogger(MarkerIconResource.class);

    private static final String ENTITY_NAME = "markerIcon";

    private final MarkerIconService markerIconService;

    private final MarkerIconQueryService markerIconQueryService;

    public MarkerIconResource(MarkerIconService markerIconService, MarkerIconQueryService markerIconQueryService) {
        this.markerIconService = markerIconService;
        this.markerIconQueryService = markerIconQueryService;
    }

    /**
     * POST  /marker-icons : Create a new markerIcon.
     *
     * @param markerIcon the markerIcon to create
     * @return the ResponseEntity with status 201 (Created) and with body the new markerIcon, or with status 400 (Bad Request) if the markerIcon has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/marker-icons")
    @Timed
    public ResponseEntity<MarkerIcon> createMarkerIcon(@Valid @RequestBody MarkerIcon markerIcon) throws URISyntaxException {
        log.debug("REST request to save MarkerIcon : {}", markerIcon);
        if (markerIcon.getId() != null) {
            throw new BadRequestAlertException("A new markerIcon cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MarkerIcon result = markerIconService.save(markerIcon);
        return ResponseEntity.created(new URI("/api/marker-icons/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /marker-icons : Updates an existing markerIcon.
     *
     * @param markerIcon the markerIcon to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated markerIcon,
     * or with status 400 (Bad Request) if the markerIcon is not valid,
     * or with status 500 (Internal Server Error) if the markerIcon couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/marker-icons")
    @Timed
    public ResponseEntity<MarkerIcon> updateMarkerIcon(@Valid @RequestBody MarkerIcon markerIcon) throws URISyntaxException {
        log.debug("REST request to update MarkerIcon : {}", markerIcon);
        if (markerIcon.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MarkerIcon result = markerIconService.save(markerIcon);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, markerIcon.getId().toString()))
            .body(result);
    }

    /**
     * GET  /marker-icons : get all the markerIcons.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of markerIcons in body
     */
    @GetMapping("/marker-icons")
    @Timed
    public ResponseEntity<List<MarkerIcon>> getAllMarkerIcons(MarkerIconCriteria criteria, Pageable pageable) {
        log.debug("REST request to get MarkerIcons by criteria: {}", criteria);
        Page<MarkerIcon> page = markerIconQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/marker-icons");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /marker-icons/:id : get the "id" markerIcon.
     *
     * @param id the id of the markerIcon to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the markerIcon, or with status 404 (Not Found)
     */
    @GetMapping("/marker-icons/{id}")
    @Timed
    public ResponseEntity<MarkerIcon> getMarkerIcon(@PathVariable Long id) {
        log.debug("REST request to get MarkerIcon : {}", id);
        Optional<MarkerIcon> markerIcon = markerIconService.findOne(id);
        return ResponseUtil.wrapOrNotFound(markerIcon);
    }

    /**
     * DELETE  /marker-icons/:id : delete the "id" markerIcon.
     *
     * @param id the id of the markerIcon to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/marker-icons/{id}")
    @Timed
    public ResponseEntity<Void> deleteMarkerIcon(@PathVariable Long id) {
        log.debug("REST request to delete MarkerIcon : {}", id);
        markerIconService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
