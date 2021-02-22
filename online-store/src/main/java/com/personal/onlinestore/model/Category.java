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

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Category {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long categoryId;
	
	@NotEmpty(message = "Please enter a valid category name")
	@Column(name = "category_name")
	private String name;
	
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
