package com.personal.onlinestore.repository;

import org.springframework.data.repository.CrudRepository;

import com.personal.onlinestore.model.Category;

public interface CategoryRepository extends CrudRepository<Category, Long> {

	
}
