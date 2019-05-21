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

import br.com.ibict.visao.domain.GroupCategory;
import br.com.ibict.visao.domain.*; // for static metamodels
import br.com.ibict.visao.repository.GroupCategoryRepository;
import br.com.ibict.visao.service.dto.GroupCategoryCriteria;


/**
 * Service for executing complex queries for GroupCategory entities in the database.
 * The main input is a {@link GroupCategoryCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link GroupCategory} or a {@link Page} of {@link GroupCategory} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class GroupCategoryQueryService extends QueryService<GroupCategory> {

    private final Logger log = LoggerFactory.getLogger(GroupCategoryQueryService.class);

    private final GroupCategoryRepository groupCategoryRepository;

    public GroupCategoryQueryService(GroupCategoryRepository groupCategoryRepository) {
        this.groupCategoryRepository = groupCategoryRepository;
    }

    /**
     * Return a {@link List} of {@link GroupCategory} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<GroupCategory> findByCriteria(GroupCategoryCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<GroupCategory> specification = createSpecification(criteria);
        return groupCategoryRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link GroupCategory} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<GroupCategory> findByCriteria(GroupCategoryCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<GroupCategory> specification = createSpecification(criteria);
        return groupCategoryRepository.findAll(specification, page);
    }

    /**
     * Function to convert GroupCategoryCriteria to a {@link Specification}
     */
    private Specification<GroupCategory> createSpecification(GroupCategoryCriteria criteria) {
        Specification<GroupCategory> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), GroupCategory_.id));
            }
            if (criteria.getIconPresentation() != null) {
                specification = specification.and(buildStringSpecification(criteria.getIconPresentation(), GroupCategory_.iconPresentation));
            }
            if (criteria.getIconContentType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getIconContentType(), GroupCategory_.iconContentType));
            }
            if (criteria.getAbout() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAbout(), GroupCategory_.about));
            }
            if (criteria.getPermission() != null) {
                specification = specification.and(buildSpecification(criteria.getPermission(), GroupCategory_.permission));
            }
            if (criteria.getOwnerId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getOwnerId(), GroupCategory_.owner, User_.id));
            }
            if (criteria.getCategoriesId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getCategoriesId(), GroupCategory_.categories, Category_.id));
            }
            if (criteria.getSharedsId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getSharedsId(), GroupCategory_.shareds, User_.id));
            }
        }
        return specification;
    }

}
