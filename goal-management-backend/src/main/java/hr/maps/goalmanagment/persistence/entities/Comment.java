package hr.maps.goalmanagment.persistence.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Rokaya
 * @Date 24/04/2023
 */
@Entity
@Table(name = "gm_comment")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment extends BaseEntity {

	@Column(columnDefinition = "TEXT")
	private String commentText;
	@ManyToOne
	private Task targetTask;
	@ManyToOne
	private User commentWriter;
}
