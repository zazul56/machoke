package hr.tvz.project.gym.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import hr.tvz.project.gym.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

	
}
