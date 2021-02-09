package com.personal.onlinestore.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.personal.onlinestore.model.Customer;
import com.personal.onlinestore.repository.CustomerRepository;

@SpringBootTest
class CustomerServiceImplTest {
	
	CustomerService customerService;
	
	@Mock
	CustomerRepository mockRepository;
	
	@Mock
	Customer mockCustomer;

	@BeforeEach
	void setUp() throws Exception {
		customerService = new CustomerServiceImpl(mockRepository);
	}

	@Test
	public void test_CustomerService_IsNotNull() {
		assertNotNull(customerService);
	}
	
	@Test
	public void test_Save_CallsRepositorySave_WhenCalled() {
		//Act
		customerService.save(mockCustomer);
		//Assert
		verify(mockRepository, times(1)).save(mockCustomer);
	}
	
	@Test
	public void test_Save_ReturnsCorrectCustomer_WhenGivenCustomerMock() {
		//Arrange
		when(mockRepository.save(mockCustomer)).thenReturn(mockCustomer);
		//Act
		Customer savedCustomer = customerService.save(mockCustomer);
		//Assert
		assertEquals(savedCustomer, mockCustomer);
	}
	
	@Test
	public void test_Delete_CallsRepositoryDelete_WhenCalled() {
		//Act
		customerService.delete(mockCustomer);
		//Assert
		verify(mockRepository, times(1)).delete(mockCustomer);
	}
	
	@Test
	public void test_FindById_CallsRepositoryFindById_WhenCalled() {
		//Act
		customerService.findById(1L);
		//Assert
		verify(mockRepository, times(1)).findById(1L);
	}
	
	@Test
	public void test_FindById_ReturnsCorrectCustomerOptional_WhenCalledWithId1() {
		//Arrange
		when(mockRepository.findById(1L)).thenReturn(Optional.of(mockCustomer));
		//Act
		Optional<Customer> customerOptional = customerService.findById(1L);
		//Assert
		assertEquals(customerOptional, Optional.of(mockCustomer));
	}
	
	@Test
	public void test_FindById_ReturnsEmptyOptional_WhenCalledWithId10() {
		//Act
		Optional<Customer> customerOptional = customerService.findById(10L);
		//Assert
		assertEquals(customerOptional, Optional.empty());
	}
	
	@Test
	public void test_UpdateFirstName_ReturnsCustomerWithFirstNameTest_WhenGivenCustomerWithFirstNameTest() {
		//Arrange
		Customer customer = new Customer();
		customer.setFirstName("testing");
		//Act
		customerService.updateFirstName(customer, "test");
		//Assert
		assertEquals("test", customer.getFirstName());
	}

}
