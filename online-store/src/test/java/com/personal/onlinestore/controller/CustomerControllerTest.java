package com.personal.onlinestore.controller;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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
import com.personal.onlinestore.model.Customer;
import com.personal.onlinestore.model.CustomerDTO;
import com.personal.onlinestore.repository.CustomerRepository;

@SpringBootTest
@AutoConfigureMockMvc
class CustomerControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	CustomerController controller;
	
	@MockBean
	CustomerRepository repository;
	
	public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

	@Test
	public void test_Controller_IsNotNull() {
		assertNotNull(controller);
	}
	
	@Test
	public void test_GetById_ReturnsCorrectStatusAndResponse_WhenGivenId1() throws Exception {
		
		Customer customer = new Customer();
		customer.setCustomerId(1L);
		customer.setFirstName("test");
		customer.setLastName("testing");
		
		when(repository.findById(1L)).thenReturn(Optional.of(customer));
		
		mockMvc.perform(get("/customer/1"))
				.andExpect(status().isOk())
				.andExpect(content().json("{'customerId':1, 'firstName':'test', 'lastName':'testing'}"));
	}
	
	@Test
	public void test_GetById_ReturnsStringCustomerNotFound_WhenGivenId10() throws Exception {
		
		mockMvc.perform(get("/customer/10"))
				.andExpect(status().isNotFound())
				.andExpect(content().string("Customer not found"));
	}
	
	@Test
	public void test_Delete_ReturnsCorrectStatusAndResponse_WhenGivenId1() throws Exception {
		
		Customer customer = new Customer();
		customer.setCustomerId(1L);
		customer.setFirstName("test");
		customer.setLastName("testing");
		
		when(repository.findById(1L)).thenReturn(Optional.of(customer));
		
		mockMvc.perform(delete("/customer/delete/1"))
				.andExpect(status().isOk())
				.andExpect(content().string("Customer with id 1 deleted"));
	}
	
	@Test
	public void test_Delete_ReturnsStringCustomerNotFound_WhenGivenId10() throws Exception {
		
		mockMvc.perform(delete("/customer/delete/10"))
				.andExpect(status().isNotFound())
				.andExpect(content().string("Customer not found"));
	}
	
	@Test
	public void test_Save_ReturnsCorrectStatusAndResponse_WhenGivenValidCustomer() throws Exception {
		
		Customer customer = new Customer();
		customer.setCustomerId(1L);
		customer.setFirstName("test");
		customer.setLastName("testing");
		
		when(repository.save(customer)).thenReturn(customer);
		
		ObjectMapper mapper = new ObjectMapper();
	    mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
	    ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
	    String requestJson = ow.writeValueAsString(customer);
		
		this.mockMvc.perform(post("/customer/save").contentType(APPLICATION_JSON_UTF8).content(requestJson))
		.andExpect(status().isOk())
		.andExpect(content().json("{'customerId':1, 'firstName':'test', 'lastName':'testing'}"));
	}
	
	@Test
	public void test_Update_ReturnsCorrectStatusAndResponse_WhenGivenValidCustomerDTO() throws Exception {
		
		CustomerDTO customerDTO = new CustomerDTO();
		customerDTO.setCustomerId(1L);
		customerDTO.setFirstName("updated");
		customerDTO.setLastName("tested");
		
		Customer customer = new Customer();
		customer.setCustomerId(1L);
		customer.setFirstName("updated");
		customer.setLastName("tested");
		
		when(repository.save(customer)).thenReturn(customer);
		
		ObjectMapper mapper = new ObjectMapper();
	    mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
	    ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
	    String requestJson = ow.writeValueAsString(customerDTO);
		
		this.mockMvc.perform(put("/customer/1/update").contentType(APPLICATION_JSON_UTF8).content(requestJson))
		.andExpect(status().isOk())
		.andExpect(content().json("{'customerId':1, 'firstName':'updated', 'lastName':'tested'}"));
	}

}
