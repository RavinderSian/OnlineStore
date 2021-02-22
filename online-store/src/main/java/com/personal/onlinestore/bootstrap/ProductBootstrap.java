package com.personal.onlinestore.bootstrap;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.personal.onlinestore.model.Category;
import com.personal.onlinestore.model.Product;
import com.personal.onlinestore.repository.CategoryRepository;
import com.personal.onlinestore.repository.ProductRepository;

@Component
public class ProductBootstrap implements CommandLineRunner{

	private final ProductRepository repository;
	private final CategoryRepository categoryRepository;
	
	public ProductBootstrap(ProductRepository repository, CategoryRepository categoryRepository) {
		this.repository = repository;
		this.categoryRepository = categoryRepository;
	}

	@Override
	public void run(String... args) throws Exception {
		
		Product product = new Product();
		product.setName("testing");
		repository.save(product);
		
		Category category = new Category();
		category.setName("test category");
		category.addProduct(product);
		categoryRepository.save(category);
		repository.save(product);

	}

}
