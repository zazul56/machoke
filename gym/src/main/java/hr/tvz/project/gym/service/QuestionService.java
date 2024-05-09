package hr.tvz.project.gym.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import hr.tvz.project.gym.exception.FieldNotFoundException;
import hr.tvz.project.gym.model.Question;
import hr.tvz.project.gym.model.User;
import hr.tvz.project.gym.repository.QuestionRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class QuestionService {

	private QuestionRepository questionRepository;
	private UserService userService;
	private MessageSource msgSrc;
	
	
	@Autowired
	public QuestionService(QuestionRepository questionRepository, UserService userService, MessageSource msgSrc) {
		this.questionRepository = questionRepository;
		this.userService = userService;
		this.msgSrc = msgSrc;
	}
	

	public Question getQuestionById(long que_id) {
		return questionRepository.getQuestionById(que_id);
	}


	public List<Question> findAllQuestions() {
		return questionRepository.findAll();
	}


	public List<Question> findQuestionsByCategoryId(Long id) {
		return questionRepository.findByCategoryIdAndDeletedFalse(id);
	}

	public Question create(Question newQuestion) throws NoSuchMessageException, FieldNotFoundException {
		
		if(newQuestion.getUser() == null || newQuestion.getUser().getUsername() == null) {
			throw new FieldNotFoundException(msgSrc.getMessage("warning.data.user.username.not.found.mandatory", null, LocaleContextHolder.getLocale()));
		}
		User user = userService.findByUsernameAndDeletedIsFalse(newQuestion.getUser().getUsername());
		
		if(user == null) {
			throw new EntityNotFoundException(msgSrc.getMessage("warning.data.user.not.found", null, LocaleContextHolder.getLocale()));
		} else {
			newQuestion.setUser(user);
		}
		
		if(newQuestion.getQ_text() == null || newQuestion.getQ_text().trim().equals("")) {
			throw new FieldNotFoundException(msgSrc.getMessage("warning.data.question.field.not.found.mandatory", new String[] {"q_text"}, LocaleContextHolder.getLocale()));
		}
		
		if(newQuestion.getCategoryId() == null || newQuestion.getCategoryId() == 0) {
			throw new FieldNotFoundException(msgSrc.getMessage("warning.data.question.field.not.found.mandatory",  new String[] {"category_id"}, LocaleContextHolder.getLocale()));
		}
		
		newQuestion = questionRepository.save(newQuestion);
		
		return newQuestion;
	}
	
	public Question update(Question question) throws NoSuchMessageException, FieldNotFoundException {
		
		Question oldQ = questionRepository.getQuestionById(question.getId());
		
		if(question.getUser() == null || question.getUser().getUsername() == null) {
			throw new FieldNotFoundException(msgSrc.getMessage("warning.data.user.username.not.found.mandatory", null, LocaleContextHolder.getLocale()));
		}
		User user = userService.findByUsernameAndDeletedIsFalse(question.getUser().getUsername());
		
		if(user == null) {
			throw new EntityNotFoundException(msgSrc.getMessage("warning.data.user.not.found", null, LocaleContextHolder.getLocale()));
		} else {
			question.setUser(user);
		}
		
		if(question.getQ_text() == null || question.getQ_text().trim().equals("")) {
			throw new FieldNotFoundException(msgSrc.getMessage("warning.data.question.field.not.found.mandatory", new String[] {"q_text"}, LocaleContextHolder.getLocale()));
		}
		
		question.setCategoryId(oldQ.getCategoryId());
		
		question = questionRepository.save(question);
		
		return question;
	}
	
	public void deleteByID(Long id) {
		Question question = getQuestionById(id);
		question.setDeleted(true);
		questionRepository.save(question);
	}

}
