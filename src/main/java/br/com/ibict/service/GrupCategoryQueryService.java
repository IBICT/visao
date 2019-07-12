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

import br.com.ibict.domain.GrupCategory;
import br.com.ibict.domain.*; // for static metamodels
import br.com.ibict.repository.GrupCategoryRepository;
import br.com.ibict.service.dto.GrupCategoryCriteria;


/**
 * Service for executing complex queries for GrupCategory entities in the database.
 * The main input is a {@link GrupCategoryCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link GrupCategory} or a {@link Page} of {@link GrupCategory} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class GrupCategoryQueryService extends QueryService<GrupCategory> {

    private final Logger log = LoggerFactory.getLogger(GrupCategoryQueryService.class);

    private final GrupCategoryRepository grupCategoryRepository;

    public GrupCategoryQueryService(GrupCategoryRepository grupCategoryRepository) {
        this.grupCategoryRepository = grupCategoryRepository;
    }

    /**
     * Return a {@link List} of {@link GrupCategory} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<GrupCategory> findByCriteria(GrupCategoryCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<GrupCategory> specification = createSpecification(criteria);
        return grupCategoryRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link GrupCategory} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<GrupCategory> findByCriteria(GrupCategoryCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<GrupCategory> specification = createSpecification(criteria);
        return grupCategoryRepository.findAll(specification, page);
    }

    /**
     * Function to convert GrupCategoryCriteria to a {@link Specification}
     */
    private Specification<GrupCategory> createSpecification(GrupCategoryCriteria criteria) {
        Specification<GrupCategory> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), GrupCategory_.id));
            }
            if (criteria.getPermission() != null) {
                specification = specification.and(buildSpecification(criteria.getPermission(), GrupCategory_.permission));
            }
            if (criteria.getOwnerId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getOwnerId(), GrupCategory_.owner, User_.id));
            }
            if (criteria.getCategoryId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getCategoryId(), GrupCategory_.categories, Category_.id));
            }
            if (criteria.getSharedId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getSharedId(), GrupCategory_.shareds, User_.id));
            }
        }
        return specification;
    }

}
