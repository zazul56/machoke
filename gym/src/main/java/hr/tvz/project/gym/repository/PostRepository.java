package hr.tvz.project.gym.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import hr.tvz.project.gym.model.Post;

public interface PostRepository extends JpaRepository<Post, Long> {

	List<Post> findByQuestionIdAndDeletedFalse(Long id);

	Post getPostById(long pos_id);
	
}
