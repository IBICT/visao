package br.com.ibict.visao.service;

import br.com.ibict.visao.domain.MetaDado;
import br.com.ibict.visao.repository.MetaDadoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;
/**
 * Service Implementation for managing MetaDado.
 */
@Service
@Transactional
public class MetaDadoService {

    private final Logger log = LoggerFactory.getLogger(MetaDadoService.class);

    private final MetaDadoRepository metaDadoRepository;

    public MetaDadoService(MetaDadoRepository metaDadoRepository) {
        this.metaDadoRepository = metaDadoRepository;
    }

    /**
     * Save a metaDado.
     *
     * @param metaDado the entity to save
     * @return the persisted entity
     */
    public MetaDado save(MetaDado metaDado) {
        log.debug("Request to save MetaDado : {}", metaDado);        return metaDadoRepository.save(metaDado);
    }

    /**
     * Get all the metaDados.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<MetaDado> findAll(Pageable pageable) {
        log.debug("Request to get all MetaDados");
        return metaDadoRepository.findAll(pageable);
    }

    /**
     * Get all the MetaDado with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    public Page<MetaDado> findAllWithEagerRelationships(Pageable pageable) {
        return metaDadoRepository.findAllWithEagerRelationships(pageable);
    }
    

    /**
     * Get one metaDado by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<MetaDado> findOne(Long id) {
        log.debug("Request to get MetaDado : {}", id);
        return metaDadoRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the metaDado by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete MetaDado : {}", id);
        metaDadoRepository.deleteById(id);
    }
}
