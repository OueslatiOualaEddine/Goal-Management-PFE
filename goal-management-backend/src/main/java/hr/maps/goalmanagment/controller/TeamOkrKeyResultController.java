package hr.maps.goalmanagment.controller;

import hr.maps.goalmanagment.dtos.PageDto;
import hr.maps.goalmanagment.dtos.TeamOkrKeyResultDetails;
import hr.maps.goalmanagment.service.TeamOkrKeyResultService;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Rokaya
 * @Date 01/05/2023
 */
@RestController
@RequestMapping("team-okr-key-result")
@RequiredArgsConstructor
@CrossOrigin
public class TeamOkrKeyResultController {
  private final TeamOkrKeyResultService teamOkrKeyResultService;

  @PostMapping
  public void saveOrUpdate(@RequestBody TeamOkrKeyResultDetails keyResultDetails) {
    this.teamOkrKeyResultService.saveKeyResult(keyResultDetails);
  }


  @GetMapping()
  public PageDto<TeamOkrKeyResultDetails> getPagedTeams(
      @RequestParam(name = "team-okr-uuid")
          UUID companyOkrUuid,
      @RequestParam(name = "page",required = false)
          Integer page,
      @RequestParam(name = "offset",required = false)
          Integer offset) {
    return this.teamOkrKeyResultService.getPagedKeyResultByTeamOkrUuid(companyOkrUuid,page,offset);
  }
  @DeleteMapping
  public void deleteTeamOkr(@RequestParam(value = "key-result-uuid") UUID companyOkrUuid){
    teamOkrKeyResultService.deleteTeamOKR(companyOkrUuid);
  }

  @GetMapping(value = "archive")
  public void archiveTeamOkr(@RequestParam(value = "key-result-uuid") UUID companyOkrUuid){
    teamOkrKeyResultService.archiveTeamOKR(companyOkrUuid);
  }
  @GetMapping(value="by-uuid")
  public TeamOkrKeyResultDetails getKeyResultByUuid(@RequestParam(value = "key-result-uuid") UUID keyResultUuid){
    return teamOkrKeyResultService.getByUuid(keyResultUuid);
  }
}
