package com.personal.onlinestore.services;

import java.util.List;

import com.personal.onlinestore.model.Category;
import com.personal.onlinestore.model.Product;

public interface CategoryService extends CrudService<Category, Long> {

	Category updateName(Category category, String name);
	List<Product> findProductsByCategoryId(Long id);
	
}
