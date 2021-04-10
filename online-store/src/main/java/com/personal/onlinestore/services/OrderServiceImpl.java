package com.personal.onlinestore.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.personal.onlinestore.model.Order;
import com.personal.onlinestore.model.Product;
import com.personal.onlinestore.repository.OrderRepository;
import com.personal.onlinestore.repository.ProductRepository;

@Service
public class OrderServiceImpl implements OrderService {
	
	private final OrderRepository repository;
	
	private final ProductRepository productRepository;

	public OrderServiceImpl(OrderRepository repository, ProductRepository productRepository) {
		this.repository = repository;
		this.productRepository = productRepository;
	}

	@Override
	public Order save(Order order) {
		return repository.save(order);
	}

	@Override
	public void delete(Order order) {
		order.getProducts().forEach(product -> product.setOrder(null));
		order.setProducts(new ArrayList<>());
		order.setCustomer(null);
		repository.delete(order);

	}

	@Override
	public Optional<Order> findById(Long id) {
		return repository.findById(id).isPresent()
		? repository.findById(id)
		: Optional.empty();
	}

	@Override
	public List<Product> findProductsByOrderId(Long id) {
		return productRepository.findProductsByOrder_OrderId(id);
	}

}
