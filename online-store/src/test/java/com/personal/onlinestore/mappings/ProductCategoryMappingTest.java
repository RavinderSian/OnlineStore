package com.personal.onlinestore.mappings;

import javax.validation.Validation;
import javax.validation.ValidatorFactory;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.personal.onlinestore.model.Category;
import com.personal.onlinestore.repository.CategoryRepository;
import com.personal.onlinestore.repository.ProductRepository;
import com.personal.onlinestore.services.CategoryService;
import com.personal.onlinestore.services.CategoryServiceImpl;
import com.personal.onlinestore.services.ProductService;
import com.personal.onlinestore.services.ProductServiceImpl;

@SpringBootTest
public class ProductCategoryMappingTest {

	ProductService productService;
	
	CategoryService categoryService;
	
	@Mock
	CategoryRepository categoryRepositoryMock;
	
	@Mock
	ProductRepository productRepositoryMock;
	
	@Mock
	Category mockCategory;
	
	@BeforeEach
	void setUp() throws Exception {
		categoryService = new CategoryServiceImpl(categoryRepositoryMock);
		productService = new ProductServiceImpl(productRepositoryMock);
	}
	
}
