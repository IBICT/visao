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

    @Query(value = "SELECT DISTINCT GC.id " +
        "FROM GROUP_CATEGORY GC " +
        "LEFT JOIN GROUP_CATEGORY_SHAREDS GCS on GC.ID = GCS.GROUP_CATEGORIES_ID " +
        "WHERE " +
        "    GC.ID = :groupCategoryCod" +
        "    AND (GC.PERMISSION = 'PUBLIC'" +
        "         OR (COALESCE(:currentUserId, null) IS NOT NULL AND  GC.OWNER_ID = :currentUserId)" +
        "         OR (COALESCE(:currentUserId, null) IS NOT NULL AND GCS.SHAREDS_ID = :currentUserId AND GC.PERMISSION = 'SHARED'))", nativeQuery = true)
    Long isGroupCategoryEnabledByCurrentUser(@Param("groupCategoryCod") Long id,@Param("currentUserId") Long userLogin);

    @Query(value = "SELECT * FROM GROUP_CATEGORY where PERMISSION = 'PUBLIC'", nativeQuery = true)
    Page<GroupCategory> listOfPublicsGroupCategories(Pageable pageable);

    @Query(value = "SELECT DISTINCT GC.* " +
        "FROM GROUP_CATEGORY GC " +
        "LEFT JOIN GROUP_CATEGORY_SHAREDS GCS on GC.ID = GCS.GROUP_CATEGORIES_ID " +
        "WHERE " +
        "    GC.PERMISSION = 'PUBLIC'" +
        "         OR (GC.OWNER_ID = :currentUserId)" +
        "         OR (GCS.SHAREDS_ID = :currentUserId AND GC.PERMISSION = 'SHARED')", nativeQuery = true)
    Page<GroupCategory> listOfGroupCategoriesByUser(@Param("currentUserId")Long currentUserId, Pageable pageable);
}
