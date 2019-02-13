package br.com.ibict.visao.repository;

import br.com.ibict.visao.domain.GroupLayer;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the GroupLayer entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GroupLayerRepository extends JpaRepository<GroupLayer, Long>, JpaSpecificationExecutor<GroupLayer> {

    @Query("select group_layer from GroupLayer group_layer where group_layer.user.login = ?#{principal.username}")
    List<GroupLayer> findByUserIsCurrentUser();

}
