package hr.maps.goalmanagment.persistence.repositories;

import hr.maps.goalmanagment.persistence.entities.ResetPassword;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResetPasswordRepository extends JpaRepository<ResetPassword, Long> {

  Optional<ResetPassword> findByResetPasswordUuid(UUID resetPasswordUuid);

  Boolean existsByResetPasswordUuid(UUID resetPasswordUuid);
}
