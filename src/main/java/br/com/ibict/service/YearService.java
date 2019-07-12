package br.com.ibict.service;

import br.com.ibict.domain.Year;
import br.com.ibict.repository.YearRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;
/**
 * Service Implementation for managing Year.
 */
@Service
@Transactional
public class YearService {

    private final Logger log = LoggerFactory.getLogger(YearService.class);

    private final YearRepository yearRepository;

    public YearService(YearRepository yearRepository) {
        this.yearRepository = yearRepository;
    }

    /**
     * Save a year.
     *
     * @param year the entity to save
     * @return the persisted entity
     */
    public Year save(Year year) {
        log.debug("Request to save Year : {}", year);        return yearRepository.save(year);
    }

    /**
     * Get all the years.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Year> findAll(Pageable pageable) {
        log.debug("Request to get all Years");
        return yearRepository.findAll(pageable);
    }


    /**
     * Get one year by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<Year> findOne(Long id) {
        log.debug("Request to get Year : {}", id);
        return yearRepository.findById(id);
    }

    /**
     * Delete the year by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Year : {}", id);
        yearRepository.deleteById(id);
    }
}
