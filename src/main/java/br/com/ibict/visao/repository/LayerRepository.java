package br.com.ibict.visao.repository;

import br.com.ibict.visao.domain.Layer;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Layer entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LayerRepository extends JpaRepository<Layer, Long>, JpaSpecificationExecutor<Layer> {

}
