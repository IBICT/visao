package br.com.ibict.service;

import br.com.ibict.domain.MarkerIcon;
import br.com.ibict.repository.MarkerIconRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;
/**
 * Service Implementation for managing MarkerIcon.
 */
@Service
@Transactional
public class MarkerIconService {

    private final Logger log = LoggerFactory.getLogger(MarkerIconService.class);

    private final MarkerIconRepository markerIconRepository;

    public MarkerIconService(MarkerIconRepository markerIconRepository) {
        this.markerIconRepository = markerIconRepository;
    }

    /**
     * Save a markerIcon.
     *
     * @param markerIcon the entity to save
     * @return the persisted entity
     */
    public MarkerIcon save(MarkerIcon markerIcon) {
        log.debug("Request to save MarkerIcon : {}", markerIcon);        return markerIconRepository.save(markerIcon);
    }

    /**
     * Get all the markerIcons.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<MarkerIcon> findAll(Pageable pageable) {
        log.debug("Request to get all MarkerIcons");
        return markerIconRepository.findAll(pageable);
    }


    /**
     * Get one markerIcon by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<MarkerIcon> findOne(Long id) {
        log.debug("Request to get MarkerIcon : {}", id);
        return markerIconRepository.findById(id);
    }

    /**
     * Delete the markerIcon by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete MarkerIcon : {}", id);
        markerIconRepository.deleteById(id);
    }
}
