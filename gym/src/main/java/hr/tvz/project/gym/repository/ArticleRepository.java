package hr.tvz.project.gym.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import hr.tvz.project.gym.model.Article;

public interface ArticleRepository extends JpaRepository<Article, Long> {

	public Article getArticleById(long art_id);

	public List<Article> findByDeletedFalse();
	
	public Article findFirstByDeletedFalseOrderByCreatedAtDesc();
	
}
