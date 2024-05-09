package hr.tvz.project.gym.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import hr.tvz.project.gym.exception.FieldNotFoundException;
import hr.tvz.project.gym.model.Post;
import hr.tvz.project.gym.model.User;
import hr.tvz.project.gym.repository.PostRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class PostService {

	private PostRepository postRepository;
	private UserService userService;
	private MessageSource msgSrc;
	
	
	@Autowired
	public PostService(PostRepository postRepository, UserService userService, MessageSource msgSrc) {
		this.postRepository = postRepository;
		this.userService = userService;
		this.msgSrc = msgSrc;
	}
	

	public Post getPostById(long pos_id) {
		return postRepository.getPostById(pos_id);
	}

	public List<Post> findAllPosts() {
		return postRepository.findAll();
	}


	public Post create(Post newPost) throws NoSuchMessageException, FieldNotFoundException {
		
		if(newPost.getUser() == null || newPost.getUser().getUsername() == null) {
			throw new FieldNotFoundException(msgSrc.getMessage("warning.data.user.username.not.found.mandatory", null, LocaleContextHolder.getLocale()));
		}
		
		User user = userService.findByUsernameAndDeletedIsFalse(newPost.getUser().getUsername());
		
		if(user == null) {
			throw new EntityNotFoundException(msgSrc.getMessage("warning.data.user.not.found", null, LocaleContextHolder.getLocale()));
		} else {
			newPost.setUser(user);
		}
		
		if(newPost.getContent() == null || newPost.getContent().trim().equals("")) {
			throw new FieldNotFoundException(msgSrc.getMessage("warning.data.post.field.not.found.mandatory", new String[] {"content"}, LocaleContextHolder.getLocale()));
		}
		
		if(newPost.getQuestionId() == null || newPost.getQuestionId() == 0) {
			throw new FieldNotFoundException(msgSrc.getMessage("warning.data.post.field.not.found.mandatory",  new String[] {"question_id"}, LocaleContextHolder.getLocale()));
		}
		
		newPost = postRepository.save(newPost);
		
		return newPost;
	}


	public List<Post> findPostsByQuestionId(Long id) {
		return postRepository.findByQuestionIdAndDeletedFalse(id);
	}


	public void deleteByID(Long id) {
		Post post = getPostById(id);
		post.setDeleted(true);
		postRepository.save(post);
		//userRepository.delete(user);
	}
	

}
