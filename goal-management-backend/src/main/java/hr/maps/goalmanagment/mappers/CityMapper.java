package hr.maps.goalmanagment.mappers;

import org.springframework.stereotype.Component;

import hr.maps.goalmanagment.dtos.CityDto;
import hr.maps.goalmanagment.persistence.entities.City;
import hr.maps.goalmanagment.persistence.repositories.CityRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CityMapper {


  private final CityRepository cityRepositoty;
  private final CountryMapper countryMapper;

  public City toCity(CityDto cityRequest) {
    return cityRequest != null ? cityRepositoty.findByCityCode(cityRequest.getCityCode()) : null;
  }

  public CityDto toCityDto(City city) {
    return city != null ? new CityDto(city.getCityLabel(), city.getCityCode(),
        countryMapper.toCountryDto(city.getCountry())) : null;
  }
}
