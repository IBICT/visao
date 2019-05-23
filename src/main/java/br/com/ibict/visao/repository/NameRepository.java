package br.com.ibict.visao.repository;

import br.com.ibict.visao.domain.Name;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Name entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NameRepository extends JpaRepository<Name, Long>, JpaSpecificationExecutor<Name> {

    @Query("select name from Name name where name.user.login = ?#{principal.username}")
    List<Name> findByUserIsCurrentUser();

    @Query(value = "select distinct name from Name name left join fetch name.categories",
        countQuery = "select count(distinct name) from Name name")
    Page<Name> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct name from Name name left join fetch name.categories")
    List<Name> findAllWithEagerRelationships();

    @Query("select name from Name name left join fetch name.categories where name.id =:id")
    Optional<Name> findOneWithEagerRelationships(@Param("id") Long id);

}
