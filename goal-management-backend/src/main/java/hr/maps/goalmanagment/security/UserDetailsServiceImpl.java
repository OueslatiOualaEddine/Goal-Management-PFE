package hr.maps.goalmanagment.security;


import javax.transaction.Transactional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import hr.maps.goalmanagment.persistence.entities.Role;
import hr.maps.goalmanagment.persistence.entities.User;
import hr.maps.goalmanagment.persistence.repositories.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

  private final UserRepository userRepository;


  @Override
  @Transactional
  public UserDetails loadUserByUsername(String loginUserUSer) throws UsernameNotFoundException {
    User user = userRepository.findByUserEmail(loginUserUSer)
        .orElseThrow(
            () -> new UsernameNotFoundException("User Not Found with username: " + loginUserUSer));
    return UserDetailsImpl.build(user);
  }

  @Transactional
  public Role getRolesUser(String loginUserUSer) {
    User user = userRepository.findByUserEmail(loginUserUSer)
        .orElseThrow(
            () -> new UsernameNotFoundException("User Not Found with username: " + loginUserUSer));
    return user.getUserRole();
  }
}