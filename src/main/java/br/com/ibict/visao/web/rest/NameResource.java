package br.com.ibict.visao.web.rest;

import com.codahale.metrics.annotation.Timed;
import br.com.ibict.visao.domain.Name;
import br.com.ibict.visao.service.NameService;
import br.com.ibict.visao.web.rest.errors.BadRequestAlertException;
import br.com.ibict.visao.web.rest.util.HeaderUtil;
import br.com.ibict.visao.web.rest.util.PaginationUtil;
import br.com.ibict.visao.service.dto.NameCriteria;
import br.com.ibict.visao.service.NameQueryService;
import io.github.jhipster.service.filter.BooleanFilter;
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
 * REST controller for managing Name.
 */
@RestController
@RequestMapping("/api")
public class NameResource {

    private final Logger log = LoggerFactory.getLogger(NameResource.class);

    private static final String ENTITY_NAME = "name";

    private final NameService nameService;

    private final NameQueryService nameQueryService;

    public NameResource(NameService nameService, NameQueryService nameQueryService) {
        this.nameService = nameService;
        this.nameQueryService = nameQueryService;
    }

    /**
     * POST  /names : Create a new name.
     *
     * @param name the name to create
     * @return the ResponseEntity with status 201 (Created) and with body the new name, or with status 400 (Bad Request) if the name has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/names")
    @Timed
    public ResponseEntity<Name> createName(@Valid @RequestBody Name name) throws URISyntaxException {
        log.debug("REST request to save Name : {}", name);
        if (name.getId() != null) {
            throw new BadRequestAlertException("A new name cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Name result = nameService.save(name);
        return ResponseEntity.created(new URI("/api/names/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /names : Updates an existing name.
     *
     * @param name the name to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated name,
     * or with status 400 (Bad Request) if the name is not valid,
     * or with status 500 (Internal Server Error) if the name couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/names")
    @Timed
    public ResponseEntity<Name> updateName(@Valid @RequestBody Name name) throws URISyntaxException {
        log.debug("REST request to update Name : {}", name);
        if (name.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Name result = nameService.save(name);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, name.getId().toString()))
            .body(result);
    }

    /**
     * GET  /names : get all the names.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of names in body
     */
    @GetMapping("/names")
    @Timed
    public ResponseEntity<List<Name>> getAllNames(NameCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Names by criteria: {}", criteria);
        Page<Name> page = nameQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/names");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /names : get all the names.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of names in body
     */
    @GetMapping("/namesPublic")
    @Timed
    public ResponseEntity<List<Name>> getAllNamesPublic(NameCriteria criteria, Pageable pageable) {
        criteria.setActive(getBooleanFilterTrue());
        log.debug("REST request to get Names by criteria: {}", criteria);
        Page<Name> page = nameQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/names");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /names/:id : get the "id" name.
     *
     * @param id the id of the name to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the name, or with status 404 (Not Found)
     */
    @GetMapping("/namesPublic/{id}")
    @Timed
    public ResponseEntity<Name> getNamePubic(@PathVariable Long id) {
        log.debug("REST request to get Name : {}", id);
        Optional<Name> name = nameService.findOne(id);
        return ResponseUtil.wrapOrNotFound(name);
    }

    private BooleanFilter getBooleanFilterTrue(){
        BooleanFilter booleanFilter = new BooleanFilter();
        booleanFilter.setEquals(Boolean.TRUE);
        return booleanFilter;
    }

    /**
     * GET  /names/:id : get the "id" name.
     *
     * @param id the id of the name to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the name, or with status 404 (Not Found)
     */
    @GetMapping("/names/{id}")
    @Timed
    public ResponseEntity<Name> getName(@PathVariable Long id) {
        log.debug("REST request to get Name : {}", id);
        Optional<Name> name = nameService.findOne(id);
        return ResponseUtil.wrapOrNotFound(name);
    }

    /**
     * DELETE  /names/:id : delete the "id" name.
     *
     * @param id the id of the name to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/names/{id}")
    @Timed
    public ResponseEntity<Void> deleteName(@PathVariable Long id) {
        log.debug("REST request to delete Name : {}", id);
        nameService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
