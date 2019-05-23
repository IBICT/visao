package br.com.ibict.visao.repository;

import br.com.ibict.visao.domain.Category;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Category entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>, JpaSpecificationExecutor<Category> {

    @Query("select category from Category category where category.owner.login = ?#{principal.username}")
    List<Category> findByOwnerIsCurrentUser();

}
