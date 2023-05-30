package hr.maps.goalmanagment.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hr.maps.goalmanagment.persistence.entities.Country;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {

  Country findByCountryCode(String countryCode);
}
