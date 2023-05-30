package hr.maps.goalmanagment.dtos;

import hr.maps.goalmanagment.enumeration.Priority;
import hr.maps.goalmanagment.enumeration.TaskStatus;
import hr.maps.goalmanagment.persistence.entities.TeamOkrKeyResult;
import hr.maps.goalmanagment.persistence.entities.User;

/**
 * @author Rokaya
 * @Date 05/05/2023
 */
public class TaskDetails {
  private String taskLabel;
  private String taskDescription;
  private Priority taskPriority;
  private TaskStatus taskStatus;
  private UserDetails taskCreator;
  private UserDetails taskResponsible;
}
