package hr.maps.goalmanagment.persistence.entities;

import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "gm_country")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Country extends BaseEntity {

  /**
   *
   */
  private static final long serialVersionUID = 9083326084374535654L;

  private String countryLabel;
  private String countryCode;
}
