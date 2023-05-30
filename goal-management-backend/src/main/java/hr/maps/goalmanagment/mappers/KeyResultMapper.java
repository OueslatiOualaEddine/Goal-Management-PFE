package hr.maps.goalmanagment.mappers;

import hr.maps.goalmanagment.dtos.KeyResultDetails;
import hr.maps.goalmanagment.dtos.TeamOKRDetails;
import hr.maps.goalmanagment.dtos.response.KeyResultResponse;
import hr.maps.goalmanagment.enumeration.RoleCode;
import hr.maps.goalmanagment.enumeration.TaskStatus;
import hr.maps.goalmanagment.persistence.entities.KeyResult;
import hr.maps.goalmanagment.persistence.entities.Task;
import hr.maps.goalmanagment.persistence.entities.Team;
import hr.maps.goalmanagment.persistence.entities.TeamOKR;
import hr.maps.goalmanagment.persistence.entities.TeamOkrKeyResult;
import hr.maps.goalmanagment.persistence.entities.User;
import hr.maps.goalmanagment.persistence.repositories.KeyResultRepository;
import hr.maps.goalmanagment.service.TeamOKRService;
import hr.maps.goalmanagment.service.TeamOkrKeyResultService;
import hr.maps.goalmanagment.service.TeamService;
import hr.maps.goalmanagment.service.UserService;
import hr.maps.goalmanagment.service.keyResultTaskService;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author Rokaya
 * @Date 14/04/2023
 */
@RequiredArgsConstructor
@Service
public class KeyResultMapper {
  private final TeamMapper teamMapper;
  private final TeamOKRService teamOKRService;
  private final TeamService teamService;
  private final UserService userService;
  private final KeyResultRepository keyResultRepository;
  private final TeamOkrKeyResultService teamOkrKeyResultService;
  private final keyResultTaskService taskService;

  public KeyResultDetails toCompanyOKRDetails(KeyResult keyResult) {
    List<Task> teamTasks = new ArrayList<>();


    teamTasks = teamOKRService.getByKeyResultUuid(keyResult.getUuid()).stream()
        .flatMap(teamOKR -> teamOkrKeyResultService.getKeyResultsByTeamOkrUuid(teamOKR.getTeamOkrUuid()).stream())
        .flatMap(currentKeyResult -> taskService.getTaskList(currentKeyResult).stream())
        .collect(Collectors.toList());
    return  KeyResultDetails.builder()
        .keyResultUuid(keyResult.getUuid())
        .keyResultLabel(keyResult.getKeyResultLabel())
        .keyResultStartValue(keyResult.getKeyResultStartValue())
        .keyResultTargetValue(keyResult.getKeyResultTargetValue())
        .keyResultType(keyResult.getKeyResultType())
        .teamList(keyResult.getTeamList().stream().map(teamMapper::toTeamDetailsResponse).collect(Collectors.toList()))
                .teamOkrNumber(teamOKRService.getByKeyResultUuid(keyResult.getUuid()).size())
         .advancement(!teamTasks.isEmpty()?teamTasks.stream().filter(task->task.getTaskStatus()==TaskStatus.FINALIZED).count()*100/teamTasks.size():0d)

        .build();
  }
  public KeyResult toKeyResult(KeyResultDetails keyResultDetails){
    return keyResultRepository.findByUuid(keyResultDetails.getKeyResultUuid());
  }

  public KeyResultResponse toKeyResultResponse(KeyResult keyResult) {
    List<TeamOKRDetails> teamOKRList=teamOKRService.getByKeyResultUuid(keyResult.getUuid());
    User currentUser=userService.getCurrentUser();
    if(currentUser.getUserRole().getRoleCode()==RoleCode.COLLABORATOR ){
      currentUser=teamService.getTeamByMember(currentUser).getManager();
    }
    Team currentUserTeam=teamService.getTeamByManager(currentUser);
    return  KeyResultResponse.builder()
        .keyResultUuid(keyResult.getUuid())
        .keyResultLabel(keyResult.getKeyResultLabel())
        .teams((keyResult.getTeamList().stream().filter(team -> !team.getUuid().equals(currentUserTeam.getUuid())).map(teamMapper::toTeamDetailsResponse)).collect(Collectors.toList()))
        .teamOKRs(teamOKRList)
        .build();
  }
}
