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
import com.personal.onlinestore.model.CustomerDTO;
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
	public void test_SaveAndReturnCustomerDTO_CallsRepositorySave_WhenCalled() {
		//Arrange		
		Customer customer = new Customer();
		customer.setCustomerId(1L);
		customer.setFirstName("test");
		customer.setLastName("testing");
		customer.setCardNumber("1");
		when(mockRepository.save(customer)).thenReturn(customer);
		//Act
		customerService.saveAndReturnCustomerDTO(customer);
		//Assert
		verify(mockRepository, times(1)).save(customer);
	}
	
	@Test
	public void test_SaveAndReturnCustomerDTO_ReturnsCorrectCustomerDTO_WhenGivenCustomer() {
		//Arrange
		Customer customer = new Customer();
		customer.setCustomerId(1L);
		customer.setFirstName("test");
		customer.setLastName("testing");
		customer.setCardNumber("1");
		when(mockRepository.save(customer)).thenReturn(customer);
		//Act
		CustomerDTO savedCustomerDTO = customerService.saveAndReturnCustomerDTO(customer);
		//Assert
		assertEquals(savedCustomerDTO.getCustomerId(), customer.getCustomerId());
		assertEquals(savedCustomerDTO.getFirstName(), customer.getFirstName());
		assertEquals(savedCustomerDTO.getLastName(), customer.getLastName());
	}
	
	@Test
	public void test_DeleteById_CallsRepositoryDeleteById_WhenCalled() {
		//Act
		customerService.deleteById(1L);
		//Assert
		verify(mockRepository, times(1)).deleteById(1L);
	}
	
	@Test
	public void test_FindById_CallsRepositoryFindById_WhenCalled() {
		//Act
		customerService.findById(1L);
		//Assert
		verify(mockRepository, times(1)).findById(1L);
	}
	
	@Test
	public void test_FindById_ReturnsCorrectCustomerDTOOptional_WhenCalledWithId1() {
		//Arrange
		Customer customer = new Customer();
		customer.setCustomerId(1L);
		customer.setFirstName("test");
		customer.setLastName("testing");
		customer.setCardNumber("1");
		when(mockRepository.findById(1L)).thenReturn(Optional.of(customer));
		//Act
		Optional<CustomerDTO> customerDTOOptional = customerService.findById(1L);
		//Assert
		assertEquals(customerDTOOptional.get().getCustomerId(), customer.getCustomerId());
		assertEquals(customerDTOOptional.get().getFirstName(), customer.getFirstName());
		assertEquals(customerDTOOptional.get().getLastName(), customer.getLastName());
	}
	
	@Test
	public void test_FindById_ReturnsEmptyOptional_WhenCalledWithId10() {
		//Act
		Optional<CustomerDTO> customerDTOOptional = customerService.findById(10L);
		//Assert
		assertEquals(customerDTOOptional, Optional.empty());
	}
	
	@Test
	public void test_saveCustomerByDTO_ReturnsCustomerDTOWithFirstNameTest_WhenGivenCustomerDTOWithFirstNameTest() {
		//Arrange
		
		Customer customer = new Customer();
		customer.setCustomerId(1L);
		customer.setFirstName("test");
		customer.setLastName("testing");
		when(mockRepository.save(customer)).thenReturn(customer);
		
		CustomerDTO customerDTO = new CustomerDTO();
		customerDTO.setCustomerId(1L);
		customerDTO.setFirstName("test");
		customerDTO.setLastName("testing");
		
		//Act
		CustomerDTO updatedCustomerDTO = customerService.saveCustomerByDTO(1L, customerDTO);
		//Assert
		assertEquals(customerDTO, updatedCustomerDTO);
	}

}
