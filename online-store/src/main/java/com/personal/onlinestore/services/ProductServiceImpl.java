package com.personal.onlinestore.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.personal.onlinestore.model.Product;
import com.personal.onlinestore.repository.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {
	
	private final ProductRepository repository;
	
	public ProductServiceImpl(ProductRepository repository) {
		this.repository = repository;
	}

	@Override
	public Product save(Product product) {
		return repository.save(product);
	}

	@Override
	public void delete(Product product) {
		repository.delete(product);

	}

	@Override
	public Optional<Product> findById(Long id) {
		Optional<Product> productOptional = repository.findById(id);
		if (!productOptional.isPresent()) {
			return Optional.empty();
		}
		
		return productOptional;
	}

	@Override
	public Product updateName(Product product, String name) {
		product.setName(name);
		return repository.save(product);
	}

}
