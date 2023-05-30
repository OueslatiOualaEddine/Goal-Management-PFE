package hr.maps.goalmanagment.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CountryDto {

  private String countryLabel;
  private String countryCode;
}
