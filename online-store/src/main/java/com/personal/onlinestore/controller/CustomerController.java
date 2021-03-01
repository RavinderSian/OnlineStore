package com.personal.onlinestore.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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

@RestController
@RequestMapping("/customer")
public class CustomerController implements CrudController<Customer, Long>{

	private final CustomerService customerService;

	public CustomerController(CustomerService customerService) {
		this.customerService = customerService;
	}

	@Override
	public ResponseEntity<?> getById(Long id) {
		Optional<CustomerDTO> customerDTOOptional = customerService.findById(id);
		
		if (customerDTOOptional.isEmpty()) {
			return new ResponseEntity<String>("Customer not found", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<CustomerDTO>(customerDTOOptional.get(), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<String> deleteById(Long id) {
		if (customerService.findById(id).isEmpty()) {
			return new ResponseEntity<String>("Customer not found", HttpStatus.NOT_FOUND);
		}
		
		customerService.deleteById(id);
		return new ResponseEntity<String>("Customer with id " + id + " deleted", HttpStatus.OK);
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
		Optional<CustomerDTO> customerDTOOptional = customerService.findById(id);
		if (customerDTOOptional.isEmpty()) {
			return new ResponseEntity<String>("Customer not found", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<Order>>(customerService.findOrdersByCustomerId(id), HttpStatus.OK);
		 
	}
	
}
