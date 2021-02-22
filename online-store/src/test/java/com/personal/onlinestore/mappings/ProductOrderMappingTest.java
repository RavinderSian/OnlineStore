package com.personal.onlinestore.mappings;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.personal.onlinestore.model.Order;
import com.personal.onlinestore.model.Product;
import com.personal.onlinestore.repository.OrderRepository;
import com.personal.onlinestore.repository.ProductRepository;
import com.personal.onlinestore.services.OrderService;
import com.personal.onlinestore.services.OrderServiceImpl;
import com.personal.onlinestore.services.ProductService;
import com.personal.onlinestore.services.ProductServiceImpl;

@SpringBootTest
public class ProductOrderMappingTest {
	
	ProductService productService;
	
	OrderService orderService;
	
	@Mock
	OrderRepository orderRepositoryMock;
	
	@Mock
	ProductRepository productRepositoryMock;
	
	
	@BeforeEach
	void setUp() throws Exception {
		orderService = new OrderServiceImpl(orderRepositoryMock);
		productService = new ProductServiceImpl(productRepositoryMock);
	}
	
	@Test
	public void test_ProductServiceDelete_SetsOrderToNull_WhenDeletingProduct() {
		//Arrange
		Order order = new Order();
		Product product = new Product();
		order.addProduct(product);
		product.setOrder(order);
		//Act
		productService.delete(product);
		//Assert
		assertNull(product.getOrder());
	}
	
	@Test
	public void test_OrderServiceDelete_ClearsBothSidesOfMapping_WhenDeletingOrder() {
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

}
