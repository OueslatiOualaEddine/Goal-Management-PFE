package hr.maps.goalmanagment.service;

import hr.maps.goalmanagment.dtos.TaskDto;
import hr.maps.goalmanagment.persistence.entities.KeyResult;
import hr.maps.goalmanagment.persistence.entities.Task;
import hr.maps.goalmanagment.persistence.entities.TeamOkrKeyResult;
import hr.maps.goalmanagment.persistence.repositories.TaskRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author Rokaya
 * @Date 09/05/2023
 */
@Service
@RequiredArgsConstructor
public class keyResultTaskService {

  private final TaskRepository taskRepository;
  public List<Task> getTaskList(TeamOkrKeyResult keyResult){
    return taskRepository.findByTeamKeyResultUuid(keyResult.getUuid());

  }


}
