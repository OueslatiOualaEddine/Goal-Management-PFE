package hr.maps.goalmanagment.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressDto {

  private String addressStreet;
  private Integer addressStreetNumber;
  private String addressPostalCode;
  private CityDto addressCity;

}
