package com.personal.onlinestore.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.personal.onlinestore.model.Order;
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
		return orderService.findById(id).isPresent()
		? new ResponseEntity<>(orderService.findById(id).get(), HttpStatus.OK)
		: new ResponseEntity<>("Order not found", HttpStatus.NOT_FOUND);
	}

	@Override
	public ResponseEntity<String> deleteById(Long id) {
		if (orderService.findById(id).isPresent()) {
			Order order = orderService.findById(id).get();
			orderService.delete(order);
			return new ResponseEntity<>("Order with id " + id + " deleted", HttpStatus.OK);
		}
		return new ResponseEntity<>("Order not found", HttpStatus.NOT_FOUND);
	}

	@Override
	public ResponseEntity<?> save(Order order, BindingResult bindingResult) {
		return new ResponseEntity<>(orderService.save(order), HttpStatus.OK);
	}
	
	@GetMapping("/{id}/products")
	public ResponseEntity<?> getProductsForOrder(@PathVariable Long id){
		return orderService.findById(id).isPresent() 
		? new ResponseEntity<>(orderService.findProductsByOrderId(id), HttpStatus.OK)
		: new ResponseEntity<>("Order not found", HttpStatus.NOT_FOUND);
	}
	
	@GetMapping("/{id}/addproduct/{productId}")
	public ResponseEntity<?> addProducts(@PathVariable Long id, @PathVariable Long productId){
		if (!orderService.findById(id).isPresent()) {
			return new ResponseEntity<>("Order not found", HttpStatus.NOT_FOUND);
		}
		
		if (!productService.findById(productId).isPresent()) {
			return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);
		}
		
		Order order = orderService.findById(id).get();
		order.addProduct(productService.findById(productId).get());
		orderService.save(order);
		return new ResponseEntity<>("Product with id " + productId + " added to Order with id " + id, HttpStatus.OK);
	}
	
}
