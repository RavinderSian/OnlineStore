package com.personal.onlinestore.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(exclude = {"products", "customer"})
@NoArgsConstructor
@Entity(name = "orders") //order is reserved as a name so changed name to orders
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long orderId;
	
	@CreationTimestamp
	private LocalDateTime orderDate;
	
	@JsonIgnore
	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
	private List<Product> products = new ArrayList<>();
	
	public void addProduct(Product product) {
		this.getProducts().add(product);
		product.setOrder(this);
	}
	
	public void removeProduct(Product product) {
		this.getProducts().remove(product);
		product.setOrder(null);
	}
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "customer_id")
	private Customer customer;
	
}
