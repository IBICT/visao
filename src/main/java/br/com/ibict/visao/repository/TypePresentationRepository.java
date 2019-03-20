package br.com.ibict.visao.repository;

import br.com.ibict.visao.domain.TypePresentation;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the TypePresentation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TypePresentationRepository extends JpaRepository<TypePresentation, Long>, JpaSpecificationExecutor<TypePresentation> {

}
