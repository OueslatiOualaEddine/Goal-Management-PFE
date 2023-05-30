package hr.maps.goalmanagment.service;

import hr.maps.goalmanagment.dtos.PageDto;
import hr.maps.goalmanagment.dtos.TeamOKRDetails;
import hr.maps.goalmanagment.enumeration.OkrStatus;
import hr.maps.goalmanagment.mappers.TeamOkrMapper;
import hr.maps.goalmanagment.persistence.entities.KeyResult;
import hr.maps.goalmanagment.persistence.entities.Team;
import hr.maps.goalmanagment.persistence.entities.TeamOKR;
import hr.maps.goalmanagment.persistence.entities.TeamOkrKeyResult;
import hr.maps.goalmanagment.persistence.entities.User;
import hr.maps.goalmanagment.persistence.repositories.KeyResultRepository;
import hr.maps.goalmanagment.persistence.repositories.TeamOKRRepository;
import hr.maps.goalmanagment.persistence.repositories.TeamOkrKeyResultRepository;
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
 * @Date 25/04/2023
 */
@Service
@RequiredArgsConstructor
public class TeamOKRService {
  private final TeamOKRRepository teamOKRRepository;
  private final TeamOkrKeyResultRepository teamOkrKeyResultRepository;
  private final TeamOkrMapper teamOkrMapper;
  private final KeyResultRepository keyResultRepository;
  private final TeamService teamService;
  private final UserService userService;


  public PageDto<TeamOKRDetails> getByKeyResultUuid(UUID uuid,int page,int offset){
    List<TeamOkrKeyResult> keyResults=teamOkrKeyResultRepository.findTeamOkrKeyResultByTeamOKRUuid(uuid);
    Pageable pageable = PageRequest.of(page, offset, Sort.by("createdAt").ascending());
    Page<TeamOKR> teamOKRS =teamOKRRepository.findTeamOKRByKeyResultUuid(uuid,pageable);
    return new PageDto<TeamOKRDetails>(teamOKRS.getContent().stream().map(teamOkr->teamOkrMapper.toTeamOKRDetails(teamOkr,teamOkrKeyResultRepository.findTeamOkrKeyResultByTeamOKRUuid(teamOkr.getUuid()))).collect(Collectors.toList()),teamOKRS.getTotalElements());
  }

  public List<TeamOKRDetails> getByKeyResultUuid(UUID uuid){
    //List<TeamOkrKeyResult> keyResults=teamOkrKeyResultRepository.findTeamOkrKeyResultByTeamOKRUuid(uuid);
    return teamOKRRepository.findTeamOKRByKeyResultUuid(uuid).stream().map(teamOkr->teamOkrMapper.toTeamOKRDetails(teamOkr,teamOkrKeyResultRepository.findTeamOkrKeyResultByTeamOKRUuid(teamOkr.getUuid()))).collect(Collectors.toList());
  }

  public void saveTeamOKR(TeamOKRDetails teamOKRRequest) {
    KeyResult keyResult=keyResultRepository.findByUuid(teamOKRRequest.getKeyResultUuid());
    TeamOKR teamOKR;
    if (teamOKRRequest.getTeamOkrUuid()==null) {
      User user=userService.getCurrentUser();

      teamOKR = new TeamOKR();
      teamOKR.setTeamOkrDescription(teamOKRRequest.getTeamOkrDescription());
      teamOKR.setTeamOkrLabel(teamOKRRequest.getTeamOkrLabel());
      teamOKR.setStartDate(teamOKRRequest.getStartDate());
      teamOKR.setEndDate(teamOKRRequest.getEndDate());
      teamOKR.setTeamOkrStatus(OkrStatus.IN_PROGRESS);
      teamOKR.setKeyResult(keyResult);
      teamOKR.setTeam(teamService.getTeamByManager(user));
      teamOKR.setPartnerTeamOKR(teamOKRRequest.getPartnerTeamOKRDetails().stream().map(teamOkrMapper::toTeamOkr).collect(Collectors
          .toList()));

    }else{
      teamOKR=teamOKRRepository.findByUuid(teamOKRRequest.getTeamOkrUuid()) ;
      teamOKR.setTeamOkrDescription(teamOKRRequest.getTeamOkrDescription());
      teamOKR.setTeamOkrLabel(teamOKRRequest.getTeamOkrLabel());
      teamOKR.setStartDate(teamOKRRequest.getStartDate());
      teamOKR.setEndDate(teamOKRRequest.getEndDate());
      if(!teamOKRRequest.getPartnerTeamOKRDetails().isEmpty()){
        teamOKR.getPartnerTeamOKR().removeAll(teamOKR.getPartnerTeamOKR());
        teamOKR.setPartnerTeamOKR(teamOKRRequest.getPartnerTeamOKRDetails().stream().map(teamOkrMapper::toTeamOkr).collect(Collectors
            .toList()));
      }


    }
    teamOKRRepository.save(teamOKR);

  }
  public void deleteTeamOKR(UUID teamOkrUuid) {
    TeamOKR  teamOKR = teamOKRRepository.findByUuid(teamOkrUuid);
    teamOKRRepository.deleteById(teamOKR.getId());
  }

  public void archiveTeamOKR(UUID teamOkrUuid) {
    TeamOKR  teamOKR = teamOKRRepository.findByUuid(teamOkrUuid);
    teamOKR.setTeamOkrStatus(OkrStatus.ARCHIVED);
    this.teamOKRRepository.save(teamOKR);
  }

  public List<TeamOKRDetails> findByKeyResultAndTeam(UUID keyResultUuid,UUID teamUuid){
    KeyResult keyResult=keyResultRepository.findByUuid(keyResultUuid);
    Team team=teamService.findTeamByUuid(teamUuid);
    return teamOKRRepository.findByKeyResultAndTeam(keyResult,team).stream().map(teamOkr->teamOkrMapper.toTeamOKRDetails(teamOkr,null)).collect(Collectors.toList());
  }

  public TeamOKR getTeamOKRByUUID(UUID teamOkrUuid) {
    return this.teamOKRRepository.findByUuid(teamOkrUuid);
  }
  public TeamOKRDetails findTeamOKRByUUID(UUID teamOkrUuid) {
    return teamOkrMapper.toTeamOKRDetails(getTeamOKRByUUID(teamOkrUuid),null);
  }
}
