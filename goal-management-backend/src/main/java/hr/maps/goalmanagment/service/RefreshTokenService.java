package hr.maps.goalmanagment.service;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import hr.maps.goalmanagment.dtos.response.RefreshJwResponse;
import hr.maps.goalmanagment.exceptions.UserForbiddenException;
import hr.maps.goalmanagment.security.JwtUtils;
import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class RefreshTokenService {

  private final JwtUtils jwtUtils;

  public RefreshJwResponse refreshUserToken(String refreshToken) {
    if (!StringUtils.hasText(refreshToken) || jwtUtils.isTokenExpired(refreshToken)) {
      throw new UserForbiddenException("Token is not valide");
    }
    return new RefreshJwResponse(jwtUtils.generateJwtTokenFromExpiredToken(refreshToken));
  }

}
