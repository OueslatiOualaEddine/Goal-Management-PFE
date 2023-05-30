package hr.maps.goalmanagment.persistence.entities;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;


@Entity
@Table(name = "gm_team")
@Data
@NoArgsConstructor
public class Team extends BaseEntity{

  private String teamName;
  @Fetch(value = FetchMode.SUBSELECT)
  @ManyToMany(cascade = CascadeType.PERSIST,fetch = FetchType.EAGER)
  private List<User> members;
  @OneToOne
  private User manager;

}
