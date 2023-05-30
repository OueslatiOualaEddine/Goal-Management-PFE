package hr.maps.goalmanagment.persistence.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "gm_reset_password")
@Data
@NoArgsConstructor
public class ResetPassword implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @JsonIgnore
  private Long resetPasswordId;

  @Type(type = "org.hibernate.type.UUIDCharType")
  private UUID resetPasswordUuid;

  @ManyToOne private User resetPasswordUser;

  public ResetPassword(User resetPasswordUser) {
    this.resetPasswordUser = resetPasswordUser;
  }

  @PrePersist
  public void persistMethod() {
    this.resetPasswordUuid = UUID.randomUUID();
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (!super.equals(obj)) return false;
    if (getClass() != obj.getClass()) return false;
    ResetPassword other = (ResetPassword) obj;
    if (resetPasswordId == null) {
      if (other.resetPasswordId != null) return false;
    } else if (!resetPasswordId.equals(other.resetPasswordId)) return false;
    return true;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + ((resetPasswordId == null) ? 0 : resetPasswordId.hashCode());
    return result;
  }
}
