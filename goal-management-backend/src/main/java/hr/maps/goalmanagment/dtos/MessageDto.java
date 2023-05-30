package hr.maps.goalmanagment.dtos;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Rokaya
 * @Date 09/05/2023
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageDto {
  private String messageBody;
  private UUID destinationUuid;
  private String taskLabel;


}
