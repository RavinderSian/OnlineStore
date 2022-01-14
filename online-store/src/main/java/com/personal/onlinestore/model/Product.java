package com.personal.onlinestore.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long productId;
	
	@NotEmpty(message = "Please enter a valid product name")
	@Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Please enter a valid product name")
	@Column(name = "product_name")
	private String name;
	
	@Column(name = "price")
	private BigDecimal price;
	
	@Column(name = "delivery_time_days")
	private Integer deliveryTimeInDays;
	
	@ManyToOne
	@JoinColumn(name = "category_id")
	private Category category;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "order_id")
	private Order order;
}
