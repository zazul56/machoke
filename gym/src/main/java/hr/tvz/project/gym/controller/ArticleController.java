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
import hr.tvz.project.gym.model.Article;
import hr.tvz.project.gym.service.ArticleService;

@RestController
@RequestMapping("/api")
public class ArticleController {
	
	private ArticleService articleService;
	private MessageSource messageSource;
	
	public ArticleController(ArticleService articleService, MessageSource messageSource) {
		this.articleService = articleService;
		this.messageSource = messageSource;
	}
	
	@GetMapping("/articles")
	List<Article> all() {
		return articleService.findAllArticles();
	}
	
	
	@GetMapping("/articles/{id}")
	public ResponseEntity<Article> getArticleById(@PathVariable Long id){
		Article article = articleService.getArticleById(id);
		if (article != null) {
			return ResponseEntity.ok(article);
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@GetMapping("/articles/last")
	public ResponseEntity<Article> getLastArticle(){
		Article article = articleService.getLastArticle();
		if (article != null) {
			return ResponseEntity.ok(article);
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@PostMapping("/articles")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_COACH')")
	ResponseEntity<String> newArticle(@RequestBody Article newArticle) {
		ResponseEntity<String> respEnt = null;
		try {
			articleService.create(newArticle);
			respEnt = ResponseEntity.ok(messageSource.getMessage("data.article.added.success", null, LocaleContextHolder.getLocale()));
		} catch (NoSuchMessageException e) {
			e.printStackTrace();
		} catch (FieldNotFoundException e) {
			e.printStackTrace();
			respEnt = ResponseEntity.ok(e.getMessage());
		}
		
		return respEnt;
	}
	
	@PostMapping("/articles/{id}")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_COACH')")
	ResponseEntity<String> updateArticle(@PathVariable Long id, @RequestBody Article article) throws FieldNotFoundException{
		ResponseEntity<String> respEnt = null;
		try {
			article.setId(id);
			articleService.update(article);
			respEnt = ResponseEntity.ok(messageSource.getMessage("data.article.updated.success", null, LocaleContextHolder.getLocale()));
		} catch (NoSuchMessageException e) {
			e.printStackTrace();
		}
		
		return respEnt;
	}
	
	@PostMapping("/articles/delete/{id}")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_COACH')")
	ResponseEntity<String> deleteArticle(@PathVariable Long id){
		ResponseEntity<String> respEnt = null;
		try {
			articleService.deleteByID(id);
			respEnt = ResponseEntity.ok(messageSource.getMessage("data.article.deleted.success", null, LocaleContextHolder.getLocale()));
		} catch (NoSuchMessageException e) {
			e.printStackTrace();
		}
		
		return respEnt;
	}
	
}
