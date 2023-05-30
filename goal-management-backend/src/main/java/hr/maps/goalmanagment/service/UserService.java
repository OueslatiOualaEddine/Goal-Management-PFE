package hr.maps.goalmanagment.service;

import hr.maps.goalmanagment.dtos.EmailDto;
import hr.maps.goalmanagment.dtos.PageDto;
import hr.maps.goalmanagment.dtos.request.ResetPasswordRequestDto;
import hr.maps.goalmanagment.enumeration.EmailContext;
import hr.maps.goalmanagment.persistence.entities.Media;
import hr.maps.goalmanagment.persistence.entities.ResetPassword;
import hr.maps.goalmanagment.persistence.entities.Team;
import hr.maps.goalmanagment.service.email.EmailService;
import hr.maps.goalmanagment.utils.Constants;
import hr.maps.goalmanagment.utils.Utils;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import hr.maps.goalmanagment.dtos.MediaDetails;
import hr.maps.goalmanagment.dtos.UserDetails;
import hr.maps.goalmanagment.dtos.request.UserRequest;
import hr.maps.goalmanagment.enumeration.MediaContext;
import hr.maps.goalmanagment.enumeration.RoleCode;
import hr.maps.goalmanagment.mappers.MediaDatailsMapper;
import hr.maps.goalmanagment.mappers.UserMapper;
import hr.maps.goalmanagment.persistence.entities.Role;
import hr.maps.goalmanagment.persistence.entities.User;
import hr.maps.goalmanagment.persistence.repositories.RoleRepository;
import hr.maps.goalmanagment.persistence.repositories.UserRepository;
import hr.maps.goalmanagment.utils.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;

	private final UserMapper userMapper;

	private final PasswordEncoder encoder;

	private final RoleRepository roleRepository;
	
	private final MediaDatailsMapper mediaDatailsMapper;
	private final MediaService mediaService;

	private final ResetPasswordService resetPasswordService;
	private final EmailService emailService;
	private final TeamService teamService;




	public List<UserDetails> getAllUsers() {
		return this.userRepository.findAll().stream().map(userMapper::toUserDetailsResponse).collect(Collectors.toList());
	}

	public PageDto<UserDetails> getPagedUsers(int page,int offset, RoleCode userType) {
		Pageable pageable = PageRequest.of(page, offset, Sort.by("userFirstName").ascending());
		Page<User> users = null;
		if(userType == null) {
			users = this.userRepository.findAll(pageable);
		} else {
			users = this.userRepository.findByUserRoleRoleCode(userType, pageable);
		}
		
		return new PageDto<UserDetails>(users.getContent().stream().map(userMapper::toUserDetailsResponse).collect(Collectors.toList()),users.getTotalElements());
	}

	public User getUserByUUID(UUID userUuid) {
		return this.userRepository.findByUuid(userUuid).orElse(null);
	}

	public User updateUser(UserRequest userRequest) {
	User user;
			user=userRequest.getUserUuid()!=null?userRepository.findByUuid(userRequest.getUserUuid()).orElse(null):getCurrentUser();
	if(user!=null) {
		user.setUserEmail(userRequest.getUserEmail());
		user.setUserFirstName(userRequest.getUserFirstName());
		user.setUserLastName(userRequest.getUserLastName());
		user.setUserPhoneNumber(userRequest.getUserPhoneNumber());
		if(userRequest.getRole()!=null){
		Role lodgerRole = this.roleRepository.findByRoleCode(userRequest.getRole());
		user.setUserRole(lodgerRole);};

		if (userRequest.getProfilePicture() != null) {
			try {
				Media oldProfilePic=user.getMedias().stream().filter(media -> media.getMediaContext().equals(MediaContext.PICTURE_PROFIL)).findFirst().orElse(null);
				if(oldProfilePic!=null) {
					mediaService.deleteMedia(oldProfilePic.getId());
				}
				Media media = mediaService
						.saveMedia(userRequest.getProfilePicture(), MediaContext.PICTURE_PROFIL);
				user.getMedias().add(media);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		user=userRepository.save(user);
	}
		return user;
	}

	public void deleteUser(UUID userUuid) {
		User user = getUserByUUID(userUuid);
		this.userRepository.deleteById(user.getId());
	}

	public UserDetails getUserInfo() {
		User user = getCurrentUser();
		return user != null ? userMapper.toUserDetailsResponse(getCurrentUser()) : null;
	}

	public User getCurrentUser() {
		UUID currentuserId = SecurityUtil.getCurrentUserUuid();
		User user = getUserByUUID(currentuserId);
		return user;
	}

	public List<User> getUserDetailsyRole(RoleCode roleCode) {
		return userRepository.findByUserRoleRoleCode(roleCode);

	}

	public Boolean checkUserEmail(String userEmail) {
		log.info("Check existence of User Email: {}", userEmail);
		return userRepository.findByUserEmail(userEmail).isPresent();
	}

	public Boolean checkUserPhoneNumber(String PhoneNumber) {
		log.info("Check existence of User Phone Number: {}", PhoneNumber);
		return userRepository.findByUserPhoneNumber(PhoneNumber).isPresent();
	}

	public User getByUserEmail(String userEmail) {
		log.info("Check existence of User Email: {}", userEmail);
		return userRepository.findByUserEmail(userEmail).orElse(null);
	}

	public void saveUser(UserRequest userIn) {
		if (!checkUserEmail(userIn.getUserEmail())) {
			User user = new User();
			user.setUserEmail(userIn.getUserEmail());
			user.setUserFirstName(userIn.getUserFirstName());
			user.setUserLastName(userIn.getUserLastName());
			String password="Azerty123";
			user.setUserPassword(encoder.encode(password));
			user.setUserPhoneNumber(userIn.getUserPhoneNumber());
			Role lodgerRole = roleRepository.findByRoleCode(userIn.getRole());
			user.setUserRole(lodgerRole);
			if (userIn.getProfilePicture()!=null ) {
				try {
					Media media = mediaService.saveMedia(userIn.getProfilePicture(), MediaContext.PICTURE_PROFIL);
					user.getMedias().add(media);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			UUID userUuid=userRepository.save(user).getUuid();
			//sendPasswordEmail(userUuid,password);

		}
	}

	public User saveOrUpdateUser(User user) {
		return this.userRepository.save(user);
	}
	 public MediaDetails getPictureProfile(User user){
	   return  mediaDatailsMapper.toMediaDetails(user.getMedias().stream()
	       .filter(media -> media.getMediaContext() == MediaContext.PICTURE_PROFIL)
	       .findFirst().orElse(null));

	 }

	public void sendPasswordEmail(UUID userUuid,String password) {
		User user = getUserByUUID(userUuid);
		ResetPassword resetPassword = resetPasswordService.createResetPassword(user);
		Map<String, Object> maps = new HashMap<>();
		maps.put("userPassword", password);
		maps.put("userEmail", user.getUserEmail());
		maps.put("requestUuid", resetPassword.getResetPasswordUuid().toString());
		EmailDto userEmailDto = new EmailDto( Constants.MAIL_SUBJECT_USER_WELCOME,"reset-password.html" ,
				 maps, new HashMap<>(),EmailContext.WELCOME_USER);
		emailService.sendMail(userEmailDto, Stream.of(user.getUserEmail()).collect(Collectors.toList()));
	}


	@Transactional
	public boolean resetPassword(ResetPasswordRequestDto dto) {
		//ResetPassword resetPassword = resetPasswordService.getByResetPasswordUuid(dto.getRequestUuid());
		User user = getCurrentUser();
		if(encoder.matches(dto.getOldPassword(),user.getUserPassword())) {
			user.setUserPassword(encoder.encode(dto.getPassword()));
			userRepository.saveAndFlush(user);
			return true;
		}else{
			return false;
		}
		//resetPasswordService.removeResetPassword(resetPassword);
	}

	public List<UserDetails> getTeamMembers(){
		User currentUser=getCurrentUser();
		Team team=null;
		if(currentUser.getUserRole().getRoleCode()==RoleCode.COLLABORATOR){
			team=teamService.getTeamByMember(currentUser);
		}else if (currentUser.getUserRole().getRoleCode()==RoleCode.MANAGER){
			 team=teamService.getTeamByManager(currentUser);
		}

		List<User> members=(team == null) ? Collections.emptyList() :team.getMembers().stream().filter(user -> user.getUuid()!=currentUser.getUuid()).collect(Collectors.toList());
		members.add(team.getManager());
		return members.stream().map(userMapper::toUserDetailsResponse).collect(Collectors.toList());

	}
}
