package com.personal.onlinestore.services;

import java.util.Optional;

import com.personal.onlinestore.model.Customer;
import com.personal.onlinestore.model.CustomerDTO;

public interface CustomerService {
	
	CustomerDTO saveAndReturnCustomerDTO(Customer customer);
	void deleteById(Long id);
	Optional<CustomerDTO> findById(Long id);
	CustomerDTO saveCustomerByDTO(Long id, CustomerDTO customerDTO);

}
