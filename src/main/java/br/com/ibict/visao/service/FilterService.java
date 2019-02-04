package br.com.ibict.visao.service;

import br.com.ibict.visao.domain.Filter;
import br.com.ibict.visao.repository.FilterRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;
/**
 * Service Implementation for managing Filter.
 */
@Service
@Transactional
public class FilterService {

    private final Logger log = LoggerFactory.getLogger(FilterService.class);

    private final FilterRepository filterRepository;

    public FilterService(FilterRepository filterRepository) {
        this.filterRepository = filterRepository;
    }

    /**
     * Save a filter.
     *
     * @param filter the entity to save
     * @return the persisted entity
     */
    public Filter save(Filter filter) {
        log.debug("Request to save Filter : {}", filter);        return filterRepository.save(filter);
    }

    /**
     * Get all the filters.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Filter> findAll(Pageable pageable) {
        log.debug("Request to get all Filters");
        return filterRepository.findAll(pageable);
    }

    /**
     * Get all the Filter with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    public Page<Filter> findAllWithEagerRelationships(Pageable pageable) {
        return filterRepository.findAllWithEagerRelationships(pageable);
    }
    

    /**
     * Get one filter by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<Filter> findOne(Long id) {
        log.debug("Request to get Filter : {}", id);
        return filterRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the filter by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Filter : {}", id);
        filterRepository.deleteById(id);
    }
}
