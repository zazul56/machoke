package hr.tvz.project.gym.controller;

import java.util.List;

import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hr.tvz.project.gym.model.Category;
import hr.tvz.project.gym.model.User;
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
}
