package hr.maps.goalmanagment.persistence.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hr.maps.goalmanagment.enumeration.RoleCode;
import hr.maps.goalmanagment.persistence.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findByUserEmail(String email);
  Optional<User> findByUserPhoneNumber(String phoneNumber);
  List<User> findByUserRoleRoleCode(RoleCode roleCode);
  Page<User> findByUserRoleRoleCode(RoleCode roleCode, Pageable pageable);
  Optional<User> findByUuid(UUID uuid);


}
