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

import br.com.ibict.domain.GeographicFilter;
import br.com.ibict.domain.*; // for static metamodels
import br.com.ibict.repository.GeographicFilterRepository;
import br.com.ibict.service.dto.GeographicFilterCriteria;


/**
 * Service for executing complex queries for GeographicFilter entities in the database.
 * The main input is a {@link GeographicFilterCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link GeographicFilter} or a {@link Page} of {@link GeographicFilter} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class GeographicFilterQueryService extends QueryService<GeographicFilter> {

    private final Logger log = LoggerFactory.getLogger(GeographicFilterQueryService.class);

    private final GeographicFilterRepository geographicFilterRepository;

    public GeographicFilterQueryService(GeographicFilterRepository geographicFilterRepository) {
        this.geographicFilterRepository = geographicFilterRepository;
    }

    /**
     * Return a {@link List} of {@link GeographicFilter} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<GeographicFilter> findByCriteria(GeographicFilterCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<GeographicFilter> specification = createSpecification(criteria);
        return geographicFilterRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link GeographicFilter} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<GeographicFilter> findByCriteria(GeographicFilterCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<GeographicFilter> specification = createSpecification(criteria);
        return geographicFilterRepository.findAll(specification, page);
    }

    /**
     * Function to convert GeographicFilterCriteria to a {@link Specification}
     */
    private Specification<GeographicFilter> createSpecification(GeographicFilterCriteria criteria) {
        Specification<GeographicFilter> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), GeographicFilter_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), GeographicFilter_.name));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), GeographicFilter_.active));
            }
            if (criteria.getKeyWord() != null) {
                specification = specification.and(buildStringSpecification(criteria.getKeyWord(), GeographicFilter_.keyWord));
            }
            if (criteria.getDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDate(), GeographicFilter_.date));
            }
            if (criteria.getSource() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSource(), GeographicFilter_.source));
            }
            if (criteria.getDateChange() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateChange(), GeographicFilter_.dateChange));
            }
            if (criteria.getCidadePoloId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getCidadePoloId(), GeographicFilter_.cidadePolo, Region_.id));
            }
            if (criteria.getMetaDadoId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getMetaDadoId(), GeographicFilter_.metaDados, MetaDado_.id));
            }
            if (criteria.getOwnerId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getOwnerId(), GeographicFilter_.owner, User_.id));
            }
            if (criteria.getRegionId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getRegionId(), GeographicFilter_.regions, Region_.id));
            }
            if (criteria.getCategoryId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getCategoryId(), GeographicFilter_.categories, Category_.id));
            }
        }
        return specification;
    }

}
