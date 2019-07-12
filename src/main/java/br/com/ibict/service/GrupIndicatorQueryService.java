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

import br.com.ibict.domain.GrupIndicator;
import br.com.ibict.domain.*; // for static metamodels
import br.com.ibict.repository.GrupIndicatorRepository;
import br.com.ibict.service.dto.GrupIndicatorCriteria;


/**
 * Service for executing complex queries for GrupIndicator entities in the database.
 * The main input is a {@link GrupIndicatorCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link GrupIndicator} or a {@link Page} of {@link GrupIndicator} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class GrupIndicatorQueryService extends QueryService<GrupIndicator> {

    private final Logger log = LoggerFactory.getLogger(GrupIndicatorQueryService.class);

    private final GrupIndicatorRepository grupIndicatorRepository;

    public GrupIndicatorQueryService(GrupIndicatorRepository grupIndicatorRepository) {
        this.grupIndicatorRepository = grupIndicatorRepository;
    }

    /**
     * Return a {@link List} of {@link GrupIndicator} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<GrupIndicator> findByCriteria(GrupIndicatorCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<GrupIndicator> specification = createSpecification(criteria);
        return grupIndicatorRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link GrupIndicator} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<GrupIndicator> findByCriteria(GrupIndicatorCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<GrupIndicator> specification = createSpecification(criteria);
        return grupIndicatorRepository.findAll(specification, page);
    }

    /**
     * Function to convert GrupIndicatorCriteria to a {@link Specification}
     */
    private Specification<GrupIndicator> createSpecification(GrupIndicatorCriteria criteria) {
        Specification<GrupIndicator> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), GrupIndicator_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), GrupIndicator_.name));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), GrupIndicator_.active));
            }
            if (criteria.getKeyWord() != null) {
                specification = specification.and(buildStringSpecification(criteria.getKeyWord(), GrupIndicator_.keyWord));
            }
            if (criteria.getDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDate(), GrupIndicator_.date));
            }
            if (criteria.getSource() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSource(), GrupIndicator_.source));
            }
            if (criteria.getDateChange() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateChange(), GrupIndicator_.dateChange));
            }
            if (criteria.getMetaDadoId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getMetaDadoId(), GrupIndicator_.metaDados, MetaDado_.id));
            }
            if (criteria.getOwnerId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getOwnerId(), GrupIndicator_.owner, User_.id));
            }
            if (criteria.getTypePresentationId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getTypePresentationId(), GrupIndicator_.typePresentation, TypePresentation_.id));
            }
            if (criteria.getCategoryId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getCategoryId(), GrupIndicator_.categories, Category_.id));
            }
        }
        return specification;
    }

}
