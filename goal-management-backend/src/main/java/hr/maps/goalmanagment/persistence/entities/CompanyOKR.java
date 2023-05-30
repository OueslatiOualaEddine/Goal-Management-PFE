package hr.maps.goalmanagment.persistence.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import hr.maps.goalmanagment.enumeration.OkrStatus;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Rokaya
 * @Date 09/04/2023
 */

@Entity
@Table(name = "gm_company_okr")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanyOKR extends BaseEntity{
  private String campanyOkrName;
  @Column(columnDefinition = "TEXT")
  private String campanyOkrDescription;
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
  private Date startDate;
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
  private Date endDate;
  @Enumerated(EnumType.STRING)
  private OkrStatus companyOkrStatus;

}
