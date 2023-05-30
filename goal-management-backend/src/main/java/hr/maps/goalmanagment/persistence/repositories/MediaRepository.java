package hr.maps.goalmanagment.persistence.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hr.maps.goalmanagment.persistence.entities.Media;

@Repository
public interface MediaRepository extends JpaRepository<Media, Long> {
  Media findByUuid(UUID uuid);
}
