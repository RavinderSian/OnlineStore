package com.personal.onlinestore.controller;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.personal.onlinestore.model.Category;
import com.personal.onlinestore.services.CategoryService;

@RestController
@RequestMapping("/category")
public class CategoryController implements CrudController<Category, Long> {
	
	private final CategoryService categoryService;
	
	public CategoryController(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	@Override
	public ResponseEntity<?> getById(Long id) {
		Optional<Category> categoryOptional = categoryService.findById(id);
		if (categoryOptional.isEmpty()) {
			return new ResponseEntity<String>("Category not found", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Category>(categoryOptional.get(), HttpStatus.OK);
		
	}	

	@Override
	public ResponseEntity<String> deleteById(Long id) {
		Optional<Category> categoryOptional = categoryService.findById(id);
		if (categoryOptional.isEmpty()) {
			return new ResponseEntity<String>("Category not found", HttpStatus.NOT_FOUND);
		}
		Category category = categoryOptional.get();
		categoryService.delete(category);
		return new ResponseEntity<String>("Category with id " + id + " deleted", HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> save(Category category) {
		
		Category savedCategory = categoryService.save(category);
		return new ResponseEntity<Category>(savedCategory, HttpStatus.OK);
	}
	
	@PatchMapping("/updatename/{id}")
	public ResponseEntity<?> updateName(@PathVariable Long id, @RequestBody String name){
		Optional<Category> categoryOptional = categoryService.findById(id);
		if (categoryOptional.isEmpty()) {
			return new ResponseEntity<String>("Category not found", HttpStatus.NOT_FOUND);
		}
		Category category = categoryOptional.get();
		categoryService.updateName(category, name);
		return new ResponseEntity<Category>(category, HttpStatus.OK);
	}

}
