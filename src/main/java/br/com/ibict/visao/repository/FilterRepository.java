package br.com.ibict.visao.repository;

import br.com.ibict.visao.domain.Filter;
import br.com.ibict.visao.dto.FilterInfoProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Filter entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FilterRepository extends JpaRepository<Filter, Long>, JpaSpecificationExecutor<Filter> {

    @Query("select filter from Filter filter where filter.user.login = ?#{principal.username}")
    List<Filter> findByUserIsCurrentUser();

    @Query(value = "select distinct filter from Filter filter left join fetch filter.regions",
        countQuery = "select count(distinct filter) from Filter filter")
    Page<Filter> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct filter from Filter filter left join fetch filter.regions")
    List<Filter> findAllWithEagerRelationships();

    @Query("select filter from Filter filter left join fetch filter.regions where filter.id =:id")
    Optional<Filter> findOneWithEagerRelationships(@Param("id") Long id);

    @Query("select filter.id as id, filter.name as name, filter.description as description from Filter filter where filter.id in (:ids)")
    Page<FilterInfoProjection> findFiltersDTOWithId(@Param("ids") List<Long> ids, Pageable pageable);
}
