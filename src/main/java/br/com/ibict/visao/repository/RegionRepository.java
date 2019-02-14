package br.com.ibict.visao.repository;

import br.com.ibict.visao.domain.Region;
import br.com.ibict.visao.dto.RegionFilterProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Region entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RegionRepository extends JpaRepository<Region, Long>, JpaSpecificationExecutor<Region> {

    @Query(value = "select distinct region from Region region left join fetch region.relacaos",
        countQuery = "select count(distinct region) from Region region")
    Page<Region> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct region from Region region left join fetch region.relacaos")
    List<Region> findAllWithEagerRelationships();

    @Query("select region from Region region left join fetch region.relacaos where region.id =:id")
    Optional<Region> findOneWithEagerRelationships(@Param("id") Long id);

    @Query(value = "select r.geo_code as geocode, f.name as filtername from region r " +
        " inner join filter_region fr on fr.regions_id = r.id " +
        " inner join filter f on fr.filters_id = f.id " +
        " where fr.filters_id in (:filtersId)",
        nativeQuery = true)
    Page<RegionFilterProjection> findRegionDTOWithFilter(@Param("filtersId") List<Long> filtersId, Pageable page);
}
