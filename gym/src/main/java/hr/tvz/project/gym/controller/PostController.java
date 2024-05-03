package hr.tvz.project.gym.controller;

import java.util.List;

import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hr.tvz.project.gym.model.Post;
import hr.tvz.project.gym.service.PostService;

@RestController
@RequestMapping("/api")
public class PostController {
	
	private PostService postService;
	private MessageSource messageSource;
	
	public PostController(PostService postService, MessageSource messageSource) {
		this.postService = postService;
		this.messageSource = messageSource;
	}
	
	
	@GetMapping("/posts")
	List<Post> all() {
		return postService.findAllPosts();
	}
}
