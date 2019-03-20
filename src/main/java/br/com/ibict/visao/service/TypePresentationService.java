package br.com.ibict.visao.service;

import br.com.ibict.visao.domain.TypePresentation;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing TypePresentation.
 */
public interface TypePresentationService {

    /**
     * Save a typePresentation.
     *
     * @param typePresentation the entity to save
     * @return the persisted entity
     */
    TypePresentation save(TypePresentation typePresentation);

    /**
     * Get all the typePresentations.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<TypePresentation> findAll(Pageable pageable);


    /**
     * Get the "id" typePresentation.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<TypePresentation> findOne(Long id);

    /**
     * Delete the "id" typePresentation.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
