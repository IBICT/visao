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

import br.com.ibict.visao.domain.MetaDado;
import br.com.ibict.visao.domain.*; // for static metamodels
import br.com.ibict.visao.repository.MetaDadoRepository;
import br.com.ibict.visao.service.dto.MetaDadoCriteria;


/**
 * Service for executing complex queries for MetaDado entities in the database.
 * The main input is a {@link MetaDadoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MetaDado} or a {@link Page} of {@link MetaDado} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MetaDadoQueryService extends QueryService<MetaDado> {

    private final Logger log = LoggerFactory.getLogger(MetaDadoQueryService.class);

    private final MetaDadoRepository metaDadoRepository;

    public MetaDadoQueryService(MetaDadoRepository metaDadoRepository) {
        this.metaDadoRepository = metaDadoRepository;
    }

    /**
     * Return a {@link List} of {@link MetaDado} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MetaDado> findByCriteria(MetaDadoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<MetaDado> specification = createSpecification(criteria);
        return metaDadoRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link MetaDado} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MetaDado> findByCriteria(MetaDadoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<MetaDado> specification = createSpecification(criteria);
        return metaDadoRepository.findAll(specification, page);
    }

    /**
     * Function to convert MetaDadoCriteria to a {@link Specification}
     */
    private Specification<MetaDado> createSpecification(MetaDadoCriteria criteria) {
        Specification<MetaDado> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), MetaDado_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), MetaDado_.name));
            }
            if (criteria.getValue() != null) {
                specification = specification.and(buildStringSpecification(criteria.getValue(), MetaDado_.value));
            }
            if (criteria.getNomeId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getNomeId(), MetaDado_.nomes, Name_.id));
            }
        }
        return specification;
    }

}
