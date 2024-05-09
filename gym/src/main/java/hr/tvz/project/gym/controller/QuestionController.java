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
	
	@GetMapping("/questions/category/{id}")
	List<Question> getQuestionsByCategoryId(@PathVariable Long id) {
		return questionService.findQuestionsByCategoryId(id);
	}
	
	@PostMapping("/questions")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_COACH', 'ROLE_USER')")
	ResponseEntity<String> newQuestion(@RequestBody Question newQuestion) {
		ResponseEntity<String> respEnt = null;
		try {
			questionService.create(newQuestion);
			respEnt = ResponseEntity.ok(messageSource.getMessage("data.question.added.success", null, LocaleContextHolder.getLocale()));
		} catch (NoSuchMessageException e) {
			e.printStackTrace();
		} catch (FieldNotFoundException e) {
			e.printStackTrace();
			respEnt = ResponseEntity.ok(e.getMessage());
		}
		
		return respEnt;
	}
	
	@PostMapping("/questions/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	ResponseEntity<String> updateQuestion(@PathVariable Long id, @RequestBody Question existingQuestion) throws FieldNotFoundException{
		ResponseEntity<String> respEnt = null;
		try {
			existingQuestion.setId(id);
			questionService.update(existingQuestion);
			respEnt = ResponseEntity.ok(messageSource.getMessage("data.question.updated.success", null, LocaleContextHolder.getLocale()));
		} catch (NoSuchMessageException e) {
			e.printStackTrace();
		}
		
		return respEnt;
	}
	
	@PostMapping("/questions/delete/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	ResponseEntity<String> deleteQuestion(@PathVariable Long id){
		ResponseEntity<String> respEnt = null;
		try {
			questionService.deleteByID(id);
			respEnt = ResponseEntity.ok(messageSource.getMessage("data.question.deleted.success", null, LocaleContextHolder.getLocale()));
		} catch (NoSuchMessageException e) {
			e.printStackTrace();
		}
		
		return respEnt;
	}
	
}
