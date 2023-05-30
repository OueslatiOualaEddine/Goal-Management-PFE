package hr.maps.goalmanagment.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class UserForbiddenException extends RuntimeException {

  private static final long serialVersionUID = 7428279561375909688L;

  public UserForbiddenException(String message) {
    super(message);
  }

  public UserForbiddenException(String message, Throwable cause) {
    super(message, cause);
  }
}
