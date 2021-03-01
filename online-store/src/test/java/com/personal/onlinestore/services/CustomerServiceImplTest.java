package com.personal.onlinestore.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.personal.onlinestore.model.Customer;
import com.personal.onlinestore.model.CustomerDTO;
import com.personal.onlinestore.model.Order;
import com.personal.onlinestore.repository.CustomerRepository;
import com.personal.onlinestore.repository.OrderRepository;

@SpringBootTest
class CustomerServiceImplTest {
	
	CustomerService customerService;
	
	@Mock
	CustomerRepository mockRepository;
	
	@Mock
	OrderRepository orderRepositoryMock;
	
	@Mock
	Customer mockCustomer;

	private Validator validator;

	@BeforeEach
	void setUp() throws Exception {
		customerService = new CustomerServiceImpl(mockRepository, orderRepositoryMock);
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@Test
	public void test_CustomerService_IsNotNull() {
		assertNotNull(customerService);
	}
	
	@Test
	public void test_FieldValidation_Gives4Violations_WhenGivenCustomerWithInvalidCardNumberAndAllOtherAttributesEmpty() {
		
		Customer customer = new Customer();
		customer.setCardNumber("1");
		assertEquals(4, validator.validate(customer).size());
	}
	
	@Test
	public void test_FieldValidation_Gives0Violations_WhenGivenValidCustomer() {
		
		Customer customer = new Customer();
		customer.setFirstName("test");
		customer.setLastName("testing");
		customer.setCardNumber("379763005117730");
		customer.setPostCode("UB1 1EP");
		assertEquals(0, validator.validate(customer).size());
	}
	
	@Test
	public void test_SaveAndReturnCustomerDTO_CallsRepositorySave_WhenCalled() {
		//Arrange		
		Customer customer = new Customer();
		customer.setCustomerId(1L);
		customer.setFirstName("test");
		customer.setLastName("testing");
		customer.setCardNumber("379763005117730");
		customer.setPostCode("UB1 1EP");
		when(mockRepository.save(customer)).thenReturn(customer);
		//Act
		customerService.saveAndReturnCustomerDTO(customer);
		//Assert
        assertTrue(validator.validate(customer).isEmpty());
		verify(mockRepository, times(1)).save(customer);
	}
	
	@Test
	public void test_SaveAndReturnCustomerDTO_ReturnsCorrectCustomerDTO_WhenGivenValidCustomer() {
		//Arrange
		Customer customer = new Customer();
		customer.setCustomerId(1L);
		customer.setFirstName("test");
		customer.setLastName("testing");
		customer.setCardNumber("379763005117730");
		customer.setPostCode("UB1 1EP");
		when(mockRepository.save(customer)).thenReturn(customer);
		//Act
		CustomerDTO savedCustomerDTO = customerService.saveAndReturnCustomerDTO(customer);
		//Assert
		assertTrue(validator.validate(customer).isEmpty());
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
		customer.setCardNumber("379763005117730");
		customer.setPostCode("UB1 1EP");
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
	public void test_updateCustomerByCustomer_ReturnsCustomerDTOWithCorrectInfo_WhenGivenCustomerWithCorrectInfoButNoId() {
		//Arrange
		
		Customer customer = new Customer();
		customer.setFirstName("test");
		customer.setLastName("testing");
		customer.setCardNumber("379763005117730");
		customer.setPostCode("UB1 1EP");
		
		Customer savedCustomer = new Customer();
		savedCustomer.setCustomerId(1L);
		savedCustomer.setFirstName("test");
		savedCustomer.setLastName("testing");
		savedCustomer.setCardNumber("379763005117730");
		savedCustomer.setPostCode("UB1 1EP");
		
		when(mockRepository.save(savedCustomer)).thenReturn(savedCustomer);
		
		CustomerDTO customerDTO = new CustomerDTO();
		customerDTO.setCustomerId(1L);
		customerDTO.setFirstName("test");
		customerDTO.setLastName("testing");
		
		//Act
		CustomerDTO updatedCustomerDTO = customerService.updateCustomerByCustomer(1L, customer);
		//Assert
		assertTrue(validator.validate(customer).isEmpty());
		assertEquals(customerDTO, updatedCustomerDTO);
	}
	
	@Test
	public void test_FindOrdersByCustomerId_ReturnsCorrectOrders_WhenCalledWithId1() {
		//Arrange
		Customer customer = new Customer();
		customer.setFirstName("test");
		customer.setLastName("testing");
		customer.setCardNumber("379763005117730");
		customer.setPostCode("UB1 1EP");
		
		Order order = new Order();
		order.setOrderId(1L);
		Order order2 = new Order();
		order2.setOrderId(2L);
		
		List<Order> orders = new ArrayList<>();
		orders.add(order);
		orders.add(order2);
		
		when(orderRepositoryMock.findOrdersByCustomer_CustomerId(1L)).thenReturn(orders);
		//Act
		List<Order> ordersForCustomer = customerService.findOrdersByCustomerId(1L);
		//Assert
		assertEquals(order, ordersForCustomer.get(0));
		assertEquals(order2, ordersForCustomer.get(1));
		assertEquals(2, ordersForCustomer.size());
	}
	
	@Test
	public void test_FindOrdersByCustomerId_ReturnsEmptyList_WhenCalledWithId10() {
		//Act
		List<Order> ordersForCustomer = customerService.findOrdersByCustomerId(1L);
		//Assert
		assertEquals(0, ordersForCustomer.size());
	}

}
