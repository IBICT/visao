package br.com.ibict.service;

import br.com.ibict.domain.GeographicFilter;
import br.com.ibict.repository.GeographicFilterRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;
/**
 * Service Implementation for managing GeographicFilter.
 */
@Service
@Transactional
public class GeographicFilterService {

    private final Logger log = LoggerFactory.getLogger(GeographicFilterService.class);

    private final GeographicFilterRepository geographicFilterRepository;

    public GeographicFilterService(GeographicFilterRepository geographicFilterRepository) {
        this.geographicFilterRepository = geographicFilterRepository;
    }

    /**
     * Save a geographicFilter.
     *
     * @param geographicFilter the entity to save
     * @return the persisted entity
     */
    public GeographicFilter save(GeographicFilter geographicFilter) {
        log.debug("Request to save GeographicFilter : {}", geographicFilter);        return geographicFilterRepository.save(geographicFilter);
    }

    /**
     * Get all the geographicFilters.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<GeographicFilter> findAll(Pageable pageable) {
        log.debug("Request to get all GeographicFilters");
        return geographicFilterRepository.findAll(pageable);
    }

    /**
     * Get all the GeographicFilter with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    public Page<GeographicFilter> findAllWithEagerRelationships(Pageable pageable) {
        return geographicFilterRepository.findAllWithEagerRelationships(pageable);
    }
    

    /**
     * Get one geographicFilter by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<GeographicFilter> findOne(Long id) {
        log.debug("Request to get GeographicFilter : {}", id);
        return geographicFilterRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the geographicFilter by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete GeographicFilter : {}", id);
        geographicFilterRepository.deleteById(id);
    }
}
