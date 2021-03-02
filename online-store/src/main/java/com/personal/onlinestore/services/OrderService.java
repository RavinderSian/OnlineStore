package com.personal.onlinestore.services;

import java.util.List;

import com.personal.onlinestore.model.Order;
import com.personal.onlinestore.model.Product;

public interface OrderService extends CrudService<Order, Long> {

	List<Product> findProductsByOrderId(Long id);
}
