package hr.maps.goalmanagment.controller;

import hr.maps.goalmanagment.dtos.PageDto;
import hr.maps.goalmanagment.dtos.TeamOKRDetails;
import hr.maps.goalmanagment.service.TeamOKRService;
import java.util.List;
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
 * @Date 27/04/2023
 */
@RestController
@RequestMapping("team-okr")
@RequiredArgsConstructor
@CrossOrigin
public class TeamOkrController {

  private final TeamOKRService teamOKRService;

  @PostMapping(value = "add")
  public void saveOrUpdate(@RequestBody TeamOKRDetails companyOKRDetails) {
    this.teamOKRService.saveTeamOKR(companyOKRDetails);
  }

  @DeleteMapping
  public void deleteTeamOkr(@RequestParam(value = "team-okr-uuid") UUID teamOkrUuid){
    teamOKRService.deleteTeamOKR(teamOkrUuid);
  }

  @GetMapping(value = "archive")
  public void archiveTeamOkr(@RequestParam(value = "team-okr-uuid") UUID teamOkrUuid){
    teamOKRService.archiveTeamOKR(teamOkrUuid);
  }

  @GetMapping(value = "by-key-result")
  public PageDto<TeamOKRDetails> getByKeyResult(@RequestParam(value = "key-result-uuid") UUID keyResultUuid,
      @RequestParam(name = "page",required = false)
      Integer page,
      @RequestParam(name = "offset",required = false)
      Integer offset) {
    return teamOKRService.getByKeyResultUuid(keyResultUuid,page,offset);
  }

  @GetMapping
  public List<TeamOKRDetails> getByKeyResultAndTeam(@RequestParam(value = "key-result-uuid") UUID keyResultUuid,@RequestParam(value = "team-uuid") UUID teamUuid){
    return teamOKRService.findByKeyResultAndTeam(keyResultUuid,teamUuid);
  }
  @GetMapping("/team-okr-by-uuid")
  public TeamOKRDetails getTeamOkrByUuid(@RequestParam("team-okr-uuid") UUID teamOkrUuid) {
    return this.teamOKRService.findTeamOKRByUUID(teamOkrUuid);
  }
}
