package com.personal.onlinestore.services;

import com.personal.onlinestore.model.Category;

public interface CategoryService extends CrudService<Category, Long> {

	Category updateName(Category category, String name);
}
