package hr.tvz.project.gym.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import hr.tvz.project.gym.model.Post;
import hr.tvz.project.gym.repository.PostRepository;

@Service
public class PostService {

	private PostRepository postRepository;
	private MessageSource msgSrc;
	
	
	@Autowired
	public PostService(PostRepository postRepository, MessageSource msgSrc) {
		this.postRepository = postRepository;
		this.msgSrc = msgSrc;
	}
	

	public Post getPostById(long que_id) {
		return postRepository.getReferenceById(que_id);
	}


	//todo: by que_id
	public List<Post> findAllPosts() {
		return postRepository.findAll();
	}


}
