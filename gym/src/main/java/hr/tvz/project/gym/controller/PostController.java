package hr.tvz.project.gym.controller;

import java.util.List;

import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hr.tvz.project.gym.exception.FieldNotFoundException;
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
	
	@GetMapping("/posts/question/{id}")
	List<Post> getPostsByQuestionId(@PathVariable Long id) {
		return postService.findPostsByQuestionId(id);
	}
	
	@PostMapping("/posts")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_COACH', 'ROLE_USER')")
	ResponseEntity<String> newPost(@RequestBody Post newPost) {
		ResponseEntity<String> respEnt = null;
		try {
			postService.create(newPost);
			respEnt = ResponseEntity.ok(messageSource.getMessage("data.post.added.success", null, LocaleContextHolder.getLocale()));
		} catch (NoSuchMessageException e) {
			e.printStackTrace();
		} catch (FieldNotFoundException e) {
			e.printStackTrace();
			respEnt = ResponseEntity.ok(e.getMessage());
		}
		
		return respEnt;
	}
}
