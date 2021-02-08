package com.personal.onlinestore.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long productId;
	
	@Column(name = "product_name")
	private String name;
	
	@Column(name = "price")
	private BigDecimal price;
	
	@Column(name = "delivery_time_days")
	private Integer deliveryTimeInDays;
	
}
