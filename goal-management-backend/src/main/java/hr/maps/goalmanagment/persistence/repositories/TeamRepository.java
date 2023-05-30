package hr.maps.goalmanagment.persistence.repositories;

import hr.maps.goalmanagment.persistence.entities.Team;
import hr.maps.goalmanagment.persistence.entities.User;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface TeamRepository extends JpaRepository<Team,Long> {

  Team findByUuid(UUID uuid);
  Team findByManager(User manager);
  @Query("SELECT t FROM Team t JOIN t.members m WHERE m.id = :memberId")
  Optional<Team> findByMember(@Param("memberId") Long memberId);

}
