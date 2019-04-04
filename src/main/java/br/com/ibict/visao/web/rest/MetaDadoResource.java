package br.com.ibict.visao.web.rest;

import com.codahale.metrics.annotation.Timed;
import br.com.ibict.visao.domain.MetaDado;
import br.com.ibict.visao.service.MetaDadoService;
import br.com.ibict.visao.web.rest.errors.BadRequestAlertException;
import br.com.ibict.visao.web.rest.util.HeaderUtil;
import br.com.ibict.visao.web.rest.util.PaginationUtil;
import br.com.ibict.visao.service.dto.MetaDadoCriteria;
import br.com.ibict.visao.service.MetaDadoQueryService;
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
 * REST controller for managing MetaDado.
 */
@RestController
@RequestMapping("/api")
public class MetaDadoResource {

    private final Logger log = LoggerFactory.getLogger(MetaDadoResource.class);

    private static final String ENTITY_NAME = "metaDado";

    private final MetaDadoService metaDadoService;

    private final MetaDadoQueryService metaDadoQueryService;

    public MetaDadoResource(MetaDadoService metaDadoService, MetaDadoQueryService metaDadoQueryService) {
        this.metaDadoService = metaDadoService;
        this.metaDadoQueryService = metaDadoQueryService;
    }

    /**
     * POST  /meta-dados : Create a new metaDado.
     *
     * @param metaDado the metaDado to create
     * @return the ResponseEntity with status 201 (Created) and with body the new metaDado, or with status 400 (Bad Request) if the metaDado has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/meta-dados")
    @Timed
    public ResponseEntity<MetaDado> createMetaDado(@Valid @RequestBody MetaDado metaDado) throws URISyntaxException {
        log.debug("REST request to save MetaDado : {}", metaDado);
        if (metaDado.getId() != null) {
            throw new BadRequestAlertException("A new metaDado cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MetaDado result = metaDadoService.save(metaDado);
        return ResponseEntity.created(new URI("/api/meta-dados/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /meta-dados : Updates an existing metaDado.
     *
     * @param metaDado the metaDado to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated metaDado,
     * or with status 400 (Bad Request) if the metaDado is not valid,
     * or with status 500 (Internal Server Error) if the metaDado couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/meta-dados")
    @Timed
    public ResponseEntity<MetaDado> updateMetaDado(@Valid @RequestBody MetaDado metaDado) throws URISyntaxException {
        log.debug("REST request to update MetaDado : {}", metaDado);
        if (metaDado.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MetaDado result = metaDadoService.save(metaDado);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, metaDado.getId().toString()))
            .body(result);
    }

    /**
     * GET  /meta-dados : get all the metaDados.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of metaDados in body
     */
    @GetMapping("/meta-dados")
    @Timed
    public ResponseEntity<List<MetaDado>> getAllMetaDados(MetaDadoCriteria criteria, Pageable pageable) {
        log.debug("REST request to get MetaDados by criteria: {}", criteria);
        Page<MetaDado> page = metaDadoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/meta-dados");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /meta-dados/:id : get the "id" metaDado.
     *
     * @param id the id of the metaDado to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the metaDado, or with status 404 (Not Found)
     */
    @GetMapping("/meta-dados/{id}")
    @Timed
    public ResponseEntity<MetaDado> getMetaDado(@PathVariable Long id) {
        log.debug("REST request to get MetaDado : {}", id);
        Optional<MetaDado> metaDado = metaDadoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(metaDado);
    }

    /**
     * DELETE  /meta-dados/:id : delete the "id" metaDado.
     *
     * @param id the id of the metaDado to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/meta-dados/{id}")
    @Timed
    public ResponseEntity<Void> deleteMetaDado(@PathVariable Long id) {
        log.debug("REST request to delete MetaDado : {}", id);
        metaDadoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
