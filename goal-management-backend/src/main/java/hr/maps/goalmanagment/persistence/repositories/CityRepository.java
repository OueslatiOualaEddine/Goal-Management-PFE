package hr.maps.goalmanagment.persistence.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hr.maps.goalmanagment.persistence.entities.City;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {

  City findByCityCode(String cityCode);
  List<City> findByCountryCountryCode(String countryCode);
}
