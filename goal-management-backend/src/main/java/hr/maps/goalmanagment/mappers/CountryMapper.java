package hr.maps.goalmanagment.mappers;

import org.springframework.stereotype.Component;

import hr.maps.goalmanagment.dtos.CountryDto;
import hr.maps.goalmanagment.persistence.entities.Country;
import hr.maps.goalmanagment.persistence.repositories.CountryRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CountryMapper {

  private final CountryRepository countryRepository;

  public Country toCountry(CountryDto countryRequest) {
    return countryRepository.findByCountryCode(countryRequest.getCountryCode());

  }

  public CountryDto toCountryDto(Country country) {
    return country != null ? new CountryDto(country.getCountryLabel(), country.getCountryCode()) : null;
  }
}
