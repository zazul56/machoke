package hr.tvz.project.gym.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import hr.tvz.project.gym.model.Question;
import hr.tvz.project.gym.repository.QuestionRepository;

@Service
public class QuestionService {

	private QuestionRepository questionRepository;
	private MessageSource msgSrc;
	
	
	@Autowired
	public QuestionService(QuestionRepository questionRepository, MessageSource msgSrc) {
		this.questionRepository = questionRepository;
		this.msgSrc = msgSrc;
	}
	

	public Question getQuestionById(long que_id) {
		return questionRepository.getQuestionById(que_id);
	}


	//todo: by cat_id
	public List<Question> findAllQuestions() {
		return questionRepository.findAll();
	}


}
