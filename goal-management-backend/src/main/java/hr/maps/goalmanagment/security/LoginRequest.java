package hr.maps.goalmanagment.security;

import lombok.Data;

@Data
public class LoginRequest {

  private String email;

  private String userPassword;
}
