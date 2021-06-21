package com.personal.onlinestore.controller;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
import com.personal.onlinestore.model.Product;
import com.personal.onlinestore.services.ProductService;

@AutoConfigureMockMvc
@SpringBootTest
class ProductControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	ProductController controller;
	
	@MockBean
	ProductService service;
	
	@Test
	public void test_Controller_IsNotNull() {
		assertNotNull(controller);
	}
	
	@Test
	public void test_GetById_ReturnsCorrectStatusAndResponse_WhenGivenId1() throws Exception {
		
		Product product = new Product();
		product.setProductId(1L);
		product.setName("test");
		
		when(service.findById(1L)).thenReturn(Optional.of(product));
		
		mockMvc.perform(get("/product/1"))
				.andExpect(status().isOk())
				.andExpect(content().json("{'productId':1, 'name':'test'}"));
	}
	
	@Test
	public void test_GetById_ReturnsStringProductNotFound_WhenGivenId10() throws Exception {
		
		mockMvc.perform(get("/product/10"))
				.andExpect(status().isNotFound())
				.andExpect(content().string("Product not found"));
	}
	
	@Test
	public void test_Delete_ReturnsStringProductNotFound_WhenGivenId10() throws Exception {
		
		mockMvc.perform(delete("/product/delete/10"))
				.andExpect(status().isNotFound())
				.andExpect(content().string("Product not found"));
	}
	
	@Test
	public void test_Save_ReturnsCorrectStatusAndResponse_WhenGivenValidProduct() throws Exception {
		
		Product product = new Product();
		product.setProductId(1L);
		product.setName("test");
		
		when(service.save(product)).thenReturn(product);
		
		ObjectMapper mapper = new ObjectMapper();
	    mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
	    ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
	    String requestJson = ow.writeValueAsString(product);
		
		this.mockMvc.perform(post("/product/save").contentType(MediaType.APPLICATION_JSON_VALUE).content(requestJson))
		.andExpect(status().isOk())
		.andExpect(content().json("{'productId':1, 'name':'test'}"));
	}
	
	@Test
	public void test_Save_ReturnsCorrectStatusAndResponse_WhenGivenInvalidProduct() throws Exception {
		
		Product product = new Product();
		product.setProductId(1L);
				
		ObjectMapper mapper = new ObjectMapper();
	    mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
	    ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
	    String requestJson = ow.writeValueAsString(product);
		
		this.mockMvc.perform(post("/product/save").contentType(MediaType.APPLICATION_JSON_VALUE).content(requestJson))
		.andExpect(status().isBadRequest())
		.andExpect(content().json("{\"name\":\"Please enter a valid product name\"}"));
	}
	
	@Test
	public void test_UpdateName_ReturnsCorrectStatusAndResponse_WhenGivenId1AndNameNewName() throws Exception {
		
		Product product = new Product();
		product.setProductId(1L);
		product.setName("test");
		
		when(service.findById(1L)).thenReturn(Optional.of(product));
		product.setName("new name");
		when(service.save(product)).thenReturn(product);
		
		this.mockMvc.perform(patch("/product/updatename/1").contentType(MediaType.APPLICATION_JSON_VALUE).content("new name"))
		.andExpect(status().isOk())
		.andExpect(content().json("{'productId':1, 'name':'new name'}"));
	}
	
	@Test
	public void test_UpdateName_ReturnsStringProductNotFound_WhenGivenId10() throws Exception {
		
		this.mockMvc.perform(patch("/product/updatename/10").contentType(MediaType.APPLICATION_JSON_VALUE).content("new name"))
		.andExpect(status().isNotFound())
		.andExpect(content().string("Product not found"));
	}
	
}
