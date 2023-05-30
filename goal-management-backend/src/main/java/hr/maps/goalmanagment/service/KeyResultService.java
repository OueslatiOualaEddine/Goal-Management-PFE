package hr.maps.goalmanagment.service;

import hr.maps.goalmanagment.dtos.KeyResultDetails;
import hr.maps.goalmanagment.dtos.PageDto;
import hr.maps.goalmanagment.dtos.response.CompanyOKRResponse;
import hr.maps.goalmanagment.dtos.response.KeyResultResponse;
import hr.maps.goalmanagment.enumeration.KeyResultStatus;
import hr.maps.goalmanagment.enumeration.RoleCode;
import hr.maps.goalmanagment.mappers.CompanyOkrMapper;
import hr.maps.goalmanagment.mappers.KeyResultMapper;
import hr.maps.goalmanagment.mappers.TeamMapper;
import hr.maps.goalmanagment.persistence.entities.CompanyOKR;
import hr.maps.goalmanagment.persistence.entities.KeyResult;
import hr.maps.goalmanagment.persistence.entities.Team;
import hr.maps.goalmanagment.persistence.entities.User;
import hr.maps.goalmanagment.persistence.repositories.KeyResultRepository;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 * @author Rokaya
 * @Date 14/04/2023
 */
@Service
@RequiredArgsConstructor
public class KeyResultService {
  private final CompanyOKRService companyOKRService;
  private final KeyResultRepository keyResultRepository;
  private final KeyResultMapper keyResultMapper;
  private final TeamMapper teamMapper;
  private final UserService userService;
  private final CompanyOkrMapper companyOkrMapper;
  private final TeamService teamService;





  public PageDto<KeyResultDetails> getPagedKeyResultByCompanyOkrUuid(UUID companyOkrUuid,int page,int offset){
    Pageable pageable = PageRequest.of(page, offset, Sort.by("createdAt").ascending());
    Page<KeyResult> keyResults =keyResultRepository.findKeyResultByCompanyOKRUuid(companyOkrUuid,pageable);
    return  new PageDto<KeyResultDetails>(keyResults.getContent().stream().map(keyResultMapper::toCompanyOKRDetails).collect(Collectors
        .toList()),keyResults.getTotalElements());
  }

  public List<KeyResult> getKeyResultByCompanyOkrUuid(UUID companyOkrUuid){
  return keyResultRepository.findKeyResultByCompanyOKRUuid(companyOkrUuid);
  }

  public void saveKeyResult(KeyResultDetails keyResultRequest) {
    KeyResult keyResult;
    CompanyOKR companyOKR=companyOKRService.getCompanyOKRByUUID(keyResultRequest.getCompanyOKRUuid());
    if (keyResultRequest.getKeyResultUuid()==null) {
      keyResult = new KeyResult();
      keyResult.setKeyResultLabel(keyResultRequest.getKeyResultLabel());
      keyResult.setKeyResultStartValue(keyResultRequest.getKeyResultStartValue());
      keyResult.setKeyResultTargetValue(keyResultRequest.getKeyResultTargetValue());
      keyResult.setKeyResultType(keyResultRequest.getKeyResultType());
      keyResult.setTeamList(keyResultRequest.getTeamList().stream().map(teamMapper::toTeam).collect(Collectors
          .toList()));
      keyResult.setCompanyOKR(companyOKR);
      keyResult.setKeyResultStatus(KeyResultStatus.IN_PROGRESS);

    }else{
      keyResult=keyResultRepository.findByUuid(keyResultRequest.getKeyResultUuid());
      keyResult.setKeyResultLabel(keyResultRequest.getKeyResultLabel());
      keyResult.setKeyResultStartValue(keyResultRequest.getKeyResultStartValue());
      keyResult.setKeyResultTargetValue(keyResultRequest.getKeyResultTargetValue());
      keyResult.setKeyResultType(keyResultRequest.getKeyResultType());
       keyResult.setTeamList(keyResultRequest.getTeamList().stream().map(teamMapper::toTeam).collect(Collectors
           .toList()));

    }
    keyResultRepository.save(keyResult);

  }
  public KeyResult getKeyResultByUUID(UUID keyResultUuid) {
    return this.keyResultRepository.findByUuid(keyResultUuid);
  }
  public void deleteCompanyOKR(UUID keyResultUuid) {
    KeyResult  keyResult = getKeyResultByUUID(keyResultUuid);
    this.keyResultRepository.deleteById(keyResult.getId());
  }

  public void archiveCompanyOKR(UUID keyResultUuid) {
    KeyResult  keyResult = getKeyResultByUUID(keyResultUuid);
    keyResult.setKeyResultStatus(KeyResultStatus.ARCHIVED);
    this.keyResultRepository.save(keyResult);
  }

  public List<CompanyOKRResponse> getKeyResultByManager() {
    User user=userService.getCurrentUser();
    if(user.getUserRole().getRoleCode()==RoleCode.COLLABORATOR ) {
      Team team = teamService.getTeamByMember(user);
      if (team != null) {
        user = teamService.getTeamByMember(user).getManager();
      } else {
        return Collections.emptyList();
      }


    }
    return companyOkrMapper.toCompanyOKRResponse(
        user != null ? this.keyResultRepository.findByTeamListManager(user) : null);

    //return companyOkrMapper.toCompanyOKRResponse(user!=null?this.keyResultRepository.findByTeamListManager(user):null);
  }


  public KeyResultResponse getKeyResultByUuid(UUID keyResultUuid){
    return keyResultMapper.toKeyResultResponse(keyResultRepository.findByUuid(keyResultUuid));
  }

  public PageDto<KeyResultDetails> getKeyResultByManagerTeam(int page,int offset) {
    User user=userService.getCurrentUser();
    if(user.getUserRole().getRoleCode()==RoleCode.COLLABORATOR ){
      user=teamService.getTeamByMember(user).getManager();
    }
    Pageable pageable = PageRequest.of(page, offset, Sort.by("createdAt").ascending());
    Page<KeyResult> keyResults =this.keyResultRepository.findByTeamListManager(user,pageable);
    return  new PageDto<KeyResultDetails>(keyResults.getContent().stream().map(keyResultMapper::toCompanyOKRDetails).collect(Collectors
        .toList()),keyResults.getTotalElements());
        //companyOkrMapper.toCompanyOKRResponse(this.keyResultRepository.findByTeamListManager(user));
  }
}
