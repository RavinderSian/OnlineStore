 package com.personal.onlinestore.controller;

import java.util.HashMap;
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
		return categoryService.findById(id).isPresent()
		? new ResponseEntity<>(categoryService.findById(id).get(), HttpStatus.OK)
		: new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@Override
	public ResponseEntity<String> deleteById(Long id) {
		if (!categoryService.findById(id).isPresent()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		categoryService.delete(categoryService.findById(id).get());
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> save(Category category, BindingResult bindingResult) {
		
		if (bindingResult.hasFieldErrors()) {
			Map<String, String> fieldErrorMap = new HashMap<>();
			
			bindingResult.getFieldErrors().forEach(objectError -> 
				fieldErrorMap.put(objectError.getField(), objectError.getDefaultMessage()));
			
			return new ResponseEntity<>(fieldErrorMap, HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<>(categoryService.save(category), HttpStatus.OK);
	}
	
	@PatchMapping("/updatename/{id}")
	public ResponseEntity<?> updateName(@PathVariable Long id, @RequestBody(required = false) String name){
		
		if (name == null || !name.matches("^[a-zA-Z\\s]+$")) {
			return new ResponseEntity<>("Please enter a valid name", HttpStatus.BAD_REQUEST);
		}
		
		Optional<Category> categoryOptional = categoryService.findById(id);
		if (!categoryOptional.isPresent()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		Category category = categoryOptional.get();
		categoryService.updateName(category, name);
		
		return new ResponseEntity<>(category, HttpStatus.OK);
	}
	
	@GetMapping("/{id}/products")
	public ResponseEntity<?> getProducts(@PathVariable Long id){
		return categoryService.findById(id).isPresent()
		? new ResponseEntity<>(categoryService.findProductsByCategoryId(id), HttpStatus.OK)
		: new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@GetMapping("/{id}/addproduct/{productId}")
	public ResponseEntity<?> addProducts(@PathVariable Long id, @PathVariable Long productId){
		if (!categoryService.findById(id).isPresent()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		if (!productService.findById(productId).isPresent()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		Category category = categoryService.findById(id).get();
		category.addProduct(productService.findById(productId).get());
		categoryService.save(category);
		
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
