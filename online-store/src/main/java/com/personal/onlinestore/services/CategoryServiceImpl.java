package com.personal.onlinestore.services;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.personal.onlinestore.model.Category;
import com.personal.onlinestore.model.Product;
import com.personal.onlinestore.repository.CategoryRepository;
import com.personal.onlinestore.repository.ProductRepository;

@Service
public class CategoryServiceImpl implements CategoryService {

	private final CategoryRepository repository;
	
	private final ProductRepository productRepository;

	public CategoryServiceImpl(CategoryRepository repository, ProductRepository productRepository) {
		this.repository = repository;
		this.productRepository = productRepository;
	}

	@Override
	public Category save(Category category) {
		return repository.save(category);
	}

	@Override
	public void delete(Category category) {
		category.getProducts().forEach(product -> product.setCategory(null));
		category.setProducts(new HashSet<>());
		repository.delete(category);
	}

	@Override
	public Optional<Category> findById(Long id) {
		return repository.findById(id).isPresent()
		? repository.findById(id)
		: Optional.empty();
	}

	@Override
	public Category updateName(Category category, String name) {
		category.setName(name);
		return repository.save(category);
	}

	@Override
	public List<Product> findProductsByCategoryId(Long id) {
		return productRepository.findProductsByCategory_CategoryId(id);
	}
}
