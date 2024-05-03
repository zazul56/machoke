package hr.tvz.project.gym.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import hr.tvz.project.gym.model.Post;

public interface PostRepository extends JpaRepository<Post, Long> {

	
}
