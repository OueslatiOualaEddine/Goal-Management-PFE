package hr.maps.goalmanagment.dtos.response;

import java.util.UUID;

import hr.maps.goalmanagment.dtos.MediaDetails;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class User_Details {
  private UUID userUuid;
  private String userFirstName;
  private String userLastName;
  private MediaDetails userProfilePicture;

}
