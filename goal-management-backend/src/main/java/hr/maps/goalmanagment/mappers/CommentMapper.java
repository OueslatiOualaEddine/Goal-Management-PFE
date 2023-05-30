package hr.maps.goalmanagment.mappers;

import org.springframework.stereotype.Component;

import hr.maps.goalmanagment.dtos.CommentDto;
import hr.maps.goalmanagment.persistence.entities.Comment;
import hr.maps.goalmanagment.persistence.entities.Task;
import hr.maps.goalmanagment.persistence.entities.User;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CommentMapper {

	public Comment toComment(CommentDto comment, User writer, Task targetTask) {
		return new Comment(comment.getCommentText(), targetTask, writer);
	}

	public CommentDto toCommentDto(Comment comment) {
		return new CommentDto(comment.getCommentText(), comment.getCommentWriter().getUserFirstName(),
				comment.getCommentWriter().getUserLastName(), comment.getCreatedAt());
	}

}
