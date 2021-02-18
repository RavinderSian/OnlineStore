package com.personal.onlinestore.services;

import java.util.Optional;

import javax.validation.Valid;

public interface CrudService<T, ID> {
	
	T save(T t);
	void delete(T t);
	Optional<T> findById(ID id);

}
