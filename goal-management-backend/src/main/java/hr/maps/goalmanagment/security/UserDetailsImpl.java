package hr.maps.goalmanagment.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import hr.maps.goalmanagment.persistence.entities.Role;
import hr.maps.goalmanagment.persistence.entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class UserDetailsImpl implements UserDetails {

	private static final long serialVersionUID = 1L;

	private UUID uuid;

	private String username;
	
	private String userFullName;

	@JsonIgnore
	private String userPassword;

	private Collection<? extends GrantedAuthority> authorities;

	public static UserDetailsImpl build(User user) {
		ArrayList<Role> userRoles = new ArrayList<>();
		userRoles.add(user.getUserRole());
		List<GrantedAuthority> authorities = userRoles.stream()
				.map(role -> new SimpleGrantedAuthority(role.getRoleCode().toString())).collect(Collectors.toList());
		return new UserDetailsImpl(user.getUuid(), user.getUserEmail(), user.getUserFirstName() + " " + user.getUserLastName(), user.getUserPassword(), authorities);
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return this.userPassword;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
		// return this.accountStatus == AccountStatus.ACTIVE;
	}

}