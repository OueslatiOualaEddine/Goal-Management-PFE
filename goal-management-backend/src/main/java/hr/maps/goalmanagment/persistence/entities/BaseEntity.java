package hr.maps.goalmanagment.persistence.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import org.hibernate.annotations.Type;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import hr.maps.goalmanagment.utils.Constants;
import lombok.Data;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Data
public class BaseEntity implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = Constants.DEFAULT_TIMEZONE)
	@Column(nullable = false, updatable = false)
	@CreatedDate
	protected Date createdAt;

	/**
	 * The updated at.
	 */
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = Constants.DEFAULT_TIMEZONE)
	@Column(nullable = false)
	@LastModifiedDate
	protected Date updatedAt;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonIgnore
	private Long id;

	@Type(type = "org.hibernate.type.UUIDCharType")
	private UUID uuid;

	@PrePersist
	private void persistCreatedDate() {
		this.uuid = UUID.randomUUID();
		if (this.createdAt == null) {
			this.createdAt = new Date();
		}
		this.updatedAt = new Date();
	}

	@PreUpdate
	private void persistUpdatedDate() {
		this.updatedAt = new Date();
	}
}
