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
		product.setCategory(null);
		product.setOrder(null);
		repository.delete(product);
	}

	@Override
	public Optional<Product> findById(Long id) {
		return repository.findById(id).isPresent()
		? repository.findById(id)
		: Optional.empty();
	}

	@Override
	public Product updateName(Product product, String name) {
		product.setName(name);
		return repository.save(product);
	}

}
