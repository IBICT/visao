package br.com.ibict.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import br.com.ibict.domain.Chart;
import br.com.ibict.domain.*; // for static metamodels
import br.com.ibict.repository.ChartRepository;
import br.com.ibict.service.dto.ChartCriteria;


/**
 * Service for executing complex queries for Chart entities in the database.
 * The main input is a {@link ChartCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Chart} or a {@link Page} of {@link Chart} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ChartQueryService extends QueryService<Chart> {

    private final Logger log = LoggerFactory.getLogger(ChartQueryService.class);

    private final ChartRepository chartRepository;

    public ChartQueryService(ChartRepository chartRepository) {
        this.chartRepository = chartRepository;
    }

    /**
     * Return a {@link List} of {@link Chart} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Chart> findByCriteria(ChartCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Chart> specification = createSpecification(criteria);
        return chartRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Chart} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Chart> findByCriteria(ChartCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Chart> specification = createSpecification(criteria);
        return chartRepository.findAll(specification, page);
    }

    /**
     * Function to convert ChartCriteria to a {@link Specification}
     */
    private Specification<Chart> createSpecification(ChartCriteria criteria) {
        Specification<Chart> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Chart_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Chart_.name));
            }
            if (criteria.getExternal() != null) {
                specification = specification.and(buildSpecification(criteria.getExternal(), Chart_.external));
            }
            if (criteria.getPermission() != null) {
                specification = specification.and(buildSpecification(criteria.getPermission(), Chart_.permission));
            }
            if (criteria.getOwnerId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getOwnerId(), Chart_.owner, User_.id));
            }
            if (criteria.getSharedId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getSharedId(), Chart_.shareds, User_.id));
            }
        }
        return specification;
    }

}
