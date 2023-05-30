package hr.maps.goalmanagment.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hr.maps.goalmanagment.persistence.entities.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

}
