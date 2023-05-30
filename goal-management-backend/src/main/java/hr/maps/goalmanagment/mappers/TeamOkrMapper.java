package hr.maps.goalmanagment.mappers;

import hr.maps.goalmanagment.dtos.TaskDto;
import hr.maps.goalmanagment.dtos.TeamOKRDetails;
import hr.maps.goalmanagment.enumeration.TaskStatus;
import hr.maps.goalmanagment.persistence.entities.KeyResult;
import hr.maps.goalmanagment.persistence.entities.Task;
import hr.maps.goalmanagment.persistence.entities.TeamOKR;
import hr.maps.goalmanagment.persistence.entities.TeamOkrKeyResult;
import hr.maps.goalmanagment.persistence.repositories.TeamOKRRepository;
import hr.maps.goalmanagment.service.TaskService;
import hr.maps.goalmanagment.service.TeamOkrKeyResultService;
import hr.maps.goalmanagment.service.keyResultTaskService;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @author Rokaya
 * @Date 25/04/2023
 */
@Component
@RequiredArgsConstructor
public class TeamOkrMapper {
  private final TeamOKRRepository teamOKRRepository;
  private final keyResultTaskService taskService;
  public TeamOKRDetails toTeamOKRDetails(TeamOKR team,List<TeamOkrKeyResult> keyResults) {
    List<Task> teamTasks = new ArrayList<>();
   if(keyResults!=null) {
     keyResults.forEach(keyResult -> {
       List<Task> keyResultTasks = taskService.getTaskList(keyResult);
       teamTasks.addAll(keyResultTasks);
     });
   }
    return  TeamOKRDetails.builder().teamOkrUuid(team.getUuid()).teamOkrLabel(team.getTeamOkrLabel()).
    teamOkrDescription(team.getTeamOkrDescription()).
    startDate(team.getStartDate()).
    endDate(team.getEndDate()).teamOkrStatus(team.getTeamOkrStatus()).keyResultUuid(team.getKeyResult().getUuid()).
       partnerTeamOKRDetails(!team.getPartnerTeamOKR().isEmpty()?team.getPartnerTeamOKR().stream().map(this::toTeamOKRDetails11).collect(Collectors.toList()):null)
       .advancement(!teamTasks.isEmpty()?teamTasks.stream().filter(task->task.getTaskStatus()==TaskStatus.FINALIZED).count()*100/teamTasks.size():0d)
    .build();
  }

  public TeamOKRDetails toTeamOKRDetails11(TeamOKR team) {
    return new TeamOKRDetails(team.getUuid(),
        team.getTeamOkrLabel(),
        team.getTeamOkrDescription(),
        team.getStartDate(),
        team.getEndDate(),
        team.getTeamOkrStatus(),
        team.getKeyResult().getUuid(),null,0d);
        //!team.getPartnerTeamOKR().isEmpty()?team.getPartnerTeamOKR().stream().map(this::toTeamOKRDetails).collect(Collectors.toList()):null) ;

  }

 /* public TeamOKRDetails toTeamOKRDetails11(TeamOKR team) {
    return  TeamOKRDetails.builder().teamOkrUuid(team.getUuid()).teamOkrLabel(team.getTeamOkrLabel()).
        teamOkrDescription(team.getTeamOkrDescription()).
        startDate(team.getStartDate()).
        endDate(team.getEndDate()).teamOkrStatus(team.getTeamOkrStatus()).keyResultUuid(team.getKeyResult().getUuid()).
        partnerTeamOKRDetails(team.getTeamOKRList().stream().map(this::toTeamOKRDetails).collect(Collectors.toList())).build();
  }*/

  public TeamOKR toTeamOkr(TeamOKRDetails teamOKRDetails){
    return teamOKRRepository.findByUuid(teamOKRDetails.getTeamOkrUuid());
  }
}
