package hr.maps.goalmanagment.mappers;

import org.springframework.stereotype.Component;

import hr.maps.goalmanagment.dtos.AddressDto;
import hr.maps.goalmanagment.persistence.entities.Address;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AddressMapper {

  private final CityMapper cityMapper;

  public Address toAddress(AddressDto addressRequest) {
    return addressRequest != null ? new Address(addressRequest.getAddressStreet(), addressRequest.getAddressStreetNumber(),
        addressRequest.getAddressPostalCode(),
        cityMapper.toCity(addressRequest.getAddressCity())) : null;
  }

  public AddressDto toAddressDto(Address address) {
    return  new AddressDto(address.getAddressStreet(), address.getAddressStreetNumber(),
        address.getAddressPostalCode(),
        cityMapper.toCityDto(address.getAddressCity()));
  }


}
