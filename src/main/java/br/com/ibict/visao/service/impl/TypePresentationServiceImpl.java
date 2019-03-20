package br.com.ibict.visao.service.impl;

import br.com.ibict.visao.service.TypePresentationService;
import br.com.ibict.visao.domain.TypePresentation;
import br.com.ibict.visao.repository.TypePresentationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;
/**
 * Service Implementation for managing TypePresentation.
 */
@Service
@Transactional
public class TypePresentationServiceImpl implements TypePresentationService {

    private final Logger log = LoggerFactory.getLogger(TypePresentationServiceImpl.class);

    private final TypePresentationRepository typePresentationRepository;

    public TypePresentationServiceImpl(TypePresentationRepository typePresentationRepository) {
        this.typePresentationRepository = typePresentationRepository;
    }

    /**
     * Save a typePresentation.
     *
     * @param typePresentation the entity to save
     * @return the persisted entity
     */
    @Override
    public TypePresentation save(TypePresentation typePresentation) {
        log.debug("Request to save TypePresentation : {}", typePresentation);        return typePresentationRepository.save(typePresentation);
    }

    /**
     * Get all the typePresentations.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TypePresentation> findAll(Pageable pageable) {
        log.debug("Request to get all TypePresentations");
        return typePresentationRepository.findAll(pageable);
    }


    /**
     * Get one typePresentation by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TypePresentation> findOne(Long id) {
        log.debug("Request to get TypePresentation : {}", id);
        return typePresentationRepository.findById(id);
    }

    /**
     * Delete the typePresentation by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TypePresentation : {}", id);
        typePresentationRepository.deleteById(id);
    }
}
