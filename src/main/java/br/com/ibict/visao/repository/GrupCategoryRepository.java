package br.com.ibict.visao.repository;

import br.com.ibict.visao.domain.GrupCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the GrupCategory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GrupCategoryRepository extends JpaRepository<GrupCategory, Long>, JpaSpecificationExecutor<GrupCategory> {

    @Query("select grup_category from GrupCategory grup_category where grup_category.owner.login = ?#{principal.username}")
    List<GrupCategory> findByOwnerIsCurrentUser();

    @Query(value = "select distinct grup_category from GrupCategory grup_category left join fetch grup_category.categories left join fetch grup_category.shareds",
        countQuery = "select count(distinct grup_category) from GrupCategory grup_category")
    Page<GrupCategory> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct grup_category from GrupCategory grup_category left join fetch grup_category.categories left join fetch grup_category.shareds")
    List<GrupCategory> findAllWithEagerRelationships();

    @Query("select grup_category from GrupCategory grup_category left join fetch grup_category.categories left join fetch grup_category.shareds where grup_category.id =:id")
    Optional<GrupCategory> findOneWithEagerRelationships(@Param("id") Long id);

}
