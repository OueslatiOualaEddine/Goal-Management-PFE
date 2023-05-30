package hr.maps.goalmanagment.controller;

import hr.maps.goalmanagment.dtos.PageDto;
import hr.maps.goalmanagment.dtos.UserDetails;
import hr.maps.goalmanagment.dtos.request.ResetPasswordRequestDto;
import hr.maps.goalmanagment.service.TeamService;
import java.util.List;

import java.util.UUID;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import hr.maps.goalmanagment.dtos.EmailVerifyDto;
import hr.maps.goalmanagment.dtos.request.UserRequest;
import hr.maps.goalmanagment.enumeration.RoleCode;
import hr.maps.goalmanagment.persistence.entities.User;
import hr.maps.goalmanagment.service.UserService;
import lombok.RequiredArgsConstructor;

@RestController
@CrossOrigin
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
  private final UserService userService;
  private final TeamService teamService;


  @GetMapping(value = "/list")
  public List<UserDetails> getAllUsers() {
    return this.userService.getAllUsers();
  }


  @GetMapping()
  public PageDto<UserDetails> getPagedUsers(
      @RequestParam(name = "page",required = false)
          Integer page,
      @RequestParam(name = "offset",required = false)
          Integer offset,
          @RequestParam(name = "userType",required = false)
      RoleCode userType) {
    return this.userService.getPagedUsers(page,offset,userType);
  }

  @GetMapping("user-profile")
  public UserDetails getUserProfile() {
    return this.userService.getUserInfo();
  }
  @PostMapping(value="/add-new-user",produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public void saveOwner(@ModelAttribute UserRequest userRequest) {
	  if(userRequest.getUserPhoneNumber().equals("undefined") || userRequest.getUserPhoneNumber().isEmpty()) userRequest.setUserPhoneNumber(null);
       this.userService.saveUser(userRequest);
  }

  @PatchMapping(value="/update-user",produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public void updateUser(@ModelAttribute UserRequest userRequest) {
	  if(userRequest.getUserPhoneNumber().equals("undefined") || userRequest.getUserPhoneNumber().equals("null") || userRequest.getUserPhoneNumber().isEmpty()) userRequest.setUserPhoneNumber(null);
    this.userService.updateUser(userRequest);
  }

  
  @PostMapping(value = "/verify")
  public Boolean checkUserEmail(@NotEmpty @Email @RequestBody EmailVerifyDto emailVerify) {
    return userService.checkUserEmail(emailVerify.getUserEmail());
  }

  @GetMapping(value = "/verify-phone-number")
  public Boolean checkUserPhoneNumber(@NotEmpty @RequestParam("user_phone_number") String userPhoneNumber) {
    return userService.checkUserPhoneNumber(userPhoneNumber);
  }

  @DeleteMapping
  public void deleteUser(@RequestParam(value = "user-uuid") UUID userUuid){
     userService.deleteUser(userUuid);
  }
  @PostMapping(value = "/reset-password", consumes = MediaType.APPLICATION_JSON_VALUE)
  public boolean resetPassword( @RequestBody ResetPasswordRequestDto resetPasswordRequestDto) {
    return userService.resetPassword(resetPasswordRequestDto);
  }

  @GetMapping(value = "/user-team-member")
  public List<UserDetails> getTeamMembers() {
    return userService.getTeamMembers();
  }


}
