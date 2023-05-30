package hr.maps.goalmanagment.persistence.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import hr.maps.goalmanagment.enumeration.OkrStatus;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 * @author Rokaya
 * @Date 24/04/2023
 */
@Entity
@Table(name = "gm_team_okr")
@Data
@NoArgsConstructor
public class TeamOKR extends BaseEntity {
  private String teamOkrLabel;
  @Column(columnDefinition = "TEXT")
  private String teamOkrDescription;
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
  private Date startDate;
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
  private Date endDate;
  @Enumerated(EnumType.STRING)
  private OkrStatus teamOkrStatus;
  @ManyToOne
  private KeyResult keyResult;
  @Fetch(value = FetchMode.SUBSELECT)
  @OneToMany(fetch = FetchType.EAGER)
  private List<TeamOKR> partnerTeamOKR;
  @ManyToOne
  private Team team;
}
