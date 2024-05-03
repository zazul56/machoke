package hr.tvz.project.gym.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import hr.tvz.project.gym.model.Category;
import hr.tvz.project.gym.repository.CategoryRepository;

@Service
public class CategoryService {

	private CategoryRepository catRepository;
	private MessageSource msgSrc;
	
	
	@Autowired
	public CategoryService(CategoryRepository catRepository, MessageSource msgSrc) {
		this.catRepository = catRepository;
		this.msgSrc = msgSrc;
	}
	

	public Category getCategoryById(long cat_id) {
		return catRepository.getReferenceById(cat_id);
	}


	public List<Category> findAllCategories() {
		return catRepository.findAll();
	}


}
