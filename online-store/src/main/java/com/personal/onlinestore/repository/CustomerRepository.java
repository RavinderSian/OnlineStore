package com.personal.onlinestore.repository;

import org.springframework.data.repository.CrudRepository;

import com.personal.onlinestore.model.Customer;

public interface CustomerRepository extends CrudRepository<Customer, Long> {

	
}
