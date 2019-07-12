package br.com.ibict.repository;

import br.com.ibict.domain.GeographicFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the GeographicFilter entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GeographicFilterRepository extends JpaRepository<GeographicFilter, Long>, JpaSpecificationExecutor<GeographicFilter> {

    @Query("select geographic_filter from GeographicFilter geographic_filter where geographic_filter.owner.login = ?#{principal.username}")
    List<GeographicFilter> findByOwnerIsCurrentUser();

    @Query(value = "select distinct geographic_filter from GeographicFilter geographic_filter left join fetch geographic_filter.regions left join fetch geographic_filter.categories",
        countQuery = "select count(distinct geographic_filter) from GeographicFilter geographic_filter")
    Page<GeographicFilter> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct geographic_filter from GeographicFilter geographic_filter left join fetch geographic_filter.regions left join fetch geographic_filter.categories")
    List<GeographicFilter> findAllWithEagerRelationships();

    @Query("select geographic_filter from GeographicFilter geographic_filter left join fetch geographic_filter.regions left join fetch geographic_filter.categories where geographic_filter.id =:id")
    Optional<GeographicFilter> findOneWithEagerRelationships(@Param("id") Long id);

}
