package com.personal.onlinestore.controller;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.Charset;
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
import com.personal.onlinestore.repository.CategoryRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class CategoryControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	CategoryController controller;
	
	@MockBean
	CategoryRepository repository;
	
	public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

	@Test
	public void test_Controller_IsNotNull() {
		assertNotNull(controller);
	}
	
	@Test
	public void test_GetById_ReturnsCorrectStatusAndResponse_WhenGivenId1() throws Exception {
		
		Category category = new Category();
		category.setCategoryId(1L);
		category.setName("test");
		
		when(repository.findById(1L)).thenReturn(Optional.of(category));
		
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
		
		when(repository.findById(1L)).thenReturn(Optional.of(category));
		
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
		
		when(repository.save(category)).thenReturn(category);
		
		ObjectMapper mapper = new ObjectMapper();
	    mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
	    ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
	    String requestJson = ow.writeValueAsString(category);
		
		this.mockMvc.perform(post("/category/save").contentType(APPLICATION_JSON_UTF8).content(requestJson))
		.andExpect(status().isOk())
		.andExpect(content().json("{'categoryId':1, 'name':'test'}"));
	}
	
	@Test
	public void test_UpdateName_ReturnsCorrectStatusAndResponse_WhenGivenId1AndNameNewName() throws Exception {
		
		Category category = new Category();
		category.setCategoryId(1L);
		category.setName("test");
		
		when(repository.findById(1L)).thenReturn(Optional.of(category));
		when(repository.save(category)).thenReturn(category);
		
		this.mockMvc.perform(patch("/category/updatename/1").contentType(APPLICATION_JSON_UTF8).content("new name"))
		.andExpect(status().isOk())
		.andExpect(content().json("{'categoryId':1, 'name':'new name'}"));
	}
	
	@Test
	public void test_UpdateName_ReturnsStringCategoryNotFound_WhenGivenId10() throws Exception {
		
		this.mockMvc.perform(patch("/category/updatename/10").contentType(APPLICATION_JSON_UTF8).content("new name"))
		.andExpect(status().isNotFound())
		.andExpect(content().string("Category not found"));
	}

}
