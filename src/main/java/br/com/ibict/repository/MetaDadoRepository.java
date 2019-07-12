package br.com.ibict.repository;

import br.com.ibict.domain.MetaDado;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the MetaDado entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MetaDadoRepository extends JpaRepository<MetaDado, Long>, JpaSpecificationExecutor<MetaDado> {

}
