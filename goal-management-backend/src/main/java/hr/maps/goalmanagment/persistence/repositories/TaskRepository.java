package hr.maps.goalmanagment.persistence.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import hr.maps.goalmanagment.persistence.entities.Task;

/**
 * @author Rokaya
 * @Date 25/04/2023
 */
public interface TaskRepository extends JpaRepository<Task, Long> {
	public Task findByUuid(UUID uuid);
	public Page<Task> findByTeamKeyResultUuid(UUID uuid,Pageable pageable);
	public List<Task> findByTeamKeyResultUuid(UUID uuid);

}
