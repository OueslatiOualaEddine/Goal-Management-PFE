package hr.maps.goalmanagment.dtos;

import java.util.List;
import java.util.UUID;

import hr.maps.goalmanagment.enumeration.Priority;
import hr.maps.goalmanagment.enumeration.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskDto {
	private UUID taskUuid;
	private String taskLabel;
	private String taskDescription;
	private Priority taskPriority;
	private TaskStatus taskStatus;
	private UUID taskResponsibleUuid;
	private UUID taskCreatorUuid;
	private String responsibleFullName;
	private String creatorFullName;
	private List<CommentDto> comments;
	
}
