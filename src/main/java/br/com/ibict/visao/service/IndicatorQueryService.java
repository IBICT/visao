package br.com.ibict.visao.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import br.com.ibict.visao.domain.Indicator;
import br.com.ibict.visao.domain.*; // for static metamodels
import br.com.ibict.visao.repository.IndicatorRepository;
import br.com.ibict.visao.service.dto.IndicatorCriteria;


/**
 * Service for executing complex queries for Indicator entities in the database.
 * The main input is a {@link IndicatorCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Indicator} or a {@link Page} of {@link Indicator} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class IndicatorQueryService extends QueryService<Indicator> {

    private final Logger log = LoggerFactory.getLogger(IndicatorQueryService.class);

    private final IndicatorRepository indicatorRepository;

    public IndicatorQueryService(IndicatorRepository indicatorRepository) {
        this.indicatorRepository = indicatorRepository;
    }

    /**
     * Return a {@link List} of {@link Indicator} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Indicator> findByCriteria(IndicatorCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Indicator> specification = createSpecification(criteria);
        return indicatorRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Indicator} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Indicator> findByCriteria(IndicatorCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Indicator> specification = createSpecification(criteria);
        return indicatorRepository.findAll(specification, page);
    }

    /**
     * Return a {@link Page} of {@link Indicator} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Indicator> findIndicatorWithFilters(IndicatorCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);

        return indicatorRepository.getIndicadorFilter(
                    criteria.getNameId().getEquals(),
                    criteria.getAnoId().getEquals(),
                    criteria.getFiltersId().getIn(),
            page);
    }

    /**
     * Function to convert IndicatorCriteria to a {@link Specification}
     */
    private Specification<Indicator> createSpecification(IndicatorCriteria criteria) {
        Specification<Indicator> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Indicator_.id));
            }
            if (criteria.getValue() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValue(), Indicator_.value));
            }
            if (criteria.getRegionId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getRegionId(), Indicator_.region, Region_.id));
            }
            if (criteria.getNameId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getNameId(), Indicator_.name, Name_.id));
            }
            if (criteria.getAnoId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getAnoId(), Indicator_.ano, Year_.id));
            }
        }
        return specification;
    }

}
