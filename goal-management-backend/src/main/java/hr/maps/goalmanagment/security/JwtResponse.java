package hr.maps.goalmanagment.security;

import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class JwtResponse {

	private String token;

	private UUID uuid;
	private String refreshToken;
	private String username;
	private String userFullName;
	private List<String> roles;

}
