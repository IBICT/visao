package br.com.ibict.repository;

import br.com.ibict.domain.TypePresentation;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the TypePresentation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TypePresentationRepository extends JpaRepository<TypePresentation, Long> {

}
