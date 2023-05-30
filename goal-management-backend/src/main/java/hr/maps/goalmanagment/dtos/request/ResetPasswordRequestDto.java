package hr.maps.goalmanagment.dtos.request;

import java.util.UUID;
import javax.validation.constraints.AssertTrue;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class ResetPasswordRequestDto {


  //private UUID requestUuid;
  private String oldPassword;
  private String password;
  private String confirmPassword;

  @AssertTrue()
  private boolean isValid() {
    return this.password.equals(this.confirmPassword);
  }
}
