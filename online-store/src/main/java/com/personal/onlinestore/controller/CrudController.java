package com.personal.onlinestore.controller;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface CrudController <T, ID> {

	@GetMapping("/{id}")
	ResponseEntity<?> getById(@PathVariable ID id);
	
	@DeleteMapping("/delete/{id}")
	ResponseEntity<String> deleteById(@PathVariable ID id);
	
	@PostMapping("/save")
	ResponseEntity<?> save(@RequestBody @Valid T t);
	
}
