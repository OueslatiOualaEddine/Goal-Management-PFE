package hr.maps.goalmanagment.mappers;

import hr.maps.goalmanagment.dtos.CompanyOKRDetails;
import hr.maps.goalmanagment.dtos.TeamOKRDetails;
import hr.maps.goalmanagment.dtos.response.CompanyOKRResponse;
import hr.maps.goalmanagment.enumeration.TaskStatus;
import hr.maps.goalmanagment.persistence.entities.CompanyOKR;
import hr.maps.goalmanagment.persistence.entities.KeyResult;
import hr.maps.goalmanagment.persistence.entities.Task;
import hr.maps.goalmanagment.persistence.entities.TeamOkrKeyResult;
import hr.maps.goalmanagment.persistence.repositories.KeyResultRepository;
import hr.maps.goalmanagment.service.KeyResultService;
import hr.maps.goalmanagment.service.TeamOKRService;
import hr.maps.goalmanagment.service.TeamOkrKeyResultService;
import hr.maps.goalmanagment.service.keyResultTaskService;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @author Rokaya
 * @Date 09/04/2023
 */
@Component
@RequiredArgsConstructor
public class CompanyOkrMapper {

  private final KeyResultRepository keyResultService;
  private final TeamOKRService teamOKRService;
  private final TeamOkrKeyResultService teamOkrKeyResultService;
  private final keyResultTaskService taskService;


  public CompanyOKRDetails toCompanyOKRDetails(CompanyOKR companyOKR) {
    List<KeyResult> keyResults = keyResultService.findKeyResultByCompanyOKRUuid(companyOKR.getUuid());
    List<TeamOKRDetails> teamOKRDetails = keyResults.stream()
        .map(KeyResult::getUuid)
        .flatMap(uuid -> teamOKRService.getByKeyResultUuid(uuid).stream())
        .collect(Collectors.toList());
    List<TeamOkrKeyResult> teamOkrKeyResults = teamOKRDetails.stream()
        .map(TeamOKRDetails::getTeamOkrUuid)
        .flatMap(uuid -> teamOkrKeyResultService.getKeyResultsByTeamOkrUuid(uuid).stream())
        .collect(Collectors.toList());
    List<Task> teamTasks = teamOkrKeyResults.parallelStream()
        .flatMap(keyResult -> taskService.getTaskList(keyResult).stream())
        .collect(Collectors.toList());
    return  CompanyOKRDetails.builder().campanyOkrUuid(companyOKR.getUuid())
        .campanyOkrName(companyOKR.getCampanyOkrName()).campanyOkrDescription(companyOKR.getCampanyOkrDescription())
        .startDate(companyOKR.getStartDate()).endDate(companyOKR.getEndDate())
        .companyOkrStatus(companyOKR.getCompanyOkrStatus())
         .advancement(!teamTasks.isEmpty()?teamTasks.stream().filter(task->task.getTaskStatus()==TaskStatus.FINALIZED).count()*100/teamTasks.size():0d)
        .build();

  }

  public List<CompanyOKRResponse> toCompanyOKRResponse(List<KeyResult> keyResults) {

   return keyResults!=null? keyResults.stream()
        .map(KeyResult::getCompanyOKR)
        .collect(Collectors.groupingBy(
            CompanyOKR::getUuid,
            Collectors.collectingAndThen(
                Collectors.toList(),
                companyOKRs -> new CompanyOKRResponse(
                    companyOKRs.get(0).getUuid(),
                    companyOKRs.get(0).getCampanyOkrName()
                    /*keyResults.stream()
                    .map(keyResultMapper::toKeyResultResponse)
                    .collect(Collectors.toList())*/
                )
            )
        ))
        .values()
        .stream()
        .collect(Collectors.toList()):null;
  }

}
