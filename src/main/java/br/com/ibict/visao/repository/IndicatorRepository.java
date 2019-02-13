package br.com.ibict.visao.repository;

import br.com.ibict.visao.domain.Indicator;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Indicator entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IndicatorRepository extends JpaRepository<Indicator, Long>, JpaSpecificationExecutor<Indicator> {

}
