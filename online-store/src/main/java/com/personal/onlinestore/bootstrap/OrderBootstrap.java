package com.personal.onlinestore.bootstrap;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.personal.onlinestore.model.Customer;
import com.personal.onlinestore.model.Order;
import com.personal.onlinestore.model.Product;
import com.personal.onlinestore.repository.CustomerRepository;
import com.personal.onlinestore.repository.OrderRepository;
import com.personal.onlinestore.repository.ProductRepository;

@Component
public class OrderBootstrap implements CommandLineRunner {

	private final OrderRepository repository;
	private final CustomerRepository customerRepository;
	private final ProductRepository productRepository;
	
	
	
	public OrderBootstrap(OrderRepository repository, CustomerRepository customerRepository,
			ProductRepository productRepository) {
		this.repository = repository;
		this.customerRepository = customerRepository;
		this.productRepository = productRepository;
	}

	@Override
	public void run(String... args) throws Exception {
		
		Order order = new Order();
		repository.save(order);
		
		Product product = new Product();
		product.setName("testing order bootstrap");
		order.addProduct(product);
		productRepository.save(product);
		repository.save(order);
		
		Customer customer = new Customer();
		customer.setFirstName("test");
		customer.setLastName("customer");
		customer.setPostCode("UB1");
		customerRepository.save(customer);
		customer.addOrder(order);
		repository.save(order);
		customerRepository.save(customer);
	}

}
