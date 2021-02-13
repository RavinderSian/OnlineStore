package com.personal.onlinestore.controller;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
import com.personal.onlinestore.model.Order;
import com.personal.onlinestore.repository.OrderRepository;

@AutoConfigureMockMvc
@SpringBootTest
public class OrderControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	OrderController controller;
	
	@MockBean
	OrderRepository repository;
	
	public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

	@Test
	public void test_Controller_IsNotNull() {
		assertNotNull(controller);
	}
	
	@Test
	public void test_GetById_ReturnsCorrectStatusAndResponse_WhenGivenId1() throws Exception {
		
		Order order = new Order();
		order.setOrderId(1L);
		
		when(repository.findById(1L)).thenReturn(Optional.of(order));
		
		mockMvc.perform(get("/order/1"))
				.andExpect(status().isOk())
				.andExpect(content().json("{'orderId':1}"));
	}
	
	@Test
	public void test_GetById_ReturnsStringOrderNotFound_WhenGivenId10() throws Exception {
		
		mockMvc.perform(get("/order/10"))
				.andExpect(status().isNotFound())
				.andExpect(content().string("Order not found"));
	}
	
	@Test
	public void test_delete_ReturnsCorrectStatusAndResponse_WhenGivenId1() throws Exception {
		
		Order order = new Order();
		order.setOrderId(1L);
		
		when(repository.findById(1L)).thenReturn(Optional.of(order));
		
		mockMvc.perform(delete("/order/delete/1"))
				.andExpect(status().isOk())
				.andExpect(content().string("Order with id 1 deleted"));
	}
	
	@Test
	public void test_delete_ReturnsStringOrderNotFound_WhenGivenId10() throws Exception {
		
		mockMvc.perform(delete("/order/delete/10"))
				.andExpect(status().isNotFound())
				.andExpect(content().string("Order not found"));
	}
	
	@Test
	public void test_Save_ReturnsCorrectStatusAndResponse_WhenGivenValidCategory() throws Exception {
		
		Order order = new Order();
		order.setOrderId(1L);
		
		when(repository.save(order)).thenReturn(order);
		
		ObjectMapper mapper = new ObjectMapper();
	    mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
	    ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
	    String requestJson = ow.writeValueAsString(order);
		
		this.mockMvc.perform(post("/order/save").contentType(APPLICATION_JSON_UTF8).content(requestJson))
		.andExpect(status().isOk())
		.andExpect(content().json("{'orderId':1}"));
	}


}
