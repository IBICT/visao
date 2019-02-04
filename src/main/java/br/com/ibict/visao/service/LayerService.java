package br.com.ibict.visao.service;

import br.com.ibict.visao.domain.Layer;
import br.com.ibict.visao.repository.LayerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;
/**
 * Service Implementation for managing Layer.
 */
@Service
@Transactional
public class LayerService {

    private final Logger log = LoggerFactory.getLogger(LayerService.class);

    private final LayerRepository layerRepository;

    public LayerService(LayerRepository layerRepository) {
        this.layerRepository = layerRepository;
    }

    /**
     * Save a layer.
     *
     * @param layer the entity to save
     * @return the persisted entity
     */
    public Layer save(Layer layer) {
        log.debug("Request to save Layer : {}", layer);        return layerRepository.save(layer);
    }

    /**
     * Get all the layers.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Layer> findAll(Pageable pageable) {
        log.debug("Request to get all Layers");
        return layerRepository.findAll(pageable);
    }


    /**
     * Get one layer by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<Layer> findOne(Long id) {
        log.debug("Request to get Layer : {}", id);
        return layerRepository.findById(id);
    }

    /**
     * Delete the layer by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Layer : {}", id);
        layerRepository.deleteById(id);
    }
}
