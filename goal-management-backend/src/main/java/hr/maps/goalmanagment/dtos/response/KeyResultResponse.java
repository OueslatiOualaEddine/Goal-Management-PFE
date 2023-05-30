package hr.maps.goalmanagment.dtos.response;

import hr.maps.goalmanagment.dtos.TeamOKRDetails;
import hr.maps.goalmanagment.dtos.request.TeamRequest;
import hr.maps.goalmanagment.persistence.entities.Team;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Rokaya
 * @Date 25/04/2023
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class KeyResultResponse {
  private UUID keyResultUuid;
  private String keyResultLabel;
  List<TeamRequest> teams;
  private List<TeamOKRDetails> teamOKRs;
}
