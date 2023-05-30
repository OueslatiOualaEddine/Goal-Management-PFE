package hr.maps.goalmanagment.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.OptBoolean;
import hr.maps.goalmanagment.enumeration.OkrStatus;
import java.util.Date;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Rokaya
 * @Date 09/04/2023
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanyOKRDetails {
  private UUID campanyOkrUuid;
  private String campanyOkrName;
  private String campanyOkrDescription;
  @JsonFormat(
      shape = JsonFormat.Shape.STRING,
      pattern = "yyyy-MM-dd",
      lenient = OptBoolean.FALSE)
  private Date startDate;
  @JsonFormat(
      shape = JsonFormat.Shape.STRING,
      pattern = "yyyy-MM-dd",
      lenient = OptBoolean.FALSE)
  private Date endDate;
  private OkrStatus companyOkrStatus;
  private double advancement;

}
