package br.com.ibict.visao.repository;

import br.com.ibict.visao.domain.GroupLayer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the GroupLayer entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GroupLayerRepository extends JpaRepository<GroupLayer, Long>, JpaSpecificationExecutor<GroupLayer> {

    @Query("select group_layer from GroupLayer group_layer where group_layer.user.login = ?#{principal.username}")
    List<GroupLayer> findByUserIsCurrentUser();

    @Query(value = "select distinct group_layer from GroupLayer group_layer left join fetch group_layer.categories",
        countQuery = "select count(distinct group_layer) from GroupLayer group_layer")
    Page<GroupLayer> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct group_layer from GroupLayer group_layer left join fetch group_layer.categories")
    List<GroupLayer> findAllWithEagerRelationships();

    @Query("select group_layer from GroupLayer group_layer left join fetch group_layer.categories where group_layer.id =:id")
    Optional<GroupLayer> findOneWithEagerRelationships(@Param("id") Long id);

}
