package br.com.ibict.visao.service;

import br.com.ibict.visao.domain.Chart;
import br.com.ibict.visao.repository.ChartRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;
/**
 * Service Implementation for managing Chart.
 */
@Service
@Transactional
public class ChartService {

    private final Logger log = LoggerFactory.getLogger(ChartService.class);

    private final ChartRepository chartRepository;

    public ChartService(ChartRepository chartRepository) {
        this.chartRepository = chartRepository;
    }

    /**
     * Save a chart.
     *
     * @param chart the entity to save
     * @return the persisted entity
     */
    public Chart save(Chart chart) {
        log.debug("Request to save Chart : {}", chart);        return chartRepository.save(chart);
    }

    /**
     * Get all the charts.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Chart> findAll(Pageable pageable) {
        log.debug("Request to get all Charts");
        return chartRepository.findAll(pageable);
    }

    /**
     * Get all the Chart with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    public Page<Chart> findAllWithEagerRelationships(Pageable pageable) {
        return chartRepository.findAllWithEagerRelationships(pageable);
    }
    

    /**
     * Get one chart by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<Chart> findOne(Long id) {
        log.debug("Request to get Chart : {}", id);
        return chartRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the chart by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Chart : {}", id);
        chartRepository.deleteById(id);
    }
}
