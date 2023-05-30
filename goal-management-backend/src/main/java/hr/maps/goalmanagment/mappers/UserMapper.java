package hr.maps.goalmanagment.mappers;

import hr.maps.goalmanagment.persistence.repositories.UserRepository;
import org.springframework.stereotype.Component;

import hr.maps.goalmanagment.dtos.UserDetails;
import hr.maps.goalmanagment.dtos.UserDto;
import hr.maps.goalmanagment.dtos.response.UserResponse;
import hr.maps.goalmanagment.dtos.response.User_Details;
import hr.maps.goalmanagment.persistence.entities.User;
import hr.maps.goalmanagment.service.UserUtils;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserMapper {

	private final UserUtils userUtils;
	private final UserRepository userRepository;

	public UserDto toUserDto(User user) {
		return new UserDto(user.getUserFirstName(), user.getUserLastName(), user.getCreatedAt());
	}
	public User toUser(UserDetails userDetails) {
		return userRepository.findByUuid(userDetails.getUserUuid()).orElse(null);
	}
	public User_Details toUserDetails(User user) {
		return new User_Details(user.getUuid(), user.getUserFirstName(), user.getUserLastName(),
				userUtils.getPictureProfile(user));
	}

	public UserDetails toUserDetailsResponse(User user) {
		return new UserDetails(user.getUuid(), user.getUserFirstName(), user.getUserLastName(), user.getUserEmail(),
				user.getUserPhoneNumber(),userUtils.getPictureProfile(user), user.getUserRole().getRoleCode()

		);
	}

	public UserResponse toUserResponse(User user) {
		return new UserResponse(user.getUuid(), user.getUserFirstName(), user.getUserLastName(), user.getCreatedAt(),
				user.getUserEmail(), userUtils.getPictureProfile(user)

		);
	}

}
