package com.personal.onlinestore.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.personal.onlinestore.model.Customer;
import com.personal.onlinestore.model.Order;
import com.personal.onlinestore.model.Product;
import com.personal.onlinestore.repository.OrderRepository;
import com.personal.onlinestore.repository.ProductRepository;

@SpringBootTest
class OrderServiceImplTest {
	
	OrderService orderService;
	
	@Mock
	OrderRepository mockRepository;
	
	@Mock
	ProductRepository productRepositoryMock;
	
	@Mock
	Order mockOrder;

	@BeforeEach
	void setUp() throws Exception {
		orderService = new OrderServiceImpl(mockRepository, productRepositoryMock);
	}

	@Test
	void test_CustomerService_IsNotNull() {
		assertNotNull(orderService);
	}
	
	@Test
	void test_OrderServiceDelete_ClearsBothSidesOfOrderCustomerMapping_WhenDeletingOrder() {
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
	void test_OrderServiceDelete_ClearsBothSidesOfProductOrderMapping_WhenDeletingOrder() {
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
	void test_FindById_ReturnsCorrectOrderOptional_WhenCalledWithId1() {
		//Arrange
		when(mockRepository.findById(1L)).thenReturn(Optional.of(mockOrder));
		//Assert
		assertEquals(orderService.findById(1L), Optional.of(mockOrder));
	}
	
	@Test
	void test_FindById_ReturnsEmptyOptional_WhenCalledWithId10() {
		//Assert
		assertEquals(orderService.findById(10L), Optional.empty());
	}
	
	@Test
	void test_FindProductsByOrderId_ReturnsCorrectProducts_WhenCalledWithId1() {
		//Arrange
		Product product = new Product();
		product.setName("testing order bootstrap");
		Product product2 = new Product();
		product2.setName("testing order bootstrap");
		
		List<Product> products = new ArrayList<>();
		products.add(product);
		products.add(product2);
		
		when(productRepositoryMock.findProductsByOrder_OrderId(1L)).thenReturn(products);
		//Assert
		assertEquals(product, orderService.findProductsByOrderId(1L).get(0));
		assertEquals(product2, orderService.findProductsByOrderId(1L).get(1));
		assertEquals(2, orderService.findProductsByOrderId(1L).size());
	}
	
	@Test
	void test_FindProductsByOrderId_ReturnsEmptyList_WhenCalledWithId10() {
		//Assert
		assertEquals(0, orderService.findProductsByOrderId(10L).size());
	}

}
