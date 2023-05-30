package hr.maps.goalmanagment.dtos.response;

import java.util.Date;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonFormat;

import hr.maps.goalmanagment.dtos.AddressDto;
import hr.maps.goalmanagment.dtos.MediaDetails;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {

  private UUID userUuid;
  private String userFirstName;
  private String userLastName;
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Africa/Tunis")
  private Date userDateCreation;
  private String userEmail;
  private MediaDetails userProfilePicture;
}
