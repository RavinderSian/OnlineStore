package com.personal.onlinestore.controller;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.personal.onlinestore.model.Customer;
import com.personal.onlinestore.model.CustomerDTO;
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
	public ResponseEntity<?> save(Customer customer) {
		CustomerDTO savedCustomerDTO = customerService.saveAndReturnCustomerDTO(customer);
		return new ResponseEntity<CustomerDTO>(savedCustomerDTO, HttpStatus.OK);
	}
	
	@PutMapping("/{id}/update")
	public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Customer customer){
		CustomerDTO updatedCustomerDTO = customerService.updateCustomerByCustomer(id, customer);
		return new ResponseEntity<CustomerDTO>(updatedCustomerDTO, HttpStatus.OK);
	}
	
}
