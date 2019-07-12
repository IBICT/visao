package br.com.ibict.service;

import br.com.ibict.domain.GrupIndicator;
import br.com.ibict.repository.GrupIndicatorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;
/**
 * Service Implementation for managing GrupIndicator.
 */
@Service
@Transactional
public class GrupIndicatorService {

    private final Logger log = LoggerFactory.getLogger(GrupIndicatorService.class);

    private final GrupIndicatorRepository grupIndicatorRepository;

    public GrupIndicatorService(GrupIndicatorRepository grupIndicatorRepository) {
        this.grupIndicatorRepository = grupIndicatorRepository;
    }

    /**
     * Save a grupIndicator.
     *
     * @param grupIndicator the entity to save
     * @return the persisted entity
     */
    public GrupIndicator save(GrupIndicator grupIndicator) {
        log.debug("Request to save GrupIndicator : {}", grupIndicator);        return grupIndicatorRepository.save(grupIndicator);
    }

    /**
     * Get all the grupIndicators.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<GrupIndicator> findAll(Pageable pageable) {
        log.debug("Request to get all GrupIndicators");
        return grupIndicatorRepository.findAll(pageable);
    }

    /**
     * Get all the GrupIndicator with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    public Page<GrupIndicator> findAllWithEagerRelationships(Pageable pageable) {
        return grupIndicatorRepository.findAllWithEagerRelationships(pageable);
    }
    

    /**
     * Get one grupIndicator by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<GrupIndicator> findOne(Long id) {
        log.debug("Request to get GrupIndicator : {}", id);
        return grupIndicatorRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the grupIndicator by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete GrupIndicator : {}", id);
        grupIndicatorRepository.deleteById(id);
    }
}
