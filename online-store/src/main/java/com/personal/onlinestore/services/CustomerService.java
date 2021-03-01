package com.personal.onlinestore.services;

import java.util.List;
import java.util.Optional;

import com.personal.onlinestore.model.Customer;
import com.personal.onlinestore.model.CustomerDTO;
import com.personal.onlinestore.model.Order;

public interface CustomerService {
	
	CustomerDTO saveAndReturnCustomerDTO(Customer customer);
	void deleteById(Long id);
	Optional<CustomerDTO> findById(Long id);
	CustomerDTO updateCustomerByCustomer(Long id, Customer customer);
	List<Order> findOrdersByCustomerId(Long id);

}
