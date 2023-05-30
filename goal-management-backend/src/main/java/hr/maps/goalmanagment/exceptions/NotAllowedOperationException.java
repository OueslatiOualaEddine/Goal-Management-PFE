package hr.maps.goalmanagment.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
public class NotAllowedOperationException extends RuntimeException {

  private static final long serialVersionUID = 7984067888798869942L;

  public NotAllowedOperationException(String message) {
    super(message);
  }
}
