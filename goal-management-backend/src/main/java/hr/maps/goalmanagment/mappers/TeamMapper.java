package hr.maps.goalmanagment.mappers;

import hr.maps.goalmanagment.dtos.request.TeamRequest;
import hr.maps.goalmanagment.persistence.entities.Team;
import hr.maps.goalmanagment.persistence.repositories.TeamRepository;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class TeamMapper {
  private final UserMapper userMapper;
  private final TeamRepository teamRepository;
  public TeamRequest toTeamDetailsResponse(Team team) {
    return new TeamRequest(team.getUuid(),team.getTeamName(),team.getMembers().stream().map(userMapper::toUserDetailsResponse).collect(Collectors.toList()),
        userMapper.toUserDetailsResponse(team.getManager()),0d
        );
  }

  public Team toTeam(TeamRequest teamRequest){
    return teamRepository.findByUuid(teamRequest.getTeamUuid());
  }
}
