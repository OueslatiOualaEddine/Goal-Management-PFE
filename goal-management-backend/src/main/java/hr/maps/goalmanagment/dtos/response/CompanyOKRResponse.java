package hr.maps.goalmanagment.dtos.response;

import hr.maps.goalmanagment.dtos.KeyResultDetails;
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
public class CompanyOKRResponse {
  private UUID campanyOkrUuid;
  private String campanyOkrName;
 // private List<KeyResultResponse> keyResults;
}
