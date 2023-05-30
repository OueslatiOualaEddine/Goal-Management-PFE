package hr.maps.goalmanagment.dtos.request;

import hr.maps.goalmanagment.dtos.AddressDto;
import hr.maps.goalmanagment.dtos.MediaDetails;
import hr.maps.goalmanagment.enumeration.RoleCode;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {
  private UUID userUuid;
  private String userEmail;
  private String userPhoneNumber;
  private String userPassword;
  private String userFirstName;
  private String userLastName;
  private RoleCode role;
  private AddressDto userAddress;
  private MultipartFile profilePicture;
  private MediaDetails userProfilePicture;
}
