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

import br.com.ibict.domain.Region;
import br.com.ibict.domain.*; // for static metamodels
import br.com.ibict.repository.RegionRepository;
import br.com.ibict.service.dto.RegionCriteria;


/**
 * Service for executing complex queries for Region entities in the database.
 * The main input is a {@link RegionCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Region} or a {@link Page} of {@link Region} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class RegionQueryService extends QueryService<Region> {

    private final Logger log = LoggerFactory.getLogger(RegionQueryService.class);

    private final RegionRepository regionRepository;

    public RegionQueryService(RegionRepository regionRepository) {
        this.regionRepository = regionRepository;
    }

    /**
     * Return a {@link List} of {@link Region} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Region> findByCriteria(RegionCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Region> specification = createSpecification(criteria);
        return regionRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Region} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Region> findByCriteria(RegionCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Region> specification = createSpecification(criteria);
        return regionRepository.findAll(specification, page);
    }

    /**
     * Function to convert RegionCriteria to a {@link Specification}
     */
    private Specification<Region> createSpecification(RegionCriteria criteria) {
        Specification<Region> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Region_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Region_.name));
            }
            if (criteria.getUf() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUf(), Region_.uf));
            }
            if (criteria.getGeoCode() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getGeoCode(), Region_.geoCode));
            }
            if (criteria.getTypeRegion() != null) {
                specification = specification.and(buildSpecification(criteria.getTypeRegion(), Region_.typeRegion));
            }
            if (criteria.getRelacaoId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getRelacaoId(), Region_.relacaos, Region_.id));
            }
        }
        return specification;
    }

}
