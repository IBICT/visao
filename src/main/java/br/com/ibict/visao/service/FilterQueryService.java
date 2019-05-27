package br.com.ibict.visao.service;

import java.util.List;

import br.com.ibict.visao.dto.FilterInfoProjection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import br.com.ibict.visao.domain.Filter;
import br.com.ibict.visao.domain.*; // for static metamodels
import br.com.ibict.visao.repository.FilterRepository;
import br.com.ibict.visao.service.dto.FilterCriteria;


/**
 * Service for executing complex queries for Filter entities in the database.
 * The main input is a {@link FilterCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Filter} or a {@link Page} of {@link Filter} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class FilterQueryService extends QueryService<Filter> {

    private final Logger log = LoggerFactory.getLogger(FilterQueryService.class);

    private final FilterRepository filterRepository;

    public FilterQueryService(FilterRepository filterRepository) {
        this.filterRepository = filterRepository;
    }

    /**
     * Return a {@link List} of {@link Filter} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Filter> findByCriteria(FilterCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Filter> specification = createSpecification(criteria);
        return filterRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Filter} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Filter> findByCriteria(FilterCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Filter> specification = createSpecification(criteria);
        return filterRepository.findAll(specification, page);
    }

    public Page<FilterInfoProjection> findFiltersDTOWithId(List<Long> ids, Pageable pageable){
        return filterRepository.findFiltersDTOWithId(ids, pageable);
    }

    /**
     * Function to convert FilterCriteria to a {@link Specification}
     */
    private Specification<Filter> createSpecification(FilterCriteria criteria) {
        Specification<Filter> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Filter_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Filter_.name));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), Filter_.active));
            }
            if (criteria.getKeyWord() != null) {
                specification = specification.and(buildStringSpecification(criteria.getKeyWord(), Filter_.keyWord));
            }
            if (criteria.getDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDate(), Filter_.date));
            }
            if (criteria.getSource() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSource(), Filter_.source));
            }
            if (criteria.getDateChange() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateChange(), Filter_.dateChange));
            }
            if (criteria.getCidadePoloId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getCidadePoloId(), Filter_.cidadePolo, Region_.id));
            }
            if (criteria.getCategoryId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getCategoryId(), Filter_.category, Category_.id));
            }
            if (criteria.getUserId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getUserId(), Filter_.user, User_.id));
            }
            if (criteria.getRegionId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getRegionId(), Filter_.regions, Region_.id));
            }
        }
        return specification;
    }

}
