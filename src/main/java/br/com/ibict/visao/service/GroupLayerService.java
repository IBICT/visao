package br.com.ibict.visao.service;

import br.com.ibict.visao.domain.GroupLayer;
import br.com.ibict.visao.repository.GroupLayerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;
/**
 * Service Implementation for managing GroupLayer.
 */
@Service
@Transactional
public class GroupLayerService {

    private final Logger log = LoggerFactory.getLogger(GroupLayerService.class);

    private final GroupLayerRepository groupLayerRepository;

    public GroupLayerService(GroupLayerRepository groupLayerRepository) {
        this.groupLayerRepository = groupLayerRepository;
    }

    /**
     * Save a groupLayer.
     *
     * @param groupLayer the entity to save
     * @return the persisted entity
     */
    public GroupLayer save(GroupLayer groupLayer) {
        log.debug("Request to save GroupLayer : {}", groupLayer);        return groupLayerRepository.save(groupLayer);
    }

    /**
     * Get all the groupLayers.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<GroupLayer> findAll(Pageable pageable) {
        log.debug("Request to get all GroupLayers");
        return groupLayerRepository.findAll(pageable);
    }


    /**
     * Get one groupLayer by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<GroupLayer> findOne(Long id) {
        log.debug("Request to get GroupLayer : {}", id);
        return groupLayerRepository.findById(id);
    }

    /**
     * Delete the groupLayer by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete GroupLayer : {}", id);
        groupLayerRepository.deleteById(id);
    }
}
