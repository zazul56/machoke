package hr.tvz.project.gym.controller;

import java.util.List;

import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hr.tvz.project.gym.model.Question;
import hr.tvz.project.gym.service.QuestionService;

@RestController
@RequestMapping("/api")
public class QuestionController {
	
	private QuestionService questionService;
	private MessageSource messageSource;
	
	public QuestionController(QuestionService questionService, MessageSource messageSource) {
		this.questionService = questionService;
		this.messageSource = messageSource;
	}
	
	@GetMapping("/questions")
	List<Question> all() {
		return questionService.findAllQuestions();
	}
	
	@GetMapping("/questions/{id}")
	public ResponseEntity<Question> getQuestionById(@PathVariable Long id){
		Question question = questionService.getQuestionById(id);
		if (question != null) {
			return ResponseEntity.ok(question);
		} else {
			return ResponseEntity.notFound().build();
		}
	}
}
