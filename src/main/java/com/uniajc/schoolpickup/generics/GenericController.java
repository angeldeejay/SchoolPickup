package com.uniajc.schoolpickup.generics;

import java.lang.Long;
import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.RestController;

@RestController
public abstract class GenericController<T> {
	public abstract List<T> getAll();

	public abstract Optional<T> getById(Long id);

	public abstract T add(T entity);

	public abstract void delete(Long id);

	public abstract Optional<T> update(Long id, T entity);

	public abstract String test();
}