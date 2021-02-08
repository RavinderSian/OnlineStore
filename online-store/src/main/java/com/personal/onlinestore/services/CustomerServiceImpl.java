package com.personal.onlinestore.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.personal.onlinestore.model.Customer;
import com.personal.onlinestore.repository.CustomerRepository;

@Service
public class CustomerServiceImpl implements CustomerService {
	
	private final CustomerRepository repository;

	public CustomerServiceImpl(CustomerRepository repository) {
		this.repository = repository;
	}

	@Override
	public Customer save(Customer customer) {
		return repository.save(customer);
	}

	@Override
	public void delete(Customer customer) {
		repository.delete(customer);
		
	}

	@Override
	public Optional<Customer> findById(Long id) {
		Optional<Customer> customerOptional = repository.findById(id);
		if (!customerOptional.isPresent()) {
			return Optional.empty();
		}
		
		return customerOptional;
	}

	@Override
	public Customer updateFirstName(Customer customer, String firstName) {
		customer.setFirstName(firstName);
		return repository.save(customer);
		
	}

}
