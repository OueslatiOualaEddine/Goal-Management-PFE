package hr.maps.goalmanagment.controller;

import hr.maps.goalmanagment.dtos.PageDto;
import hr.maps.goalmanagment.dtos.UserDetails;
import hr.maps.goalmanagment.dtos.request.TeamRequest;
import hr.maps.goalmanagment.persistence.entities.Team;
import hr.maps.goalmanagment.service.TeamService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@CrossOrigin
@RequestMapping("/team")
@RequiredArgsConstructor
public class TeamController {

  private final TeamService teamService;

  @PostMapping(value="/add-new-team")
  public void saveTeam(@RequestBody TeamRequest teamRequest) {
    teamService.addOrUpdateTeam(teamRequest);
  }

  @PatchMapping(value="/add-members")
  public Team addTeamMembers(@RequestBody TeamRequest teamRequest) {
    return teamService.addMembers(teamRequest);
  }


  @GetMapping()
  public PageDto<TeamRequest> getPagedTeams(
      @RequestParam(name = "page",required = false)
          Integer page,
      @RequestParam(name = "offset",required = false)
          Integer offset) {
    return this.teamService.getPagedTeams(page,offset);
  }

  @DeleteMapping
  public void deleteTeam(@RequestParam(value = "team-uuid") UUID teamUuid){
    teamService.deleteTeam(teamUuid);
  }

  @GetMapping("/team-by-uuid")
  public TeamRequest getOffersByUuid(@RequestParam("team-uuid") UUID teamUuid) {
    return this.teamService.getTeamByUuid(teamUuid);
  }
  @GetMapping(value = "/list")
  public List<TeamRequest> getAllTeams() {
    return this.teamService.getAllTeams();
  }


}
