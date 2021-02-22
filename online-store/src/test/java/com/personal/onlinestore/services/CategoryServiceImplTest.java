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
import com.personal.onlinestore.model.Product;
import com.personal.onlinestore.repository.CategoryRepository;

@SpringBootTest
class CategoryServiceImplTest {
	
	CategoryService categoryService;
	
	@Mock
	CategoryRepository mockRepository;
	
	@Mock
	Category mockCategory;
	
	private Validator validator;

	@BeforeEach
	void setUp() throws Exception {
		categoryService = new CategoryServiceImpl(mockRepository);
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@Test
	public void test_CategoryService_IsNotNull() {
		assertNotNull(categoryService);
	}
	
	@Test
	public void test_FieldValidation_GivesOneViolation_WhenGivenCategoryWithNoName() {
		Category category = new Category();
		category.setCategoryId(1L);
		assertEquals(1, validator.validate(category).size());

	}
	
	@Test
	public void test_Save_CallsRepositorySave_WhenCalled() {
		//Act
		categoryService.save(mockCategory);
		//Assert
		verify(mockRepository, times(1)).save(mockCategory);
	}
	
	@Test
	public void test_Save_ReturnsCorrectCategory_WhenGivenCategoryMock() {
		//Arrange
		when(mockRepository.save(mockCategory)).thenReturn(mockCategory);
		//Act
		Category savedCategory = categoryService.save(mockCategory);
		//Assert
		assertEquals(savedCategory, mockCategory);
	}
	
	@Test
	public void test_Delete_CallsRepositoryDelete_WhenCalled() {
		//Act
		categoryService.delete(mockCategory);
		//Assert
		verify(mockRepository, times(1)).delete(mockCategory);
	}
	
	@Test
	public void test_CategoryServiceDelete_ClearsBothSidesOfMapping_WhenDeletingCategory() {
		//Arrange
		Category category = new Category();
		category.setName("test");
		Product product = new Product();
		category.addProduct(product);
		product.setCategory(category);
		//Act
		categoryService.delete(category);
		//Assert
		assertNull(product.getCategory());
		assertEquals(category.getProducts().size(), 0);
	}
	
	@Test
	public void test_FindById_CallsRepositoryFindById_WhenCalled() {
		//Act
		categoryService.findById(1L);
		//Assert
		verify(mockRepository, times(1)).findById(1L);
	}
	
	@Test
	public void test_FindById_ReturnsCorrectCategoryOptional_WhenCalledWithId1() {
		//Arrange
		when(mockRepository.findById(1L)).thenReturn(Optional.of(mockCategory));
		//Act
		Optional<Category> categoryOptional = categoryService.findById(1L);
		//Assert
		assertEquals(categoryOptional, Optional.of(mockCategory));
	}
	
	@Test
	public void test_FindById_ReturnsEmptyOptional_WhenCalledWithId10() {
		//Act
		Optional<Category> categoryOptional = categoryService.findById(10L);
		//Assert
		assertEquals(categoryOptional, Optional.empty());
	}
	
	@Test
	public void test_UpdateName_ReturnsCategoryWithNameTest_WhenGivenCategoryWithNameTest() {
		//Arrange
		Category category = new Category();
		category.setName("testing");
		//Act
		categoryService.updateName(category, "test");
		//Assert
		assertEquals("test", category.getName());
		verify(mockRepository, times(1)).save(category);
	}

}
