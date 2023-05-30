package hr.maps.goalmanagment.persistence.repositories;

import hr.maps.goalmanagment.persistence.entities.KeyResult;
import hr.maps.goalmanagment.persistence.entities.Team;
import hr.maps.goalmanagment.persistence.entities.TeamOKR;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * @author Rokaya
 * @Date 25/04/2023
 */
public interface TeamOKRRepository extends JpaRepository<TeamOKR,Long> {
  TeamOKR findByUuid(UUID uuid);
  Page<TeamOKR> findTeamOKRByKeyResultUuid(UUID uuid,Pageable pageable);
  List<TeamOKR> findTeamOKRByKeyResultUuid(UUID uuid);

  /*@Query("SELECT t FROM TeamOKR t WHERE t.keyResult = :keyResult AND :team MEMBER OF t.keyResult.teamList")
  List<TeamOKR> findByKeyResultAndTeam(@Param("keyResult")KeyResult keyResult,@Param("team") Team team);
*/
  List<TeamOKR> findByKeyResultAndTeam(KeyResult keyResult,Team team);


}
