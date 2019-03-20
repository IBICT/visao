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

import br.com.ibict.visao.domain.TypePresentation;
import br.com.ibict.visao.domain.*; // for static metamodels
import br.com.ibict.visao.repository.TypePresentationRepository;
import br.com.ibict.visao.service.dto.TypePresentationCriteria;


/**
 * Service for executing complex queries for TypePresentation entities in the database.
 * The main input is a {@link TypePresentationCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link TypePresentation} or a {@link Page} of {@link TypePresentation} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TypePresentationQueryService extends QueryService<TypePresentation> {

    private final Logger log = LoggerFactory.getLogger(TypePresentationQueryService.class);

    private final TypePresentationRepository typePresentationRepository;

    public TypePresentationQueryService(TypePresentationRepository typePresentationRepository) {
        this.typePresentationRepository = typePresentationRepository;
    }

    /**
     * Return a {@link List} of {@link TypePresentation} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TypePresentation> findByCriteria(TypePresentationCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<TypePresentation> specification = createSpecification(criteria);
        return typePresentationRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link TypePresentation} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TypePresentation> findByCriteria(TypePresentationCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<TypePresentation> specification = createSpecification(criteria);
        return typePresentationRepository.findAll(specification, page);
    }

    /**
     * Function to convert TypePresentationCriteria to a {@link Specification}
     */
    private Specification<TypePresentation> createSpecification(TypePresentationCriteria criteria) {
        Specification<TypePresentation> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), TypePresentation_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), TypePresentation_.name));
            }
            if (criteria.getDisplay() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDisplay(), TypePresentation_.display));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), TypePresentation_.description));
            }
        }
        return specification;
    }

}
