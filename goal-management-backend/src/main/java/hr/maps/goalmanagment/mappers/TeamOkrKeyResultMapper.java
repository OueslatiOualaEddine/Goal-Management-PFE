package hr.maps.goalmanagment.mappers;

import hr.maps.goalmanagment.dtos.TaskDto;
import hr.maps.goalmanagment.dtos.TeamOkrKeyResultDetails;
import hr.maps.goalmanagment.enumeration.TaskStatus;
import hr.maps.goalmanagment.persistence.entities.Task;
import hr.maps.goalmanagment.persistence.entities.TeamOkrKeyResult;
import hr.maps.goalmanagment.persistence.repositories.TaskRepository;
import hr.maps.goalmanagment.service.TaskService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @author Rokaya
 * @Date 01/05/2023
 */
@Component
@RequiredArgsConstructor
public class TeamOkrKeyResultMapper {
  private final TaskRepository taskRepository;
  public TeamOkrKeyResultDetails toTeamOKRDetails(TeamOkrKeyResult keyResult) {
   List<Task> taskList = taskRepository.findByTeamKeyResultUuid(keyResult.getUuid());
    return  TeamOkrKeyResultDetails.builder()
        .keyResultUuid(keyResult.getUuid())
        .keyResultLabel(keyResult.getKeyResultLabel())
        .keyResultStartValue(keyResult.getKeyResultStartValue())
        .keyResultTargetValue(keyResult.getKeyResultTargetValue())
        .keyResultType(keyResult.getKeyResultType())
        .advancement(!taskList.isEmpty()?((taskList.stream().filter(task->task.getTaskStatus()==TaskStatus.FINALIZED).collect(Collectors
            .toList()).size()*100/taskList.size())):0d)
        .build();

  }
}
