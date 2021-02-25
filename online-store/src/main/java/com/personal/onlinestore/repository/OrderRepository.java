package com.personal.onlinestore.repository;

import org.springframework.data.repository.CrudRepository;

import com.personal.onlinestore.model.Order;

public interface OrderRepository extends CrudRepository<Order, Long> {

}
