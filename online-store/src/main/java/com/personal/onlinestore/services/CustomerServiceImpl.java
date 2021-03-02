package com.personal.onlinestore.services;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.personal.onlinestore.model.Customer;
import com.personal.onlinestore.model.CustomerDTO;
import com.personal.onlinestore.model.Order;
import com.personal.onlinestore.repository.CustomerRepository;
import com.personal.onlinestore.repository.OrderRepository;

@Service
public class CustomerServiceImpl implements CustomerService {
	
	private final CustomerRepository repository;
	private final OrderRepository orderRepository;
	
	private ModelMapper mapper = new ModelMapper();

	public CustomerServiceImpl(CustomerRepository repository, OrderRepository orderRepository) {
		this.repository = repository;
		this.orderRepository = orderRepository;
	}

	@Override
	public CustomerDTO saveAndReturnCustomerDTO(Customer customer) {
		Customer savedCustomer = repository.save(customer);
		return mapper.map(savedCustomer, CustomerDTO.class);
		
	}

	@Override
	public void deleteById(Long id) {
		repository.deleteById(id);
		
	}

	@Override
	public Optional<CustomerDTO> findById(Long id) {
		Optional<Customer> customerOptional = findCustomerById(id);
		if (!customerOptional.isPresent()) {
			return Optional.empty();
		}
		
		CustomerDTO customerDTO = mapper.map(customerOptional.get(), CustomerDTO.class);
		return Optional.of(customerDTO);
	}

	@Override
	public CustomerDTO updateCustomerByCustomer(Long id, Customer customer) {
		
		customer.setCustomerId(id);
		
		Customer savedCustomer = repository.save(customer);
		
		return mapper.map(savedCustomer, CustomerDTO.class);
		
	}

	@Override
	public List<Order> findOrdersByCustomerId(Long id) {
		return orderRepository.findOrdersByCustomer_CustomerId(id);
	}

	@Override
	public Optional<Customer> findCustomerById(Long id) {
		Optional<Customer> customerOptional = repository.findById(id);
		if (!customerOptional.isPresent()) {
			return Optional.empty();
		}
		
		return customerOptional;
	}

}
