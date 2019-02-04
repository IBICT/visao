package br.com.ibict.visao.repository;

import br.com.ibict.visao.domain.Year;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Year entity.
 */
@SuppressWarnings("unused")
@Repository
public interface YearRepository extends JpaRepository<Year, Long>, JpaSpecificationExecutor<Year> {

}
