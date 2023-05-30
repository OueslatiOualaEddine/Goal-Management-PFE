package hr.maps.goalmanagment.mappers;

import java.util.UUID;

import org.springframework.stereotype.Component;

import hr.maps.goalmanagment.dtos.TaskDto;
import hr.maps.goalmanagment.persistence.entities.Task;
import hr.maps.goalmanagment.service.CommentService;
import hr.maps.goalmanagment.service.TeamOkrKeyResultService;
import hr.maps.goalmanagment.service.UserService;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TaskMapper {

	private final UserService userService;
	private final TeamOkrKeyResultService teamKeyResultService;
	private final CommentService commentService;

	public Task toTask(TaskDto taskRequest, UUID teamKeyResultUuid, Long taskId) {
		Task task =  taskRequest != null ? new Task(taskRequest.getTaskLabel(),
				taskRequest.getTaskDescription(),
				taskRequest.getTaskPriority(),
				taskRequest.getTaskStatus(),
				teamKeyResultService.getKeyResultByUUID(teamKeyResultUuid),
				taskRequest.getTaskCreatorUuid() != null ? userService.getUserByUUID(taskRequest.getTaskCreatorUuid()): userService.getCurrentUser(),
				taskRequest.getTaskResponsibleUuid() != null
						? userService.getUserByUUID(taskRequest.getTaskResponsibleUuid())
						: null)
				: null;
		if(taskRequest.getTaskUuid() != null) { task.setId(taskId);
		task.setUuid(taskRequest.getTaskUuid());}
		return task;
	}

	public TaskDto toTaskDto(Task task) {
		return task != null ? new TaskDto(task.getUuid(),task.getTaskLabel(), task.getTaskDescription(),
				task.getTaskPriority(),
				task.getTaskStatus(),
				task.getTaskResponsible().getUuid(),
				task.getTaskCreator().getUuid(),
				task.getTaskResponsible().getFullName(),
				task.getTaskCreator().getFullName(),
				commentService.findAllComments(task.getUuid()))
				: null;
	}
}
