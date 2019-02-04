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

import br.com.ibict.visao.domain.Layer;
import br.com.ibict.visao.domain.*; // for static metamodels
import br.com.ibict.visao.repository.LayerRepository;
import br.com.ibict.visao.service.dto.LayerCriteria;


/**
 * Service for executing complex queries for Layer entities in the database.
 * The main input is a {@link LayerCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Layer} or a {@link Page} of {@link Layer} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class LayerQueryService extends QueryService<Layer> {

    private final Logger log = LoggerFactory.getLogger(LayerQueryService.class);

    private final LayerRepository layerRepository;

    public LayerQueryService(LayerRepository layerRepository) {
        this.layerRepository = layerRepository;
    }

    /**
     * Return a {@link List} of {@link Layer} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Layer> findByCriteria(LayerCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Layer> specification = createSpecification(criteria);
        return layerRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Layer} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Layer> findByCriteria(LayerCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Layer> specification = createSpecification(criteria);
        return layerRepository.findAll(specification, page);
    }

    /**
     * Function to convert LayerCriteria to a {@link Specification}
     */
    private Specification<Layer> createSpecification(LayerCriteria criteria) {
        Specification<Layer> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Layer_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Layer_.name));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), Layer_.active));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), Layer_.description));
            }
            if (criteria.getKeyWord() != null) {
                specification = specification.and(buildStringSpecification(criteria.getKeyWord(), Layer_.keyWord));
            }
            if (criteria.getDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDate(), Layer_.date));
            }
            if (criteria.getProducer() != null) {
                specification = specification.and(buildStringSpecification(criteria.getProducer(), Layer_.producer));
            }
            if (criteria.getSource() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSource(), Layer_.source));
            }
            if (criteria.getDateChange() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateChange(), Layer_.dateChange));
            }
            if (criteria.getNote() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNote(), Layer_.note));
            }
        }
        return specification;
    }

}
