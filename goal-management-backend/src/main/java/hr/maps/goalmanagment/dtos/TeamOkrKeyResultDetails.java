package hr.maps.goalmanagment.dtos;

import hr.maps.goalmanagment.enumeration.KeyResultType;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Rokaya
 * @Date 01/05/2023
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TeamOkrKeyResultDetails {
  private UUID keyResultUuid;
  private String keyResultLabel;
  private Double keyResultStartValue;
  private Double keyResultTargetValue;
  private KeyResultType keyResultType;
  private UUID teamOKRUuid;
  private Double advancement;

}
