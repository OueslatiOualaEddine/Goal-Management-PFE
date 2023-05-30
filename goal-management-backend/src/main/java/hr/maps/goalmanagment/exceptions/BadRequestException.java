package hr.maps.goalmanagment.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException {

  private static final long serialVersionUID = 7428279561375909688L;

  public BadRequestException(String message) {
    super(message);
  }
}
