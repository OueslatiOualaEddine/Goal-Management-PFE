package hr.maps.goalmanagment.persistence.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import hr.maps.goalmanagment.enumeration.Priority;
import hr.maps.goalmanagment.enumeration.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Rokaya
 * @Date 24/04/2023
 */
@Entity
@Table(name = "gm_task")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Task extends BaseEntity {
	private String taskLabel;
	@Column(columnDefinition = "TEXT")
	private String taskDescription;
	@Enumerated(EnumType.STRING)
	private Priority taskPriority;
	@Enumerated(EnumType.STRING)
	private TaskStatus taskStatus;
	@ManyToOne
	private TeamOkrKeyResult teamKeyResult;
	@ManyToOne
	private User taskCreator;
	@ManyToOne
	private User taskResponsible;
	
	@PrePersist
	public void prepersist() {
		this.taskStatus = TaskStatus.TO_DO;
	}
}
