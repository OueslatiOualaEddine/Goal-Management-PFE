package hr.maps.goalmanagment.controller;

import hr.maps.goalmanagment.dtos.MessageDto;
import java.util.UUID;

import javax.websocket.server.PathParam;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import hr.maps.goalmanagment.dtos.PageDto;
import hr.maps.goalmanagment.dtos.TaskDto;
import hr.maps.goalmanagment.service.TaskService;
import lombok.RequiredArgsConstructor;

/**
 * @author Rokaya
 * @Date 14/04/2023
 */
@RestController
@RequestMapping("/task")
@RequiredArgsConstructor
@CrossOrigin
public class TaskController {
	private final TaskService taskService;

	@PostMapping
	public void saveOrUpdate(@RequestBody TaskDto taskRequest, @RequestParam(value = "team-key-result",required = false) UUID teamKeyResult) {
		this.taskService.saveOrUpdateTask(taskRequest, teamKeyResult);
	}

	/*@GetMapping
	public List<TaskDto> getTasks(@RequestParam("team-key-result") UUID teamKeyResult) {
		return taskService.getAllTasksForAteamresultKey(teamKeyResult);
	}*/
	
	 @GetMapping(value = "list")
	  public PageDto<TaskDto> getPagedTeams(
	      @RequestParam(name = "team-key-result")
	          UUID teamKeyResult,
	      @RequestParam(name = "page",required = false)
	          Integer page,
	      @RequestParam(name = "offset",required = false)
	          Integer offset) {
	    return this.taskService.getAllTasksForAteamresultKey(teamKeyResult,page,offset);
	  }
	 
	 @DeleteMapping("/{taskUuid}")
	 public void deleteTask(@PathVariable("taskUuid") UUID taskUuid) {
		 this.taskService.deleteTask(taskUuid);
	 }
	 
	 @PutMapping("/archive/{taskUuid}")
	 public void archiveTask(@PathVariable("taskUuid") UUID taskUuid) {
		 this.taskService.archiveTask(taskUuid);
	 }

	 	@GetMapping(value="task-details")
	public TaskDto getTaskByUuid(@RequestParam("task-uuid") UUID taskUuid) {
		return taskService.findTaskByUuid(taskUuid);
	}

	@PostMapping(value="report-problem")
	public void reportProblem(@RequestBody MessageDto messageDto){
	 	this.taskService.reportProblem(messageDto);
	}
}
