package br.com.ibict.visao.repository;

import br.com.ibict.visao.domain.MarkerIcon;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the MarkerIcon entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MarkerIconRepository extends JpaRepository<MarkerIcon, Long>, JpaSpecificationExecutor<MarkerIcon> {

}
