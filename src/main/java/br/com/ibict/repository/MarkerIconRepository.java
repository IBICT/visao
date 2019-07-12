package br.com.ibict.repository;

import br.com.ibict.domain.MarkerIcon;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the MarkerIcon entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MarkerIconRepository extends JpaRepository<MarkerIcon, Long>, JpaSpecificationExecutor<MarkerIcon> {

}
