package hr.maps.goalmanagment.dtos;


import hr.maps.goalmanagment.enumeration.RoleCode;

import java.util.Date;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDetails {

  private UUID userUuid;
  private String userFirstName;
  private String userLastName;
  private String userEmail;
  private String userPhoneNumber;
  private MediaDetails userProfilePicture;
  private RoleCode userRole;


}
