package br.com.ibict.visao.repository;

import br.com.ibict.visao.domain.MetaDado;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the MetaDado entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MetaDadoRepository extends JpaRepository<MetaDado, Long>, JpaSpecificationExecutor<MetaDado> {

    @Query(value = "select distinct meta_dado from MetaDado meta_dado left join fetch meta_dado.nomes",
        countQuery = "select count(distinct meta_dado) from MetaDado meta_dado")
    Page<MetaDado> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct meta_dado from MetaDado meta_dado left join fetch meta_dado.nomes")
    List<MetaDado> findAllWithEagerRelationships();

    @Query("select meta_dado from MetaDado meta_dado left join fetch meta_dado.nomes where meta_dado.id =:id")
    Optional<MetaDado> findOneWithEagerRelationships(@Param("id") Long id);

}
