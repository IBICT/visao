package br.com.ibict.visao.web.rest;

import com.codahale.metrics.annotation.Timed;
import br.com.ibict.visao.domain.TypePresentation;
import br.com.ibict.visao.security.AuthoritiesConstants;
import br.com.ibict.visao.service.TypePresentationService;
import br.com.ibict.visao.web.rest.errors.BadRequestAlertException;
import br.com.ibict.visao.web.rest.util.HeaderUtil;
import br.com.ibict.visao.web.rest.util.PaginationUtil;
import br.com.ibict.visao.service.dto.TypePresentationCriteria;
import br.com.ibict.visao.service.TypePresentationQueryService;
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

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing TypePresentation.
 */
@RestController
@RequestMapping("/api")
public class TypePresentationResource {

    private final Logger log = LoggerFactory.getLogger(TypePresentationResource.class);

    private static final String ENTITY_NAME = "typePresentation";

    private final TypePresentationService typePresentationService;

    private final TypePresentationQueryService typePresentationQueryService;

    public TypePresentationResource(TypePresentationService typePresentationService, TypePresentationQueryService typePresentationQueryService) {
        this.typePresentationService = typePresentationService;
        this.typePresentationQueryService = typePresentationQueryService;
    }

    /**
     * POST  /type-presentations : Create a new typePresentation.
     *
     * @param typePresentation the typePresentation to create
     * @return the ResponseEntity with status 201 (Created) and with body the new typePresentation, or with status 400 (Bad Request) if the typePresentation has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/type-presentations")
    @Timed
    public ResponseEntity<TypePresentation> createTypePresentation(@RequestBody TypePresentation typePresentation) throws URISyntaxException {
        log.debug("REST request to save TypePresentation : {}", typePresentation);
        if (typePresentation.getId() != null) {
            throw new BadRequestAlertException("A new typePresentation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TypePresentation result = typePresentationService.save(typePresentation);
        return ResponseEntity.created(new URI("/api/type-presentations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /type-presentations : Updates an existing typePresentation.
     *
     * @param typePresentation the typePresentation to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated typePresentation,
     * or with status 400 (Bad Request) if the typePresentation is not valid,
     * or with status 500 (Internal Server Error) if the typePresentation couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/type-presentations")
    @Timed
    @Secured(AuthoritiesConstants.ADMIN)
    public ResponseEntity<TypePresentation> updateTypePresentation(@RequestBody TypePresentation typePresentation) throws URISyntaxException {
        log.debug("REST request to update TypePresentation : {}", typePresentation);
        if (typePresentation.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TypePresentation result = typePresentationService.save(typePresentation);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, typePresentation.getId().toString()))
            .body(result);
    }

    /**
     * GET  /type-presentations : get all the typePresentations.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of typePresentations in body
     */
    @GetMapping("/type-presentations")
    @Timed
    public ResponseEntity<List<TypePresentation>> getAllTypePresentations(TypePresentationCriteria criteria, Pageable pageable) {
        log.debug("REST request to get TypePresentations by criteria: {}", criteria);
        Page<TypePresentation> page = typePresentationQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/type-presentations");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /type-presentations/:id : get the "id" typePresentation.
     *
     * @param id the id of the typePresentation to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the typePresentation, or with status 404 (Not Found)
     */
    @GetMapping("/type-presentations/{id}")
    @Timed
    public ResponseEntity<TypePresentation> getTypePresentation(@PathVariable Long id) {
        log.debug("REST request to get TypePresentation : {}", id);
        Optional<TypePresentation> typePresentation = typePresentationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(typePresentation);
    }

    /**
     * DELETE  /type-presentations/:id : delete the "id" typePresentation.
     *
     * @param id the id of the typePresentation to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/type-presentations/{id}")
    @Timed
    @Secured(AuthoritiesConstants.ADMIN)
    public ResponseEntity<Void> deleteTypePresentation(@PathVariable Long id) {
        log.debug("REST request to delete TypePresentation : {}", id);
        typePresentationService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
