package com.personal.onlinestore.controller;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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
import com.personal.onlinestore.model.Customer;
import com.personal.onlinestore.model.CustomerDTO;
import com.personal.onlinestore.services.CustomerService;

@SpringBootTest
@AutoConfigureMockMvc
class CustomerControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	CustomerController controller;
	
	@MockBean
	CustomerService service;
	
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
		
		CustomerDTO customerDTO = new CustomerDTO();
		customerDTO.setCustomerId(1L);
		customerDTO.setFirstName("test");
		customerDTO.setLastName("testing");
		
		when(service.findById(1L)).thenReturn(Optional.of(customerDTO));
		
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
		
		CustomerDTO customerDTO = new CustomerDTO();
		customerDTO.setCustomerId(1L);
		customerDTO.setFirstName("test");
		customerDTO.setLastName("testing");
		
		when(service.findById(1L)).thenReturn(Optional.of(customerDTO));
		
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
		customer.setCardNumber("379763005117730");
		customer.setPostCode("UB1 1EP");
		
		CustomerDTO customerDTO = new CustomerDTO();
		customerDTO.setCustomerId(1L);
		customerDTO.setFirstName("test");
		customerDTO.setLastName("testing");
		
		when(service.saveAndReturnCustomerDTO(customer)).thenReturn(customerDTO);
		
		ObjectMapper mapper = new ObjectMapper();
	    mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
	    ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
	    String requestJson = ow.writeValueAsString(customer);
		
		this.mockMvc.perform(post("/customer/save").contentType(MediaType.APPLICATION_JSON_VALUE).content(requestJson))
		.andExpect(status().isOk())
		.andExpect(content().json("{'customerId':1, 'firstName':'test', 'lastName':'testing'}"));
	}
	
	@Test
	public void test_Save_ReturnsCorrectStatusAndResponse_WhenGivenInvalidCustomer() throws Exception {
		
		Customer customer = new Customer();
		customer.setCustomerId(1L);
		customer.setFirstName("test");
		customer.setPostCode("UB1 1EP");
				
		ObjectMapper mapper = new ObjectMapper();
	    mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
	    ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
	    String requestJson = ow.writeValueAsString(customer);
		
		this.mockMvc.perform(post("/customer/save").contentType(MediaType.APPLICATION_JSON_VALUE).content(requestJson))
		.andExpect(status().isBadRequest())
		.andExpect(content().string("{\"lastName\":\"Please enter a valid last name\"}"));
	}
	
	@Test
	public void test_Update_ReturnsCorrectStatusAndResponse_WhenGivenValidCustomer() throws Exception {
		
		Customer customer = new Customer();
		customer.setCustomerId(1L);
		customer.setFirstName("updated");
		customer.setLastName("tested");
		customer.setCardNumber("379763005117730");
		customer.setPostCode("UB1 1EP");
		
		CustomerDTO customerDTO = new CustomerDTO();
		customerDTO.setCustomerId(1L);
		customerDTO.setFirstName("updated");
		customerDTO.setLastName("tested");
		
		when(service.updateCustomerByCustomer(1L, customer)).thenReturn(customerDTO);
		
		ObjectMapper mapper = new ObjectMapper();
	    mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
	    ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
	    String requestJson = ow.writeValueAsString(customer);
		
		this.mockMvc.perform(put("/customer/1/update").contentType(MediaType.APPLICATION_JSON_VALUE).content(requestJson))
		.andExpect(status().isOk())
		.andExpect(content().json("{'customerId':1, 'firstName':'updated', 'lastName':'tested'}"));
	}
	
	@Test
	public void test_Update_ReturnsCorrectStatusAndResponse_WhenGivenInvalidCustomer() throws Exception {
		
		Customer customer = new Customer();
		customer.setCustomerId(1L);
		customer.setFirstName("updated");
		customer.setCardNumber("379763005117730");
		customer.setPostCode("UB1 1EP");
				
		ObjectMapper mapper = new ObjectMapper();
	    mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
	    ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
	    String requestJson = ow.writeValueAsString(customer);
		
		this.mockMvc.perform(put("/customer/1/update").contentType(MediaType.APPLICATION_JSON_VALUE).content(requestJson))
		.andExpect(status().isBadRequest())
		.andExpect(content().string("{\"lastName\":\"Please enter a valid last name\"}"));
	}

}
