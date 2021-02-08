package com.personal.onlinestore.services;

import com.personal.onlinestore.model.Product;

public interface ProductService extends CrudService<Product, Long> {

	Product updateName(Product product, String name);
}
