package hr.maps.goalmanagment.persistence.repositories;

import hr.maps.goalmanagment.persistence.entities.TeamOkrKeyResult;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Rokaya
 * @Date 01/05/2023
 */
@Repository
public interface TeamOkrKeyResultRepository extends JpaRepository<TeamOkrKeyResult,Long> {
  TeamOkrKeyResult findByUuid(UUID uuid);
  Page<TeamOkrKeyResult> findTeamOkrKeyResultByTeamOKRUuid(UUID teamOkrUuid,Pageable pageable);
  List<TeamOkrKeyResult> findTeamOkrKeyResultByTeamOKRUuid(UUID teamOkrUuid);



}
