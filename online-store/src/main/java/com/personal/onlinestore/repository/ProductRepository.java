package com.personal.onlinestore.repository;

import org.springframework.data.repository.CrudRepository;

import com.personal.onlinestore.model.Product;

public interface ProductRepository extends CrudRepository<Product, Long> {

	
}
