package hr.maps.goalmanagment.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CityDto {

  private String cityLabel;
  private String cityCode;
  private CountryDto country;
}
