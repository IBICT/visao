package br.com.ibict.visao.service;

import br.com.ibict.visao.domain.GrupCategory;
import br.com.ibict.visao.repository.GrupCategoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;
/**
 * Service Implementation for managing GrupCategory.
 */
@Service
@Transactional
public class GrupCategoryService {

    private final Logger log = LoggerFactory.getLogger(GrupCategoryService.class);

    private final GrupCategoryRepository grupCategoryRepository;

    public GrupCategoryService(GrupCategoryRepository grupCategoryRepository) {
        this.grupCategoryRepository = grupCategoryRepository;
    }

    /**
     * Save a grupCategory.
     *
     * @param grupCategory the entity to save
     * @return the persisted entity
     */
    public GrupCategory save(GrupCategory grupCategory) {
        log.debug("Request to save GrupCategory : {}", grupCategory);        return grupCategoryRepository.save(grupCategory);
    }

    /**
     * Get all the grupCategories.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<GrupCategory> findAll(Pageable pageable) {
        log.debug("Request to get all GrupCategories");
        return grupCategoryRepository.findAll(pageable);
    }

    /**
     * Get all the GrupCategory with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    public Page<GrupCategory> findAllWithEagerRelationships(Pageable pageable) {
        return grupCategoryRepository.findAllWithEagerRelationships(pageable);
    }
    

    /**
     * Get one grupCategory by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<GrupCategory> findOne(Long id) {
        log.debug("Request to get GrupCategory : {}", id);
        return grupCategoryRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the grupCategory by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete GrupCategory : {}", id);
        grupCategoryRepository.deleteById(id);
    }
}
