package hr.maps.goalmanagment.security;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hr.maps.goalmanagment.dtos.response.RefreshJwResponse;
import hr.maps.goalmanagment.persistence.entities.User;
import hr.maps.goalmanagment.service.RefreshTokenService;
import hr.maps.goalmanagment.service.UserService;
import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

	private final AuthenticationManager authenticationManager;

	private final RefreshTokenService refreshTokenService;

	private final JwtUtils jwtUtils;

	private final UserService userService;

	@CrossOrigin
	@PostMapping("/signin")
	public ResponseEntity<JwtResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getUserPassword()));
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String token = jwtUtils.generateJwtToken(authentication);
		List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
				.collect(Collectors.toList());
		String refreshToken = jwtUtils
				.generateRefreshToken(((UserDetailsImpl) authentication.getPrincipal()).getUsername());
		return ResponseEntity
				.ok(new JwtResponse(token, userDetails.getUuid(), refreshToken, userDetails.getUsername(), userDetails.getUserFullName(), roles));

	}

	@PostMapping("/refresh-token")
	public ResponseEntity<RefreshJwResponse> refreshToken(@RequestHeader(value = "Refresh-token") String refreshToken) {
		RefreshJwResponse newToken = refreshTokenService.refreshUserToken(refreshToken);
		return ResponseEntity.ok(newToken);
	}
}
