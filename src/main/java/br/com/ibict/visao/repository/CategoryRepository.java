package br.com.ibict.visao.repository;

import br.com.ibict.visao.domain.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Category entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>, JpaSpecificationExecutor<Category> {

    @Query(value = "SELECT CATEGORY.* " +
        " FROM CATEGORY CATEGORY " +
        " INNER JOIN GROUP_CATEGORY_CATEGORIES GCC on GCC.CATEGORIES_ID = CATEGORY.ID " +
        " WHERE GCC.GROUP_CATEGORIES_ID = :groupCategoryId ", nativeQuery = true)
    Page<Category> findAllFromGroupCategory(@Param("groupCategoryId") Long groupCategoryId, Pageable page);
}
