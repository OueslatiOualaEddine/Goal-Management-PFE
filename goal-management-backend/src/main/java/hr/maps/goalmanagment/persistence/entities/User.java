package hr.maps.goalmanagment.persistence.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;

import hr.maps.goalmanagment.utils.Utils;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "gm_user")
@Data
@NoArgsConstructor
public class User extends BaseEntity {

	private static final long serialVersionUID = -5021940166648386759L;
	private String userEmail;
	private String userPhoneNumber;
	private String userPassword;
	private String userLogin;
	private String userFirstName;
	private String userLastName;

	@ManyToOne(cascade = CascadeType.MERGE)
	private Role userRole;

	@OneToMany(fetch = FetchType.EAGER)
	private List<Media> medias = new ArrayList<>();

	@PrePersist
	public void prePersist() {
		this.userLogin = this.userEmail;
	}
	
	@PostConstruct
	public String getFullName() {
		return this.userFirstName + " " + this.userLastName;
	}

}
