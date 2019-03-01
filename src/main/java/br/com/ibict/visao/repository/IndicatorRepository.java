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

    @Query(value = "SELECT ID FROM YEAR WHERE YEAR.JHI_DATE = " +
        "       (SELECT MAX(ANO) FROM (" +
        "           SELECT DISTINCT INDICATOR.YEAR_ID, YEAR.JHI_DATE ANO " +
        "           FROM INDICATOR INDICATOR" +
        "           INNER JOIN YEAR YEAR ON YEAR.ID = INDICATOR.YEAR_ID " +
        "           WHERE INDICATOR.NAME_ID = :nameId " +
        "       ) AS CONSULTA" +
        ") ", nativeQuery = true)
    Long getMostRecentYearFromIndicator(@Param("nameId") Long nameId);
}
