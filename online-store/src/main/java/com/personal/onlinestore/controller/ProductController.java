package com.personal.onlinestore.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.personal.onlinestore.model.Product;
import com.personal.onlinestore.services.ProductService;

@RestController
@RequestMapping("/product")
public class ProductController implements CrudController<Product, Long>{

	private final ProductService productService;

	public ProductController(ProductService productService) {
		this.productService = productService;
	}

	@Override
	public ResponseEntity<?> getById(Long id) {
		return productService.findById(id).isPresent()
		? new ResponseEntity<>(productService.findById(id).get(), HttpStatus.OK)
		: new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);
	}

	@Override
	public ResponseEntity<String> deleteById(Long id) {
		if (productService.findById(id).isPresent()) {
			productService.delete(productService.findById(id).get());
			return new ResponseEntity<>("Product with id " + id + " deleted", HttpStatus.OK);
		}
		return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);
	}

	@Override
	public ResponseEntity<?> save(Product product, BindingResult bindingResult) {
		
		if (bindingResult.hasFieldErrors()) {
			Map<String, String> fieldErrorMap = new HashMap<>();
			
			bindingResult.getFieldErrors().forEach(objectError -> 
				fieldErrorMap.put(objectError.getField(), objectError.getDefaultMessage()));
			
			return new ResponseEntity<>(fieldErrorMap, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(productService.save(product), HttpStatus.OK);
	}
	
	@PatchMapping("/updatename/{id}")
	public ResponseEntity<?> updateName(@PathVariable Long id, @RequestBody(required = false) String name){
		
		if (name == null || !name.matches("^[a-zA-Z\\s]+$")) {
			return new ResponseEntity<>("Please enter a valid name", HttpStatus.BAD_REQUEST);
		}
		
		Optional<Product> productOptional = productService.findById(id);
		
		if (productOptional.isPresent()) {
			Product product = productOptional.get();
			productService.updateName(product, name);
			return new ResponseEntity<>(product, HttpStatus.OK);
		}
		return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);
		
	}
	
}
