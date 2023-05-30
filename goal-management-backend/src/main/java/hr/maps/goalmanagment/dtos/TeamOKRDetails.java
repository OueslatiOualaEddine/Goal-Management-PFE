package hr.maps.goalmanagment.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import hr.maps.goalmanagment.enumeration.OkrStatus;
import java.util.Date;
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
//@JsonInclude(Include.NON_NULL)
public class TeamOKRDetails {
  private UUID teamOkrUuid;
  private String teamOkrLabel;
  private String teamOkrDescription;
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
  private Date startDate;
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
  private Date endDate;
  private OkrStatus teamOkrStatus;
  private UUID keyResultUuid;
  private List<TeamOKRDetails> partnerTeamOKRDetails;
  private double advancement;

}
