package br.com.ibict.visao.service;

import br.com.ibict.visao.domain.Name;
import br.com.ibict.visao.repository.NameRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;
/**
 * Service Implementation for managing Name.
 */
@Service
@Transactional
public class NameService {

    private final Logger log = LoggerFactory.getLogger(NameService.class);

    private final NameRepository nameRepository;

    public NameService(NameRepository nameRepository) {
        this.nameRepository = nameRepository;
    }

    /**
     * Save a name.
     *
     * @param name the entity to save
     * @return the persisted entity
     */
    public Name save(Name name) {
        log.debug("Request to save Name : {}", name);        return nameRepository.save(name);
    }

    /**
     * Get all the names.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Name> findAll(Pageable pageable) {
        log.debug("Request to get all Names");
        return nameRepository.findAll(pageable);
    }


    /**
     * Get one name by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<Name> findOne(Long id) {
        log.debug("Request to get Name : {}", id);
        return nameRepository.findById(id);
    }

    /**
     * Delete the name by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Name : {}", id);
        nameRepository.deleteById(id);
    }
}
