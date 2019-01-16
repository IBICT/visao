package br.com.ibict.visao.repository;

import br.com.ibict.visao.domain.Name;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Name entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NameRepository extends JpaRepository<Name, Long>, JpaSpecificationExecutor<Name> {

}
