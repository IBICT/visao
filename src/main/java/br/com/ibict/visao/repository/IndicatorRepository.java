package br.com.ibict.visao.repository;

import br.com.ibict.visao.domain.Indicator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the Indicator entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IndicatorRepository extends JpaRepository<Indicator, Long>, JpaSpecificationExecutor<Indicator> {

    @Query(value = "SELECT indicator.* FROM indicator indicator " +
        "inner JOIN filter_region fr ON indicator.region_id = fr.regions_id " +
        "where " +
        " indicator.name_id = :nameId " +
        " and indicator.year_id = :yearId " +
        " and fr.filters_id in (:filtersId) "
        , nativeQuery = true)
    Page<Indicator> getIndicadorFilter(@Param("nameId") Long nameId,
                                       @Param("yearId")Long yearId,
                                       @Param("filtersId") List<Long> filtersId,
                                       Pageable page);

    @Query(value = "SELECT `id` FROM `year` WHERE `year`.`jhi_date` =(SELECT MAX(ANO) FROM ( SELECT DISTINCT `indicator`.`year_id`, `year`.`jhi_date` ANO FROM `indicator` AS `indicator` INNER JOIN `year` AS `year` ON `year`.`id` = `indicator`.`year_id` WHERE `indicator`.`name_id` = :nameId ) AS `consulta` )", nativeQuery = true)
    Long getMostRecentYearFromIndicator(@Param("nameId") Long nameId);
}
