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

import com.personal.onlinestore.model.Product;
import com.personal.onlinestore.repository.ProductRepository;

@SpringBootTest
class ProductServiceImplTest {
	
	ProductService productService;
	
	@Mock
	ProductRepository mockRepository;
	
	@Mock
	Product mockProduct;

	@BeforeEach
	void setUp() throws Exception {
		productService = new ProductServiceImpl(mockRepository);
	}

	@Test
	public void test_CategoryService_IsNotNull() {
		assertNotNull(productService);
	}
	
	@Test
	public void test_Save_CallsRepositorySave_WhenCalled() {
		//Act
		productService.save(mockProduct);
		//Assert
		verify(mockRepository, times(1)).save(mockProduct);
	}
	
	@Test
	public void test_Save_ReturnsCorrectProduct_WhenGivenProductMock() {
		//Arrange
		when(mockRepository.save(mockProduct)).thenReturn(mockProduct);
		//Act
		Product savedProduct = productService.save(mockProduct);
		//Assert
		assertEquals(savedProduct, mockProduct);
	}
	
	@Test
	public void test_Delete_CallsRepositoryDelete_WhenCalled() {
		//Act
		productService.delete(mockProduct);
		//Assert
		verify(mockRepository, times(1)).delete(mockProduct);
	}
	
	@Test
	public void test_FindById_CallsRepositoryFindById_WhenCalled() {
		//Act
		productService.findById(1L);
		//Assert
		verify(mockRepository, times(1)).findById(1L);
	}
	
	@Test
	public void test_FindById_ReturnsCorrectProductOptional_WhenCalledWithId1() {
		//Arrange
		when(mockRepository.findById(1L)).thenReturn(Optional.of(mockProduct));
		//Act
		Optional<Product> productOptional = productService.findById(1L);
		//Assert
		assertEquals(productOptional, Optional.of(mockProduct));
	}
	
	@Test
	public void test_FindById_ReturnsEmptyOptional_WhenCalledWithId10() {
		//Act
		Optional<Product> productOptional = productService.findById(10L);
		//Assert
		assertEquals(productOptional, Optional.empty());
	}
	
	@Test
	public void test_UpdateName_ReturnsProductWithNameTest_WhenGivenProductWithNameTest() {
		//Arrange
		Product product = new Product();
		product.setName("testing");
		//Act
		productService.updateName(product, "test");
		//Assert
		assertEquals("test", product.getName());
		verify(mockRepository, times(1)).save(product);
	}

}
