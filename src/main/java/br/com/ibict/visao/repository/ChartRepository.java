package br.com.ibict.visao.repository;

import br.com.ibict.visao.domain.Chart;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Chart entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ChartRepository extends JpaRepository<Chart, Long>, JpaSpecificationExecutor<Chart> {

    @Query("select chart from Chart chart where chart.owner.login = ?#{principal.username}")
    List<Chart> findByOwnerIsCurrentUser();

    @Query(value = "select distinct chart from Chart chart left join fetch chart.shareds",
        countQuery = "select count(distinct chart) from Chart chart")
    Page<Chart> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct chart from Chart chart left join fetch chart.shareds")
    List<Chart> findAllWithEagerRelationships();

    @Query("select chart from Chart chart left join fetch chart.shareds where chart.id =:id")
    Optional<Chart> findOneWithEagerRelationships(@Param("id") Long id);

}
