package hr.tvz.project.gym.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import hr.tvz.project.gym.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

	public Category getCategoryById(long cat_id);

	public List<Category> findByDeletedFalse();

	
}
