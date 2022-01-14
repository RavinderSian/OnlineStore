package com.personal.onlinestore.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

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
	
	@Column(name = "category_name")
	@NotBlank(message = "Please enter a valid category name")
	@Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Please enter a valid category name")
	private String name;
	
	@JsonIgnore 
	@OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
	private Set<Product> products = new HashSet<>();
	
	public void addProduct(Product product) {
		this.products.add(product); 
		product.setCategory(this);

	}
	
	public void removeProduct(Product product) {
		this.products.remove(product);
		product.setCategory(null);
	}
	
}
