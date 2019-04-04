package br.com.ibict.visao.web.rest;

import com.codahale.metrics.annotation.Timed;
import br.com.ibict.visao.domain.GroupLayer;
import br.com.ibict.visao.security.AuthoritiesConstants;
import br.com.ibict.visao.service.GroupLayerService;
import br.com.ibict.visao.web.rest.errors.BadRequestAlertException;
import br.com.ibict.visao.web.rest.util.HeaderUtil;
import br.com.ibict.visao.web.rest.util.PaginationUtil;
import br.com.ibict.visao.service.dto.GroupLayerCriteria;
import br.com.ibict.visao.service.GroupLayerQueryService;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing GroupLayer.
 */
@RestController
@RequestMapping("/api")
public class GroupLayerResource {

    private final Logger log = LoggerFactory.getLogger(GroupLayerResource.class);

    private static final String ENTITY_NAME = "groupLayer";

    private final GroupLayerService groupLayerService;

    private final GroupLayerQueryService groupLayerQueryService;

    public GroupLayerResource(GroupLayerService groupLayerService, GroupLayerQueryService groupLayerQueryService) {
        this.groupLayerService = groupLayerService;
        this.groupLayerQueryService = groupLayerQueryService;
    }

    /**
     * POST  /group-layers : Create a new groupLayer.
     *
     * @param groupLayer the groupLayer to create
     * @return the ResponseEntity with status 201 (Created) and with body the new groupLayer, or with status 400 (Bad Request) if the groupLayer has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/group-layers")
    @Timed
    public ResponseEntity<GroupLayer> createGroupLayer(@Valid @RequestBody GroupLayer groupLayer) throws URISyntaxException {
        log.debug("REST request to save GroupLayer : {}", groupLayer);
        if (groupLayer.getId() != null) {
            throw new BadRequestAlertException("A new groupLayer cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GroupLayer result = groupLayerService.save(groupLayer);
        return ResponseEntity.created(new URI("/api/group-layers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /group-layers : Updates an existing groupLayer.
     *
     * @param groupLayer the groupLayer to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated groupLayer,
     * or with status 400 (Bad Request) if the groupLayer is not valid,
     * or with status 500 (Internal Server Error) if the groupLayer couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/group-layers")
    @Timed
    @Secured(AuthoritiesConstants.ADMIN)
    public ResponseEntity<GroupLayer> updateGroupLayer(@Valid @RequestBody GroupLayer groupLayer) throws URISyntaxException {
        log.debug("REST request to update GroupLayer : {}", groupLayer);
        if (groupLayer.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        GroupLayer result = groupLayerService.save(groupLayer);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, groupLayer.getId().toString()))
            .body(result);
    }

    /**
     * GET  /group-layers : get all the groupLayers.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of groupLayers in body
     */
    @GetMapping("/group-layers")
    @Timed
    public ResponseEntity<List<GroupLayer>> getAllGroupLayers(GroupLayerCriteria criteria, Pageable pageable) {
        log.debug("REST request to get GroupLayers by criteria: {}", criteria);
        Page<GroupLayer> page = groupLayerQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/group-layers");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /group-layers/:id : get the "id" groupLayer.
     *
     * @param id the id of the groupLayer to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the groupLayer, or with status 404 (Not Found)
     */
    @GetMapping("/group-layers/{id}")
    @Timed
    public ResponseEntity<GroupLayer> getGroupLayer(@PathVariable Long id) {
        log.debug("REST request to get GroupLayer : {}", id);
        Optional<GroupLayer> groupLayer = groupLayerService.findOne(id);
        return ResponseUtil.wrapOrNotFound(groupLayer);
    }

    /**
     * DELETE  /group-layers/:id : delete the "id" groupLayer.
     *
     * @param id the id of the groupLayer to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/group-layers/{id}")
    @Timed
    @Secured(AuthoritiesConstants.ADMIN)
    public ResponseEntity<Void> deleteGroupLayer(@PathVariable Long id) {
        log.debug("REST request to delete GroupLayer : {}", id);
        groupLayerService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
