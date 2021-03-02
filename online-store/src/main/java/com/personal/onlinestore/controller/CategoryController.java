package com.personal.onlinestore.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.personal.onlinestore.model.Category;
import com.personal.onlinestore.model.Product;
import com.personal.onlinestore.services.CategoryService;
import com.personal.onlinestore.services.ProductService;

@RestController
@RequestMapping("/category")
public class CategoryController implements CrudController<Category, Long> {
	
	private final CategoryService categoryService;
	private final ProductService productService;
	
	public CategoryController(CategoryService categoryService, ProductService productService) {
		this.categoryService = categoryService;
		this.productService = productService;
	}

	@Override
	public ResponseEntity<?> getById(Long id) {
		Optional<Category> categoryOptional = categoryService.findById(id);
		if (categoryOptional.isEmpty()) {
			return new ResponseEntity<String>("Category not found", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Category>(categoryOptional.get(), HttpStatus.OK);
		
	}	

	@Override
	public ResponseEntity<String> deleteById(Long id) {
		Optional<Category> categoryOptional = categoryService.findById(id);
		if (categoryOptional.isEmpty()) {
			return new ResponseEntity<String>("Category not found", HttpStatus.NOT_FOUND);
		}
		Category category = categoryOptional.get();
		categoryService.delete(category);
		return new ResponseEntity<String>("Category with id " + id + " deleted", HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> save(Category category, BindingResult bindingResult) {
		
		if (bindingResult.hasFieldErrors()) {
			Map<String, String> fieldErrorMap = new HashMap<>();
			
			bindingResult.getFieldErrors().forEach(objectError -> {
				fieldErrorMap.put(objectError.getField(), objectError.getDefaultMessage());
			});
			
			return new ResponseEntity<Map<String, String>>(fieldErrorMap, HttpStatus.BAD_REQUEST);
		}
		
		Category savedCategory = categoryService.save(category);
		return new ResponseEntity<Category>(savedCategory, HttpStatus.OK);
	}
	
	@PatchMapping("/updatename/{id}")
	public ResponseEntity<?> updateName(@PathVariable Long id, @RequestBody String name){
		Optional<Category> categoryOptional = categoryService.findById(id);
		if (categoryOptional.isEmpty()) {
			return new ResponseEntity<String>("Category not found", HttpStatus.NOT_FOUND);
		}
		Category category = categoryOptional.get();
		categoryService.updateName(category, name);
		return new ResponseEntity<Category>(category, HttpStatus.OK);
	}
	
	@GetMapping("/{id}/products")
	public ResponseEntity<?> getProducts(@PathVariable Long id){
		Optional<Category> categoryOptional = categoryService.findById(id);
		if (categoryOptional.isEmpty()) {
			return new ResponseEntity<String>("Category not found", HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<List<Product>>(categoryService.findProductsByCategoryId(id), HttpStatus.OK);
		 
	}
	
	@GetMapping("/{id}/addproduct/{productId}")
	public ResponseEntity<?> addProducts(@PathVariable Long id, @PathVariable Long productId){
		Optional<Category> categoryOptional = categoryService.findById(id);
		if (categoryOptional.isEmpty()) {
			return new ResponseEntity<String>("Category not found", HttpStatus.NOT_FOUND);
		}
		
		Optional<Product> productOptional = productService.findById(productId);
		if (productOptional.isEmpty()) {
			return new ResponseEntity<String>("Product not found", HttpStatus.NOT_FOUND);
		}
		
		Category category = categoryOptional.get();
		Product product = productOptional.get();
		category.addProduct(product);
		categoryService.save(category);
		
		return new ResponseEntity<String>("Product with id " + productId + " added to Category with id " + id, HttpStatus.OK);
	}

}
