package hr.maps.goalmanagment.persistence.entities;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "gm_city")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class City extends BaseEntity {

  /**
   *
   */
  private static final long serialVersionUID = -7692445847604439036L;

  private String cityLabel;
  private String cityCode;
  @ManyToOne
  private Country country;
  @PrePersist
  public void prePersist() {
    this.cityCode = this.cityLabel.trim().toUpperCase().replace(" ","_");
  }
}
