package com.personal.onlinestore.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.personal.onlinestore.model.Category;
import com.personal.onlinestore.model.Product;
import com.personal.onlinestore.services.CategoryService;

@SpringBootTest
@AutoConfigureMockMvc
public class CategoryControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	CategoryController controller;
	
	@MockBean
	CategoryService service;
	
	@Test
	public void test_Controller_IsNotNull() {
		assertNotNull(controller);
	}
	
	@Test
	public void test_GetById_ReturnsCorrectStatusAndResponse_WhenGivenId1() throws Exception {
		
		Category category = new Category();
		category.setCategoryId(1L);
		category.setName("test");
		
		when(service.findById(1L)).thenReturn(Optional.of(category));
		
		mockMvc.perform(get("/category/1"))
				.andExpect(status().isOk())
				.andExpect(content().json("{'categoryId':1, 'name':'test'}"));
	}
	
	@Test
	public void test_GetById_ReturnsStringCategoryNotFound_WhenGivenId10() throws Exception {
		
		mockMvc.perform(get("/category/10"))
				.andExpect(status().isNotFound())
				.andExpect(content().string("Category not found"));
	}
	
	@Test
	public void test_Delete_ReturnsCorrectStatusAndResponse_WhenGivenId1() throws Exception {
		
		Category category = new Category();
		category.setCategoryId(1L);
		category.setName("test");
		
		when(service.findById(1L)).thenReturn(Optional.of(category));
		
		mockMvc.perform(delete("/category/delete/1"))
				.andExpect(status().isOk())
				.andExpect(content().string("Category with id 1 deleted"));
	}
	
	@Test
	public void test_Delete_ReturnsStringCategoryNotFound_WhenGivenId10() throws Exception {
		
		mockMvc.perform(delete("/category/delete/10"))
				.andExpect(status().isNotFound())
				.andExpect(content().string("Category not found"));
	}
	
	@Test
	public void test_Save_ReturnsCorrectStatusAndResponse_WhenGivenValidCategory() throws Exception {
		
		Category category = new Category();
		category.setCategoryId(1L);
		category.setName("test");
		
		when(service.save(category)).thenReturn(category);
		
		ObjectMapper mapper = new ObjectMapper();
	    mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
	    ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
	    String requestJson = ow.writeValueAsString(category);
		
		this.mockMvc.perform(post("/category/save").contentType(MediaType.APPLICATION_JSON_VALUE).content(requestJson))
		.andExpect(status().isOk())
		.andExpect(content().json("{'categoryId':1, 'name':'test'}"));
	}
	
	@Test
	public void test_Save_ReturnsCorrectStatusAndResponse_WhenGivenInvalidCategory() throws Exception {
		
		Category category = new Category();
		category.setCategoryId(1L);
				
		ObjectMapper mapper = new ObjectMapper();
	    mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
	    ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
	    String requestJson = ow.writeValueAsString(category);
		
		this.mockMvc.perform(post("/category/save").contentType(MediaType.APPLICATION_JSON_VALUE).content(requestJson))
		.andExpect(status().isBadRequest())
		.andExpect(content().string("{\"name\":\"Please enter a valid category name\"}"));
	}
	
	@Test
	public void test_UpdateName_ReturnsCorrectStatusAndResponse_WhenGivenId1AndNameNewName() throws Exception {
		
		Category category = new Category();
		category.setCategoryId(1L);
		category.setName("test");
		
		when(service.findById(1L)).thenReturn(Optional.of(category));
		category.setName("new name");
		when(service.save(category)).thenReturn(category);
		
		this.mockMvc.perform(patch("/category/updatename/1").contentType(MediaType.APPLICATION_JSON_VALUE).content("new name"))
		.andExpect(status().isOk())
		.andExpect(content().json("{'categoryId':1, 'name':'new name'}"));
	}
	
	@Test
	public void test_UpdateName_ReturnsStringCategoryNotFound_WhenGivenId10() throws Exception {
		
		this.mockMvc.perform(patch("/category/updatename/10").contentType(MediaType.APPLICATION_JSON_VALUE).content("new name"))
		.andExpect(status().isNotFound())
		.andExpect(content().string("Category not found"));
	}
	
	@Test
	public void test_GetProducts_ReturnsCorrectStatusAndResponse_WhenGivenId1() throws Exception {
		
		Product product = new Product();
		product.setProductId(1L);
		product.setName("testing order bootstrap");
		Product product2 = new Product();
		product2.setProductId(2L);
		product2.setName("testing order");
		
		List<Product> products = new ArrayList<>();
		products.add(product);
		products.add(product2);
		
		Category category = new Category();
		
		when(service.findById(1L)).thenReturn(Optional.of(category));
		when(service.findProductsByCategoryId(1L)).thenReturn(products);
		
		mockMvc.perform(get("/category/1/products"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect((jsonPath("$[0].productId", is(1))))
				.andExpect((jsonPath("$[0].name", is("testing order bootstrap"))))
				.andExpect((jsonPath("$[1].productId", is(2))))
				.andExpect((jsonPath("$[1].name", is("testing order"))));
	}

	@Test
	public void test_GetProducts_ReturnsCorrectStatusAndResponse_WhenGivenId10() throws Exception {
		
		mockMvc.perform(get("/category/10/products"))
				.andExpect(status().isNotFound())
				.andExpect(content().string("Category not found"));
	}

}
