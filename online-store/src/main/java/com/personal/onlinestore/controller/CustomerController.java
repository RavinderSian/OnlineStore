package com.personal.onlinestore.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.personal.onlinestore.model.Customer;
import com.personal.onlinestore.model.CustomerDTO;
import com.personal.onlinestore.model.Order;
import com.personal.onlinestore.services.CustomerService;
import com.personal.onlinestore.services.OrderService;

@RestController
@RequestMapping("/customer")
public class CustomerController implements CrudController<Customer, Long>{

	private final CustomerService customerService;
	private final OrderService orderService;

	public CustomerController(CustomerService customerService, OrderService orderService) {
		this.customerService = customerService;
		this.orderService = orderService;
	}

	@Override
	public ResponseEntity<?> getById(Long id) {
		return customerService.findById(id).isPresent()
		? new ResponseEntity<CustomerDTO>(customerService.findById(id).get(), HttpStatus.OK)
		: new ResponseEntity<String>("Customer not found", HttpStatus.NOT_FOUND);
	}

	@Override
	public ResponseEntity<String> deleteById(Long id) {
		if (customerService.findById(id).isPresent()) {
			customerService.deleteById(id);
			return new ResponseEntity<String>("Customer with id " + id + " deleted", HttpStatus.OK);
		}
		return new ResponseEntity<String>("Customer not found", HttpStatus.NOT_FOUND);
	}

	@Override
	public ResponseEntity<?> save(Customer customer, BindingResult bindingResult) {
		
		if (bindingResult.hasFieldErrors()) {
			Map<String, String> fieldErrorMap = new HashMap<>();
			
			bindingResult.getFieldErrors().forEach(objectError -> {
				fieldErrorMap.put(objectError.getField(), objectError.getDefaultMessage());
			});
			
			return new ResponseEntity<Map<String, String>>(fieldErrorMap, HttpStatus.BAD_REQUEST);
		}
		
		CustomerDTO savedCustomerDTO = customerService.saveAndReturnCustomerDTO(customer);
		return new ResponseEntity<CustomerDTO>(savedCustomerDTO, HttpStatus.OK);
	}
	
	@PutMapping("/{id}/update")
	public ResponseEntity<?> update(@PathVariable Long id, @RequestBody @Valid Customer customer, BindingResult bindingResult){
		
		if (bindingResult.hasFieldErrors()) {
			Map<String, String> fieldErrorMap = new HashMap<>();
			
			bindingResult.getFieldErrors().forEach(objectError -> {
				fieldErrorMap.put(objectError.getField(), objectError.getDefaultMessage());
			});
			
			return new ResponseEntity<Map<String, String>>(fieldErrorMap, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<CustomerDTO>(customerService.updateCustomerByCustomer(id, customer), HttpStatus.OK);
	}
	
	@GetMapping("/{id}/orders")
	public ResponseEntity<?> getOrders(@PathVariable Long id){
		
		return customerService.findById(id).isPresent()
		? new ResponseEntity<List<Order>>(customerService.findOrdersByCustomerId(id), HttpStatus.OK)
		: new ResponseEntity<String>("Customer not found", HttpStatus.NOT_FOUND);
	}
	
	@GetMapping("/{id}/addorder/{orderId}")
	public ResponseEntity<?> addProducts(@PathVariable Long id, @PathVariable Long orderId){
		if (!customerService.findCustomerById(id).isPresent()) {
			return new ResponseEntity<String>("Customer not found", HttpStatus.NOT_FOUND);
		}
		
		if (!orderService.findById(orderId).isPresent()) {
			return new ResponseEntity<String>("Order not found", HttpStatus.NOT_FOUND);
		}
		
		Customer customer = customerService.findCustomerById(id).get();
		customer.addOrder(orderService.findById(orderId).get());
		customerService.saveAndReturnCustomerDTO(customer);
		return new ResponseEntity<String>("Order with id " + orderId + " added to Customer with id " + id, HttpStatus.OK);
	}
	
}
