package br.com.ibict.visao.repository;

import br.com.ibict.visao.domain.GroupCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the GroupCategory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GroupCategoryRepository extends JpaRepository<GroupCategory, Long>, JpaSpecificationExecutor<GroupCategory> {

    @Query("select group_category from GroupCategory group_category where group_category.owner.login = ?#{principal.username}")
    List<GroupCategory> findByOwnerIsCurrentUser();

    @Query(value = "select distinct group_category from GroupCategory group_category left join fetch group_category.categories left join fetch group_category.shareds",
        countQuery = "select count(distinct group_category) from GroupCategory group_category")
    Page<GroupCategory> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct group_category from GroupCategory group_category left join fetch group_category.categories left join fetch group_category.shareds")
    List<GroupCategory> findAllWithEagerRelationships();

    @Query("select group_category from GroupCategory group_category left join fetch group_category.categories left join fetch group_category.shareds where group_category.id =:id")
    Optional<GroupCategory> findOneWithEagerRelationships(@Param("id") Long id);

}
