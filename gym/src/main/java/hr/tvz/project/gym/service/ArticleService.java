package hr.tvz.project.gym.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import hr.tvz.project.gym.exception.FieldNotFoundException;
import hr.tvz.project.gym.model.Article;
import hr.tvz.project.gym.repository.ArticleRepository;

@Service
public class ArticleService {

	private ArticleRepository articleRepository;
	private MessageSource msgSrc;
	
	
	@Autowired
	public ArticleService(ArticleRepository articleRepository, MessageSource msgSrc) {
		this.articleRepository = articleRepository;
		this.msgSrc = msgSrc;
	}
	

	public Article getArticleById(long art_id) {
		return articleRepository.getArticleById(art_id);
	}


	public List<Article> findAllArticles() {
		return articleRepository.findByDeletedFalse();
	}

	public Article create(Article newArticle) throws NoSuchMessageException, FieldNotFoundException {
		
		if(newArticle.getTitle() == null || newArticle.getTitle().trim().equals("")) {
			throw new FieldNotFoundException(msgSrc.getMessage("warning.data.article.field.not.found.mandatory", new String[] {"title"}, LocaleContextHolder.getLocale()));
		}
		
		if(newArticle.getText() == null || newArticle.getText().trim().equals("")) {
			throw new FieldNotFoundException(msgSrc.getMessage("warning.data.article.field.not.found.mandatory", new String[] {"text"}, LocaleContextHolder.getLocale()));
		}
		
		
		//dodati spremanje slike
		
		newArticle = articleRepository.save(newArticle);
		
		return newArticle;
	}
	
	public Article update(Article article) throws NoSuchMessageException, FieldNotFoundException {
		
		if(article.getTitle() == null || article.getTitle().trim().equals("")) {
			throw new FieldNotFoundException(msgSrc.getMessage("warning.data.article.field.not.found.mandatory", new String[] {"title"}, LocaleContextHolder.getLocale()));
		}
		
		if(article.getText() == null || article.getText().trim().equals("")) {
			throw new FieldNotFoundException(msgSrc.getMessage("warning.data.article.field.not.found.mandatory", new String[] {"text"}, LocaleContextHolder.getLocale()));
		}
		
		//+ nešto za sliku
		
		article = articleRepository.save(article);
		
		return article;
	}
	
	public void deleteByID(Long id) {
		Article article = getArticleById(id);
		article.setDeleted(true);
		articleRepository.save(article);
	}


	public Article getLastArticle() {
		return articleRepository.findFirstByDeletedFalseOrderByCreatedAtDesc();
	}
	
}
