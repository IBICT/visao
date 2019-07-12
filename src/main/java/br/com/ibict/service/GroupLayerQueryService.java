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

import br.com.ibict.domain.GroupLayer;
import br.com.ibict.domain.*; // for static metamodels
import br.com.ibict.repository.GroupLayerRepository;
import br.com.ibict.service.dto.GroupLayerCriteria;


/**
 * Service for executing complex queries for GroupLayer entities in the database.
 * The main input is a {@link GroupLayerCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link GroupLayer} or a {@link Page} of {@link GroupLayer} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class GroupLayerQueryService extends QueryService<GroupLayer> {

    private final Logger log = LoggerFactory.getLogger(GroupLayerQueryService.class);

    private final GroupLayerRepository groupLayerRepository;

    public GroupLayerQueryService(GroupLayerRepository groupLayerRepository) {
        this.groupLayerRepository = groupLayerRepository;
    }

    /**
     * Return a {@link List} of {@link GroupLayer} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<GroupLayer> findByCriteria(GroupLayerCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<GroupLayer> specification = createSpecification(criteria);
        return groupLayerRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link GroupLayer} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<GroupLayer> findByCriteria(GroupLayerCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<GroupLayer> specification = createSpecification(criteria);
        return groupLayerRepository.findAll(specification, page);
    }

    /**
     * Function to convert GroupLayerCriteria to a {@link Specification}
     */
    private Specification<GroupLayer> createSpecification(GroupLayerCriteria criteria) {
        Specification<GroupLayer> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), GroupLayer_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), GroupLayer_.name));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), GroupLayer_.active));
            }
            if (criteria.getKeyWord() != null) {
                specification = specification.and(buildStringSpecification(criteria.getKeyWord(), GroupLayer_.keyWord));
            }
            if (criteria.getMetaDadoId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getMetaDadoId(), GroupLayer_.metaDados, MetaDado_.id));
            }
            if (criteria.getOwnerId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getOwnerId(), GroupLayer_.owner, User_.id));
            }
            if (criteria.getCategoryId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getCategoryId(), GroupLayer_.categories, Category_.id));
            }
        }
        return specification;
    }

}
