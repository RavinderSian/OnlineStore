package com.personal.onlinestore.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.personal.onlinestore.model.Order;
import com.personal.onlinestore.model.Product;
import com.personal.onlinestore.services.OrderService;
import com.personal.onlinestore.services.ProductService;

@RestController
@RequestMapping("/order")
public class OrderController implements CrudController<Order, Long>{

	private final OrderService orderService;
	private final ProductService productService;

	public OrderController(OrderService orderService, ProductService productService) {
		this.orderService = orderService;
		this.productService = productService;
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
	public ResponseEntity<?> save(Order order, BindingResult bindingResult) {
		Order savedOrder = orderService.save(order);
		return new ResponseEntity<Order>(savedOrder, HttpStatus.OK);
	}
	
	@GetMapping("/{id}/products")
	public ResponseEntity<?> getProductsForOrder(@PathVariable Long id){
		Optional<Order> orderOptional = orderService.findById(id);
		if (orderOptional.isEmpty()) {
			return new ResponseEntity<String>("Order not found", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<Product>>(orderService.findProductsByOrderId(id), HttpStatus.OK);
	}
	
	@GetMapping("/{id}/addproduct/{productId}")
	public ResponseEntity<?> addProducts(@PathVariable Long id, @PathVariable Long productId){
		Optional<Order> orderOptional = orderService.findById(id);
		if (orderOptional.isEmpty()) {
			return new ResponseEntity<String>("Order not found", HttpStatus.NOT_FOUND);
		}
		
		Optional<Product> productOptional = productService.findById(productId);
		if (productOptional.isEmpty()) {
			return new ResponseEntity<String>("Product not found", HttpStatus.NOT_FOUND);
		}
		
		Order order = orderOptional.get();
		Product product = productOptional.get();
		order.addProduct(product);
		orderService.save(order);
		
		return new ResponseEntity<String>("Product with id " + productId + " added to Order with id " + id, HttpStatus.OK);
	}
	
}
