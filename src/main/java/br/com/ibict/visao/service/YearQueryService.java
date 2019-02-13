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

import br.com.ibict.visao.domain.Year;
import br.com.ibict.visao.domain.*; // for static metamodels
import br.com.ibict.visao.repository.YearRepository;
import br.com.ibict.visao.service.dto.YearCriteria;


/**
 * Service for executing complex queries for Year entities in the database.
 * The main input is a {@link YearCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Year} or a {@link Page} of {@link Year} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class YearQueryService extends QueryService<Year> {

    private final Logger log = LoggerFactory.getLogger(YearQueryService.class);

    private final YearRepository yearRepository;

    public YearQueryService(YearRepository yearRepository) {
        this.yearRepository = yearRepository;
    }

    /**
     * Return a {@link List} of {@link Year} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Year> findByCriteria(YearCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Year> specification = createSpecification(criteria);
        return yearRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Year} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Year> findByCriteria(YearCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Year> specification = createSpecification(criteria);
        return yearRepository.findAll(specification, page);
    }

    /**
     * Function to convert YearCriteria to a {@link Specification}
     */
    private Specification<Year> createSpecification(YearCriteria criteria) {
        Specification<Year> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Year_.id));
            }
            if (criteria.getDate() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDate(), Year_.date));
            }
        }
        return specification;
    }

}
