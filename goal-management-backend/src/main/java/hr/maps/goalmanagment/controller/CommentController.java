package hr.maps.goalmanagment.controller;

import java.util.UUID;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import hr.maps.goalmanagment.dtos.CommentDto;
import hr.maps.goalmanagment.service.CommentService;
import lombok.RequiredArgsConstructor;

/**
 * @author Rokaya
 * @Date 14/04/2023
 */
@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
@CrossOrigin
public class CommentController {
	private final CommentService commentService;

	@PostMapping
	public void addComment(@RequestBody CommentDto comment, @RequestParam("task") UUID taskUuid) {
		this.commentService.saveComment(comment, taskUuid);
	}

}
