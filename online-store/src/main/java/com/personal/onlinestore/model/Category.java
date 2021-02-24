package com.personal.onlinestore.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(exclude = "products")
@NoArgsConstructor
@Entity
public class Category {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long categoryId;
	
	@NotEmpty(message = "Please enter a valid category name")
	@Column(name = "category_name")
	private String name;
	
	@JsonIgnore
	@OneToMany(mappedBy = "category")
	private Set<Product> products = new HashSet<>();
	
	public void addProduct(Product product) {
		this.getProducts().add(product);
		product.setCategory(this);
	}
	
	public void removeProduct(Product product) {
		this.getProducts().remove(product);
		product.setCategory(null);
	}
	
}
