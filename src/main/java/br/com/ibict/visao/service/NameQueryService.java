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

import br.com.ibict.visao.domain.Name;
import br.com.ibict.visao.domain.*; // for static metamodels
import br.com.ibict.visao.repository.NameRepository;
import br.com.ibict.visao.service.dto.NameCriteria;


/**
 * Service for executing complex queries for Name entities in the database.
 * The main input is a {@link NameCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Name} or a {@link Page} of {@link Name} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class NameQueryService extends QueryService<Name> {

    private final Logger log = LoggerFactory.getLogger(NameQueryService.class);

    private final NameRepository nameRepository;

    public NameQueryService(NameRepository nameRepository) {
        this.nameRepository = nameRepository;
    }

    /**
     * Return a {@link List} of {@link Name} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Name> findByCriteria(NameCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Name> specification = createSpecification(criteria);
        return nameRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Name} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Name> findByCriteria(NameCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Name> specification = createSpecification(criteria);
        return nameRepository.findAll(specification, page);
    }

    /**
     * Function to convert NameCriteria to a {@link Specification}
     */
    private Specification<Name> createSpecification(NameCriteria criteria) {
        Specification<Name> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Name_.id));
            }
            if (criteria.getValue() != null) {
                specification = specification.and(buildStringSpecification(criteria.getValue(), Name_.value));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), Name_.active));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), Name_.description));
            }
            if (criteria.getKeyWord() != null) {
                specification = specification.and(buildStringSpecification(criteria.getKeyWord(), Name_.keyWord));
            }
            if (criteria.getDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDate(), Name_.date));
            }
            if (criteria.getProducer() != null) {
                specification = specification.and(buildStringSpecification(criteria.getProducer(), Name_.producer));
            }
            if (criteria.getSource() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSource(), Name_.source));
            }
            if (criteria.getDateChange() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateChange(), Name_.dateChange));
            }
            if (criteria.getNote() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNote(), Name_.note));
            }
            if (criteria.getCategoryId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getCategoryId(), Name_.category, Category_.id));
            }
            if (criteria.getUserId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getUserId(), Name_.user, User_.id));
            }
        }
        return specification;
    }

}
