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

import br.com.ibict.domain.MarkerIcon;
import br.com.ibict.domain.*; // for static metamodels
import br.com.ibict.repository.MarkerIconRepository;
import br.com.ibict.service.dto.MarkerIconCriteria;


/**
 * Service for executing complex queries for MarkerIcon entities in the database.
 * The main input is a {@link MarkerIconCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MarkerIcon} or a {@link Page} of {@link MarkerIcon} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MarkerIconQueryService extends QueryService<MarkerIcon> {

    private final Logger log = LoggerFactory.getLogger(MarkerIconQueryService.class);

    private final MarkerIconRepository markerIconRepository;

    public MarkerIconQueryService(MarkerIconRepository markerIconRepository) {
        this.markerIconRepository = markerIconRepository;
    }

    /**
     * Return a {@link List} of {@link MarkerIcon} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MarkerIcon> findByCriteria(MarkerIconCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<MarkerIcon> specification = createSpecification(criteria);
        return markerIconRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link MarkerIcon} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MarkerIcon> findByCriteria(MarkerIconCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<MarkerIcon> specification = createSpecification(criteria);
        return markerIconRepository.findAll(specification, page);
    }

    /**
     * Function to convert MarkerIconCriteria to a {@link Specification}
     */
    private Specification<MarkerIcon> createSpecification(MarkerIconCriteria criteria) {
        Specification<MarkerIcon> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), MarkerIcon_.id));
            }
            if (criteria.getIconSize() != null) {
                specification = specification.and(buildStringSpecification(criteria.getIconSize(), MarkerIcon_.iconSize));
            }
            if (criteria.getShadowSize() != null) {
                specification = specification.and(buildStringSpecification(criteria.getShadowSize(), MarkerIcon_.shadowSize));
            }
            if (criteria.getIconAnchor() != null) {
                specification = specification.and(buildStringSpecification(criteria.getIconAnchor(), MarkerIcon_.iconAnchor));
            }
            if (criteria.getShadowAnchor() != null) {
                specification = specification.and(buildStringSpecification(criteria.getShadowAnchor(), MarkerIcon_.shadowAnchor));
            }
            if (criteria.getPopupAnchor() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPopupAnchor(), MarkerIcon_.popupAnchor));
            }
        }
        return specification;
    }

}
