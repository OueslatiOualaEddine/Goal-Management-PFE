package hr.maps.goalmanagment.persistence.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hr.maps.goalmanagment.persistence.entities.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
	public List<Comment> findAllCommentsByTargetTaskUuid(UUID taskUuid);

}
