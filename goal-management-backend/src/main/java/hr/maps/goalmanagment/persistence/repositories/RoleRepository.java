package hr.maps.goalmanagment.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hr.maps.goalmanagment.enumeration.RoleCode;
import hr.maps.goalmanagment.persistence.entities.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

  Role findByRoleCode(RoleCode roleCode);
}
