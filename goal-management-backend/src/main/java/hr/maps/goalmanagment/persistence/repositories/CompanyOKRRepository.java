package hr.maps.goalmanagment.persistence.repositories;

import hr.maps.goalmanagment.persistence.entities.CompanyOKR;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Rokaya
 * @Date 09/04/2023
 */
public interface CompanyOKRRepository extends JpaRepository<CompanyOKR,Long> {
  Optional<CompanyOKR> findByUuid(UUID uuid);

}
