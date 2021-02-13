package com.personal.onlinestore.controller;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.personal.onlinestore.model.Order;
import com.personal.onlinestore.services.OrderService;

@RestController
@RequestMapping("/order")
public class OrderController implements CrudController<Order, Long>{

	private final OrderService orderService;

	public OrderController(OrderService orderService) {
		this.orderService = orderService;
	}

	@Override
	public ResponseEntity<?> getById(Long id) {
		Optional<Order> orderOptional = orderService.findById(id);
		if (orderOptional.isEmpty()) {
			return new ResponseEntity<String>("Order not found", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Order>(orderOptional.get(), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<String> deleteById(Long id) {
		
		Optional<Order> orderOptional = orderService.findById(id);
		if (orderOptional.isEmpty()) {
			return new ResponseEntity<String>("Order not found", HttpStatus.NOT_FOUND);
		}
		Order order = orderOptional.get();
		orderService.delete(order);
		return new ResponseEntity<String>("Order with id " + id + " deleted", HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> save(Order order) {
		Order savedOrder = orderService.save(order);
		return new ResponseEntity<Order>(savedOrder, HttpStatus.OK);
	}
	
}
