package hr.maps.goalmanagment.persistence.repositories;

import hr.maps.goalmanagment.persistence.entities.KeyResult;
import hr.maps.goalmanagment.persistence.entities.User;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Rokaya
 * @Date 14/04/2023
 */
public interface KeyResultRepository extends JpaRepository<KeyResult,Long> {
  KeyResult findByUuid(UUID uuid);
  Page<KeyResult> findKeyResultByCompanyOKRUuid(UUID CompanyOkrUuid,Pageable pageable);
  List<KeyResult> findByTeamListManager(User manager);
  Page<KeyResult> findByTeamListManager(User manager,Pageable pageable);
  List<KeyResult> findKeyResultByCompanyOKRUuid(UUID CompanyOkrUuid);


}
