package br.com.ibict.repository;

import br.com.ibict.domain.GrupIndicator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the GrupIndicator entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GrupIndicatorRepository extends JpaRepository<GrupIndicator, Long>, JpaSpecificationExecutor<GrupIndicator> {

    @Query("select grup_indicator from GrupIndicator grup_indicator where grup_indicator.owner.login = ?#{principal.username}")
    List<GrupIndicator> findByOwnerIsCurrentUser();

    @Query(value = "select distinct grup_indicator from GrupIndicator grup_indicator left join fetch grup_indicator.categories",
        countQuery = "select count(distinct grup_indicator) from GrupIndicator grup_indicator")
    Page<GrupIndicator> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct grup_indicator from GrupIndicator grup_indicator left join fetch grup_indicator.categories")
    List<GrupIndicator> findAllWithEagerRelationships();

    @Query("select grup_indicator from GrupIndicator grup_indicator left join fetch grup_indicator.categories where grup_indicator.id =:id")
    Optional<GrupIndicator> findOneWithEagerRelationships(@Param("id") Long id);

}
