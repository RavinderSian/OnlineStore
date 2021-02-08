package com.personal.onlinestore.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.personal.onlinestore.model.Category;
import com.personal.onlinestore.repository.CategoryRepository;

@Service
public class CategoryServiceImpl implements CategoryService {

	private final CategoryRepository repository;

	public CategoryServiceImpl(CategoryRepository repository) {
		this.repository = repository;
	}

	@Override
	public Category save(Category category) {
		return repository.save(category);
	}

	@Override
	public void delete(Category category) {
		repository.delete(category);
		
	}

	@Override
	public Optional<Category> findById(Long id) {
		Optional<Category> categoryOptional = repository.findById(id);
		if (!categoryOptional.isPresent()) {
			return Optional.empty();
		}
		return categoryOptional;
	}

	@Override
	public Category updateName(Category category, String name) {
		category.setName(name);
		return repository.save(category);
	}
}
