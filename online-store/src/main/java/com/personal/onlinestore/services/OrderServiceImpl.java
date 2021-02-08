package com.personal.onlinestore.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.personal.onlinestore.model.Order;
import com.personal.onlinestore.repository.OrderRepository;

@Service
public class OrderServiceImpl implements OrderService {
	
	private final OrderRepository repository;

	public OrderServiceImpl(OrderRepository repository) {
		this.repository = repository;
	}

	@Override
	public Order save(Order order) {
		return repository.save(order);
	}

	@Override
	public void delete(Order order) {
		repository.delete(order);

	}

	@Override
	public Optional<Order> findById(Long id) {
		Optional<Order> orderOptional = repository.findById(id);
		if (!orderOptional.isPresent()) {
			return Optional.empty();
		}
		return orderOptional;
	}

}
