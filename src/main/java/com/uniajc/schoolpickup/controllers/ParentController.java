package com.uniajc.schoolpickup.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.uniajc.schoolpickup.entities.Parent;
import com.uniajc.schoolpickup.generics.GenericController;
import com.uniajc.schoolpickup.services.ParentService;

@RestController
@RequestMapping(value = "/parents")
public class ParentController extends GenericController<Parent> {
	@Autowired
	ParentService parentService;

	// Route: GET /parents
	@Override
	@RequestMapping(value = "", method = RequestMethod.GET)
	public List<Parent> getAll() {
		return parentService.findAllEntities();
	}

	// Route: GET /parents/{id}
	@Override
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public Optional<Parent> getById(@PathVariable Long id) {
		return parentService.findEntityById(id);
	}

	// Route: POST /parents
	@Override
	@RequestMapping(value = "", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.CREATED)
	public Parent add(Parent parent) {
		return parentService.saveEntity(parent);
	}

	// Route: DELETE /parents/{id}
	@Override
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
		parentService.deleteEntity(id);
	}

	// Route: PUT /parents/{id}
	@Override
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public Optional<Parent> update(@PathVariable Long id, Parent parent) {
		return parentService.updateEntity(id, parent);
	}

	// Route: GET /test
	@Override
	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public String test() {
		return "Test done";
	}
}