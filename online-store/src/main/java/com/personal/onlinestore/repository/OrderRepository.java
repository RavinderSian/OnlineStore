package com.personal.onlinestore.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.personal.onlinestore.model.Order;

public interface OrderRepository extends CrudRepository<Order, Long> {

	List<Order> findOrdersByCustomer_CustomerId(Long id);
}
