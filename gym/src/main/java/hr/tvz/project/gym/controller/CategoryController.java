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
import hr.tvz.project.gym.model.Category;
import hr.tvz.project.gym.service.CategoryService;

@RestController
@RequestMapping("/api")
public class CategoryController {
	
	private CategoryService categoryService;
	private MessageSource messageSource;
	
	public CategoryController(CategoryService categoryService, MessageSource messageSource) {
		this.categoryService = categoryService;
		this.messageSource = messageSource;
	}
	
	@GetMapping("/categories")
	List<Category> all() {
		return categoryService.findAllCategories();
	}
	
	
	@GetMapping("/categories/{id}")
	public ResponseEntity<Category> getCategoryById(@PathVariable Long id){
		Category category = categoryService.getCategoryById(id);
		if (category != null) {
			return ResponseEntity.ok(category);
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@PostMapping("/categories")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_COACH', 'ROLE_USER')")
	ResponseEntity<String> newCategory(@RequestBody Category newCategory) {
		ResponseEntity<String> respEnt = null;
		try {
			categoryService.create(newCategory);
			respEnt = ResponseEntity.ok(messageSource.getMessage("data.category.added.success", null, LocaleContextHolder.getLocale()));
		} catch (NoSuchMessageException e) {
			e.printStackTrace();
		} catch (FieldNotFoundException e) {
			e.printStackTrace();
			respEnt = ResponseEntity.ok(e.getMessage());
		}
		
		return respEnt;
	}
	
	@PostMapping("/categories/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	ResponseEntity<String> updateCategory(@PathVariable Long id, @RequestBody Category category) throws FieldNotFoundException{
		ResponseEntity<String> respEnt = null;
		try {
			category.setId(id);
			categoryService.update(category);
			respEnt = ResponseEntity.ok(messageSource.getMessage("data.category.updated.success", null, LocaleContextHolder.getLocale()));
		} catch (NoSuchMessageException e) {
			e.printStackTrace();
		}
		
		return respEnt;
	}
	
	@PostMapping("/categories/delete/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	ResponseEntity<String> deleteCategory(@PathVariable Long id){
		ResponseEntity<String> respEnt = null;
		try {
			categoryService.deleteByID(id);
			respEnt = ResponseEntity.ok(messageSource.getMessage("data.category.deleted.success", null, LocaleContextHolder.getLocale()));
		} catch (NoSuchMessageException e) {
			e.printStackTrace();
		}
		
		return respEnt;
	}
	
}
