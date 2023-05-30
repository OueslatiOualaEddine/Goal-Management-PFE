package hr.maps.goalmanagment.service;

import hr.maps.goalmanagment.dtos.EmailDto;
import hr.maps.goalmanagment.dtos.MessageDto;
import hr.maps.goalmanagment.enumeration.EmailContext;
import hr.maps.goalmanagment.persistence.entities.User;
import hr.maps.goalmanagment.service.email.EmailService;
import hr.maps.goalmanagment.utils.Constants;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import java.util.stream.Stream;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import hr.maps.goalmanagment.dtos.KeyResultDetails;
import hr.maps.goalmanagment.dtos.PageDto;
import hr.maps.goalmanagment.dtos.TaskDto;
import hr.maps.goalmanagment.enumeration.TaskStatus;
import hr.maps.goalmanagment.mappers.TaskMapper;
import hr.maps.goalmanagment.persistence.entities.KeyResult;
import hr.maps.goalmanagment.persistence.entities.Task;
import hr.maps.goalmanagment.persistence.entities.TeamOkrKeyResult;
import hr.maps.goalmanagment.persistence.repositories.TaskRepository;
import lombok.RequiredArgsConstructor;

/**
 * @author Rokaya
 * @Date 01/05/2023
 */
@Service
@RequiredArgsConstructor
public class TaskService {

	private final TaskRepository taskRepository;
	private final TaskMapper taskMapper;

	private final UserService  userService;
	private final EmailService emailService;


	public void saveOrUpdateTask(TaskDto taskRequest, UUID teamKeyResultUuid) {
		Long taskId = null;
		if(taskRequest.getTaskUuid() != null) {
			taskId = findByUuid(taskRequest.getTaskUuid()).getId();
		}
		taskRepository.save(taskMapper.toTask(taskRequest, teamKeyResultUuid, taskId));
	}
	
	public Task findByUuid( UUID uuid) {
		return taskRepository.findByUuid(uuid);
	}

	public TaskDto findTaskByUuid( UUID uuid) {
		return taskMapper.toTaskDto(taskRepository.findByUuid(uuid));
	}
	
	public List<TaskDto> getAllTasksForAteamresultKey(UUID teamKeyResultUuid) {
		List<Task> tasks = taskRepository.findByTeamKeyResultUuid(teamKeyResultUuid);
		return tasks.stream().map(task -> taskMapper.toTaskDto(task)).collect(Collectors.toList());
	}

	public PageDto<TaskDto> getAllTasksForAteamresultKey(UUID teamKeyResult, Integer page, Integer offset) {
		 Pageable pageable = PageRequest.of(page, offset, Sort.by("createdAt").descending());
		    Page<Task> tasks =taskRepository.findByTeamKeyResultUuid(teamKeyResult,pageable);
		    return  new PageDto<TaskDto>(tasks.getContent().stream().map(taskMapper::toTaskDto).collect(Collectors
		        .toList()),tasks.getTotalElements());
	}

	public void deleteTask(UUID taskUuid) {
		Task task = findByUuid(taskUuid);
		taskRepository.delete(task);
		
	}
	public void archiveTask(UUID taskUuid) {
		Task task = findByUuid(taskUuid);
		task.setTaskStatus(TaskStatus.ARCHIVED);
		taskRepository.save(task);
		
	}
public void reportProblem(MessageDto messageDto){
		User destination=userService.getUserByUUID(messageDto.getDestinationUuid());
		User currentUser=userService.getCurrentUser();
	Map<String, Object> maps = new HashMap<>();
	maps.put("userFullName", destination.getFullName());
	maps.put("senderFullName", currentUser.getFullName());
	maps.put("messageBody", messageDto.getMessageBody());
	maps.put("taskLabel", messageDto.getTaskLabel());

	EmailDto userEmailDto = new EmailDto( Constants.MAIL_SUBJECT_REPORT_PROBLEM,"report-problem.html" ,
			maps, new HashMap<>(),EmailContext.REPORT_PROBLEM);
	emailService.sendMail(userEmailDto, Stream.of(destination.getUserEmail()).collect(Collectors.toList()));

}

}
