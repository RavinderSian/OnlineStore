package com.personal.onlinestore.services;

import com.personal.onlinestore.model.Customer;

public interface CustomerService extends CrudService<Customer, Long> {
	
	Customer updateFirstName(Customer customer, String firstName);

}
