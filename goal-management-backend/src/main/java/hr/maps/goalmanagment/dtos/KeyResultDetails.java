package hr.maps.goalmanagment.dtos;

import hr.maps.goalmanagment.dtos.request.TeamRequest;
import hr.maps.goalmanagment.enumeration.KeyResultType;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Rokaya
 * @Date 14/04/2023
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KeyResultDetails {
  private UUID keyResultUuid;
  private String keyResultLabel;
  private Double keyResultStartValue;
  private Double keyResultTargetValue;
  private KeyResultType keyResultType;
  private List<TeamRequest> teamList;
  private int teamOkrNumber;
  private UUID companyOKRUuid;
  private double advancement;
}
