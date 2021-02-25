package com.personal.onlinestore.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.personal.onlinestore.model.Product;

public interface ProductRepository extends CrudRepository<Product, Long> {

	List<Product> findProductsByOrder_OrderId(Long id);
}
