package hr.tvz.project.gym.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import hr.tvz.project.gym.model.Question;

public interface QuestionRepository extends JpaRepository<Question, Long> {

	public Question getQuestionById(long que_id);

	
}
