package br.com.ibict.visao.repository;

import br.com.ibict.visao.domain.Year;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Year entity.
 */
@SuppressWarnings("unused")
@Repository
public interface YearRepository extends JpaRepository<Year, Long>, JpaSpecificationExecutor<Year> {

    @Query(value = "SELECT DISTINCT `indicator`.`year_id` AS `id`, `year`.`jhi_date` AS `jhi_date` FROM `indicator` AS `indicator` INNER JOIN `year` AS `year` ON `year`.`id` = `indicator`.`year_id` WHERE `indicator`.`name_id` = :nameId", nativeQuery = true)
    Page<Year> findAllFromIndicator(@Param("nameId") Long nameId, Pageable page);
}
