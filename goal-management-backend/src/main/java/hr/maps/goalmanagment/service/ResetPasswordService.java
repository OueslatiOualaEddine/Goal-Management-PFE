package hr.maps.goalmanagment.service;

import hr.maps.goalmanagment.exceptions.NotFoundException;
import hr.maps.goalmanagment.persistence.entities.ResetPassword;
import hr.maps.goalmanagment.persistence.entities.User;
import hr.maps.goalmanagment.persistence.repositories.ResetPasswordRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class ResetPasswordService {
  private static final Logger logger = LoggerFactory.getLogger(ResetPasswordService.class);

   private final ResetPasswordRepository resetPasswordRepository;

  /**
   * create new reset password request
   *
   * @param user
   * @return
   */
  public ResetPassword createResetPassword(User user) {
    logger.info("create ResetPassword request for user UUID: {}", user.getUuid());
    ResetPassword resetPassword = new ResetPassword(user);
    return resetPasswordRepository.saveAndFlush(resetPassword);
  }

  public ResetPassword getByResetPasswordUuid(UUID resetPasswordUuid) {
    logger.info("Get ResetPassword with UUID: {}", resetPasswordUuid);
    return resetPasswordRepository
        .findByResetPasswordUuid(resetPasswordUuid)
        .orElseThrow(
            () ->
                new NotFoundException(
                    "User Not Found with username: " + resetPasswordUuid.toString()));
  }

  /**
   * remove reset password request
   *
   * @param resetPassword
   */
  public void removeResetPassword(ResetPassword resetPassword) {
    logger.info("Remove ResetPassword with UUID: {}", resetPassword.getResetPasswordUuid());
    resetPasswordRepository.delete(resetPassword);
  }
}
