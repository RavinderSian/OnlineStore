package com.personal.onlinestore.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.personal.onlinestore.model.Category;
import com.personal.onlinestore.model.Order;
import com.personal.onlinestore.model.Product;
import com.personal.onlinestore.repository.ProductRepository;

@SpringBootTest
class ProductServiceImplTest {
	
	ProductService productService;
	
	@Mock
	ProductRepository mockRepository;
	
	@Mock
	Product mockProduct;
	
	private Validator validator;

	@BeforeEach
	void setUp() throws Exception {
		productService = new ProductServiceImpl(mockRepository);
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@Test
	void test_CategoryService_IsNotNull() {
		assertNotNull(productService);
	}
	
	@Test
	void test_FieldValidation_GivesOneViolation_WhenGivenProductWithNoName() {
		Product product = new Product();
		assertEquals(1, validator.validate(product).size());
	}
	
	@Test
	void test_ProductServiceDelete_SetsOrderToNull_WhenDeletingProduct() {
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
	void test_ProductServiceDelete_SetsCategoryToNull_WhenDeletingProduct() {
		//Arrange
		Category category = new Category();
		category.setName("test");
		Product product = new Product();
		category.addProduct(product);
		product.setCategory(category);
		//Act
		productService.delete(product);
		//Assert
		assertNull(product.getCategory());
	}
	
	@Test
	void test_FindById_ReturnsCorrectProductOptional_WhenCalledWithId1() {
		//Arrange
		when(mockRepository.findById(1L)).thenReturn(Optional.of(mockProduct));
		//Assert
		assertEquals(productService.findById(1L), Optional.of(mockProduct));
	}
	
	@Test
	void test_FindById_ReturnsEmptyOptional_WhenCalledWithId10() {
		//Assert
		assertEquals(productService.findById(10L), Optional.empty());
	}
	
	@Test
	void test_UpdateName_ReturnsProductWithNameTest_WhenGivenProductWithNameTest() {
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
