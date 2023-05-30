package hr.maps.goalmanagment.service;

import hr.maps.goalmanagment.dtos.PageDto;
import hr.maps.goalmanagment.dtos.TeamOkrKeyResultDetails;
import hr.maps.goalmanagment.enumeration.KeyResultStatus;
import hr.maps.goalmanagment.mappers.TeamOkrKeyResultMapper;
import hr.maps.goalmanagment.persistence.entities.TeamOKR;
import hr.maps.goalmanagment.persistence.entities.TeamOkrKeyResult;
import hr.maps.goalmanagment.persistence.repositories.TeamOkrKeyResultRepository;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 * @author Rokaya
 * @Date 01/05/2023
 */
@Service
@RequiredArgsConstructor
public class TeamOkrKeyResultService {

  private final TeamOkrKeyResultRepository teamOkrKeyResultRepository;
  private final TeamOKRService teamOKRService;
  private final TeamOkrKeyResultMapper teamOkrKeyResultMapper;

  public PageDto<TeamOkrKeyResultDetails> getPagedKeyResultByTeamOkrUuid(UUID teamOkrUuid,int page,int offset){
    Pageable pageable = PageRequest.of(page, offset, Sort.by("createdAt").ascending());
    Page<TeamOkrKeyResult> keyResults =teamOkrKeyResultRepository.findTeamOkrKeyResultByTeamOKRUuid(teamOkrUuid,pageable);
    return  new PageDto<TeamOkrKeyResultDetails>(keyResults.getContent().stream().map(teamOkrKeyResultMapper::toTeamOKRDetails).collect(Collectors
        .toList()),keyResults.getTotalElements());
  }

  public List<TeamOkrKeyResult> getKeyResultsByTeamOkrUuid(UUID teamOkrUuid){
    return teamOkrKeyResultRepository.findTeamOkrKeyResultByTeamOKRUuid(teamOkrUuid);

  }
  public void saveKeyResult(TeamOkrKeyResultDetails keyResultRequest) {
    TeamOkrKeyResult keyResult;
    TeamOKR teamOKR=teamOKRService.getTeamOKRByUUID(keyResultRequest.getTeamOKRUuid());
    if (keyResultRequest.getKeyResultUuid()==null) {
      keyResult = new TeamOkrKeyResult();
      keyResult.setKeyResultLabel(keyResultRequest.getKeyResultLabel());
      keyResult.setKeyResultStartValue(keyResultRequest.getKeyResultStartValue());
      keyResult.setKeyResultTargetValue(keyResultRequest.getKeyResultTargetValue());
      keyResult.setKeyResultType(keyResultRequest.getKeyResultType());
      keyResult.setTeamOKR(teamOKR);
      keyResult.setKeyResultStatus(KeyResultStatus.IN_PROGRESS);

    }else{
      keyResult=teamOkrKeyResultRepository.findByUuid(keyResultRequest.getKeyResultUuid());
      keyResult.setKeyResultLabel(keyResultRequest.getKeyResultLabel());
      keyResult.setKeyResultStartValue(keyResultRequest.getKeyResultStartValue());
      keyResult.setKeyResultTargetValue(keyResultRequest.getKeyResultTargetValue());
      keyResult.setKeyResultType(keyResultRequest.getKeyResultType());


    }
    teamOkrKeyResultRepository.save(keyResult);

  }
  public TeamOkrKeyResult getKeyResultByUUID(UUID keyResultUuid) {
    return this.teamOkrKeyResultRepository.findByUuid(keyResultUuid);
  }


  public TeamOkrKeyResultDetails getByUuid(UUID keyResultUuid){
    return teamOkrKeyResultMapper.toTeamOKRDetails(getKeyResultByUUID(keyResultUuid));
  }
  public void deleteTeamOKR(UUID keyResultUuid) {
    TeamOkrKeyResult  keyResult = getKeyResultByUUID(keyResultUuid);
    this.teamOkrKeyResultRepository.deleteById(keyResult.getId());
  }

  public void archiveTeamOKR(UUID keyResultUuid) {
    TeamOkrKeyResult  keyResult = getKeyResultByUUID(keyResultUuid);
    keyResult.setKeyResultStatus(KeyResultStatus.ARCHIVED);
    this.teamOkrKeyResultRepository.save(keyResult);
  }
/*  public PageDto<TeamOkrKeyResultDetails> getPagedKeyResultByTeamOkrUuid(UUID teamOkrUuid,int page,int offset){
    Pageable pageable = PageRequest.of(page, offset, Sort.by("createdAt").ascending());
    Page<TeamOkrKeyResult> keyResults =teamOkrKeyResultRepository.findTeamOkrKeyResultByTeamOKRUuid(teamOkrUuid,pageable);
    return  new PageDto<TeamOkrKeyResultDetails>(keyResults.getContent().stream().map(teamOkrKeyResultMapper::toTeamOKRDetails).collect(Collectors
        .toList()),keyResults.getTotalElements());
  }*/
}
