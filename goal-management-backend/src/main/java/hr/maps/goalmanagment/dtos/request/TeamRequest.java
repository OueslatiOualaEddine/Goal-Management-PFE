package hr.maps.goalmanagment.dtos.request;

import hr.maps.goalmanagment.dtos.UserDetails;
import hr.maps.goalmanagment.persistence.entities.User;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TeamRequest {
  private UUID teamUuid;
  private String teamName;
  private List<UserDetails> members;
  private UserDetails manager;
  private double advancement;
}
