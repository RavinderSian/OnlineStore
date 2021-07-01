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
	private final ModelMapper mapper;

	public CustomerServiceImpl(final CustomerRepository repository, final OrderRepository orderRepository) {
		this.repository = repository;
		this.orderRepository = orderRepository;
		this.mapper = new ModelMapper();
	}

	@Override
	public CustomerDTO saveAndReturnCustomerDTO(Customer customer) {
		return mapper.map(repository.save(customer), CustomerDTO.class);
	}

	@Override
	public void deleteById(Long id) {
		repository.deleteById(id);
	}

	@Override
	public Optional<CustomerDTO> findById(Long id) {
		return findCustomerById(id).isPresent()
		? Optional.of(mapper.map(findCustomerById(id).get(), CustomerDTO.class))
		: Optional.empty();
	}

	@Override
	public CustomerDTO updateCustomerByCustomer(Long id, Customer customer) {
		customer.setCustomerId(id);
		return mapper.map(repository.save(customer), CustomerDTO.class);
	}

	@Override
	public List<Order> findOrdersByCustomerId(Long id) {
		return orderRepository.findOrdersByCustomer_CustomerId(id);
	}

	@Override
	public Optional<Customer> findCustomerById(Long id) {
		return repository.findById(id).isPresent()
		? repository.findById(id)
		: Optional.empty();
	}

}
