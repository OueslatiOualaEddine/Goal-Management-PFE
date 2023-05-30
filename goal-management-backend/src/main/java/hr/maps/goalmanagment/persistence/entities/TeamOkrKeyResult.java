package hr.maps.goalmanagment.persistence.entities;

import hr.maps.goalmanagment.enumeration.KeyResultStatus;
import hr.maps.goalmanagment.enumeration.KeyResultType;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Rokaya
 * @Date 01/05/2023
 */
@Entity
@Table(name = "gm_team_okr_key_result")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeamOkrKeyResult extends BaseEntity{
  private String keyResultLabel;
  private Double keyResultStartValue;
  private Double keyResultTargetValue;
  @Enumerated(EnumType.STRING)
  private KeyResultType keyResultType;
  @Enumerated(EnumType.STRING)
  private KeyResultStatus keyResultStatus;
  @OneToOne
  private TeamOKR teamOKR;
}
