package com.personal.onlinestore.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.personal.onlinestore.model.Customer;
import com.personal.onlinestore.model.Order;
import com.personal.onlinestore.model.Product;
import com.personal.onlinestore.repository.OrderRepository;

@SpringBootTest
class OrderServiceImplTest {
	
	OrderService orderService;
	
	@Mock
	OrderRepository mockRepository;
	
	@Mock
	Order mockOrder;

	@BeforeEach
	void setUp() throws Exception {
		orderService = new OrderServiceImpl(mockRepository);
	}

	@Test
	public void test_CustomerService_IsNotNull() {
		assertNotNull(orderService);
	}
	
	@Test
	public void test_Save_CallsRepositorySave_WhenCalled() {
		//Act
		orderService.save(mockOrder);
		//Assert
		verify(mockRepository, times(1)).save(mockOrder);
	}
	
	@Test
	public void test_Save_ReturnsCorrectOrder_WhenGivenOrderMock() {
		//Arrange
		when(mockRepository.save(mockOrder)).thenReturn(mockOrder);
		//Act
		Order savedOrder = orderService.save(mockOrder);
		//Assert
		assertEquals(savedOrder, mockOrder);
	}
	
	@Test
	public void test_Delete_CallsRepositoryDelete_WhenCalled() {
		//Act
		orderService.delete(mockOrder);
		//Assert
		verify(mockRepository, times(1)).delete(mockOrder);
	}
	
	@Test
	public void test_OrderServiceDelete_ClearsBothSidesOfOrderCustomerMapping_WhenDeletingOrder() {
		//Arrange
		Order order = new Order();
		Customer customer = new Customer();
		order.setCustomer(customer);
		customer.addOrder(order);
		//Act
		orderService.delete(order);
		//Assert
		assertNull(order.getCustomer());
	}
	
	@Test
	public void test_OrderServiceDelete_ClearsBothSidesOfProductOrderMapping_WhenDeletingOrder() {
		//Arrange
		Order order = new Order();
		Product product = new Product();
		order.addProduct(product);
		product.setOrder(order);
		//Act
		orderService.delete(order);
		//Assert
		assertNull(product.getOrder());
		assertEquals(order.getProducts().size(), 0);
	}
	
	@Test
	public void test_FindById_CallsRepositoryFindById_WhenCalled() {
		//Act
		orderService.findById(1L);
		//Assert
		verify(mockRepository, times(1)).findById(1L);
	}
	
	@Test
	public void test_FindById_ReturnsCorrectOrderOptional_WhenCalledWithId1() {
		//Arrange
		when(mockRepository.findById(1L)).thenReturn(Optional.of(mockOrder));
		//Act
		Optional<Order> orderOptional = orderService.findById(1L);
		//Assert
		assertEquals(orderOptional, Optional.of(mockOrder));
	}
	
	@Test
	public void test_FindById_ReturnsEmptyOptional_WhenCalledWithId10() {
		//Act
		Optional<Order> orderOptional = orderService.findById(10L);
		//Assert
		assertEquals(orderOptional, Optional.empty());
	}

}
