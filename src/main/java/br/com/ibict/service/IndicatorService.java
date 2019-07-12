package br.com.ibict.service;

import br.com.ibict.domain.Indicator;
import br.com.ibict.repository.IndicatorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;
/**
 * Service Implementation for managing Indicator.
 */
@Service
@Transactional
public class IndicatorService {

    private final Logger log = LoggerFactory.getLogger(IndicatorService.class);

    private final IndicatorRepository indicatorRepository;

    public IndicatorService(IndicatorRepository indicatorRepository) {
        this.indicatorRepository = indicatorRepository;
    }

    /**
     * Save a indicator.
     *
     * @param indicator the entity to save
     * @return the persisted entity
     */
    public Indicator save(Indicator indicator) {
        log.debug("Request to save Indicator : {}", indicator);        return indicatorRepository.save(indicator);
    }

    /**
     * Get all the indicators.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Indicator> findAll(Pageable pageable) {
        log.debug("Request to get all Indicators");
        return indicatorRepository.findAll(pageable);
    }


    /**
     * Get one indicator by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<Indicator> findOne(Long id) {
        log.debug("Request to get Indicator : {}", id);
        return indicatorRepository.findById(id);
    }

    /**
     * Delete the indicator by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Indicator : {}", id);
        indicatorRepository.deleteById(id);
    }
}
