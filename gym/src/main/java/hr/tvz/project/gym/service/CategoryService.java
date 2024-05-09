package hr.tvz.project.gym.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import hr.tvz.project.gym.exception.FieldNotFoundException;
import hr.tvz.project.gym.model.Category;
import hr.tvz.project.gym.model.User;
import hr.tvz.project.gym.repository.CategoryRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class CategoryService {

	private CategoryRepository catRepository;
	private UserService userService;
	private MessageSource msgSrc;
	
	
	@Autowired
	public CategoryService(CategoryRepository catRepository, UserService userService, MessageSource msgSrc) {
		this.catRepository = catRepository;
		this.userService = userService;
		this.msgSrc = msgSrc;
	}
	

	public Category getCategoryById(long cat_id) {
		return catRepository.getCategoryById(cat_id);
	}


	public List<Category> findAllCategories() {
		return catRepository.findByDeletedFalse();
	}

	public Category create(Category newCategory) throws NoSuchMessageException, FieldNotFoundException {
		
		if(newCategory.getUser() == null || newCategory.getUser().getUsername() == null) {
			throw new FieldNotFoundException(msgSrc.getMessage("warning.data.user.username.not.found.mandatory", null, LocaleContextHolder.getLocale()));
		}
		User user = userService.findByUsernameAndDeletedIsFalse(newCategory.getUser().getUsername());
		
		if(user == null) {
			throw new EntityNotFoundException(msgSrc.getMessage("warning.data.user.not.found", null, LocaleContextHolder.getLocale()));
		} else {
			newCategory.setUser(user);
		}
		
		if(newCategory.getName() == null || newCategory.getName().trim().equals("")) {
			throw new FieldNotFoundException(msgSrc.getMessage("warning.data.category.field.not.found.mandatory", new String[] {"name"}, LocaleContextHolder.getLocale()));
		}
		
		newCategory = catRepository.save(newCategory);
		
		return newCategory;
	}
	
	public Category update(Category category) throws NoSuchMessageException, FieldNotFoundException {
		
		if(category.getUser() == null || category.getUser().getUsername() == null) {
			throw new FieldNotFoundException(msgSrc.getMessage("warning.data.user.username.not.found.mandatory", null, LocaleContextHolder.getLocale()));
		}
		User user = userService.findByUsernameAndDeletedIsFalse(category.getUser().getUsername());
		
		if(user == null) {
			throw new EntityNotFoundException(msgSrc.getMessage("warning.data.user.not.found", null, LocaleContextHolder.getLocale()));
		} else {
			category.setUser(user);
		}
		
		if(category.getName() == null || category.getName().trim().equals("")) {
			throw new FieldNotFoundException(msgSrc.getMessage("warning.data.category.field.not.found.mandatory", new String[] {"name"}, LocaleContextHolder.getLocale()));
		}
		
		category = catRepository.save(category);
		
		return category;
	}
	
	public void deleteByID(Long id) {
		Category category = getCategoryById(id);
		category.setDeleted(true);
		catRepository.save(category);
	}
}
