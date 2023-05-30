package hr.maps.goalmanagment.service;

import hr.maps.goalmanagment.persistence.entities.Comment;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import hr.maps.goalmanagment.dtos.CommentDto;
import hr.maps.goalmanagment.dtos.PageDto;
import hr.maps.goalmanagment.dtos.TaskDto;
import hr.maps.goalmanagment.enumeration.TaskStatus;
import hr.maps.goalmanagment.mappers.CommentMapper;
import hr.maps.goalmanagment.persistence.entities.Task;
import hr.maps.goalmanagment.persistence.entities.User;
import hr.maps.goalmanagment.persistence.repositories.CommentRepository;
import hr.maps.goalmanagment.persistence.repositories.TaskRepository;
import lombok.RequiredArgsConstructor;

/**
 * @author Rokaya
 * @Date 01/05/2023
 */
@Service
@RequiredArgsConstructor
public class CommentService {

	private final CommentRepository commentRepository;
	private final TaskRepository taskRespository;
	private final UserService userService;
	private final CommentMapper commentMapper;

	public void saveComment(CommentDto comment, UUID taskUuid) {
		User user = userService.getCurrentUser();
		Task task = taskRespository.findByUuid(taskUuid);
		commentRepository.save(commentMapper.toComment(comment, user, task));
	}

	public List<CommentDto> findAllComments(UUID taskUuid) {
		return commentRepository.findAllCommentsByTargetTaskUuid(taskUuid).stream().sorted(Comparator.comparing(Comment::getCreatedAt).reversed()).map(commentMapper::toCommentDto)
				.collect(Collectors.toList());
	}

}
