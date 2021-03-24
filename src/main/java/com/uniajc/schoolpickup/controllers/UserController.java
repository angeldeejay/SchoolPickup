package com.uniajc.schoolpickup.controllers;

import com.uniajc.schoolpickup.entities.User;
import com.uniajc.schoolpickup.generics.GenericController;
import com.uniajc.schoolpickup.services.UserService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/users")
public class UserController extends GenericController<User> {

  @Autowired UserService userService;

  // Route: GET /users
  @Override
  @RequestMapping(value = "", method = RequestMethod.GET)
  public List<User> getAll() {
    return userService.findAllEntities();
  }

  // Route: GET /users/{id}
  @Override
  @RequestMapping(value = "/{id}", method = RequestMethod.GET)
  public Optional<User> getById(@PathVariable Long id) {
    return userService.findEntityById(id);
  }

  // Route: POST /users
  @Override
  @RequestMapping(value = "", method = RequestMethod.POST)
  @ResponseStatus(value = HttpStatus.CREATED)
  public User add(User user) {
    return userService.saveEntity(user);
  }

  // Route: DELETE /users/{id}
  @Override
  @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
  @ResponseStatus(value = HttpStatus.NO_CONTENT)
  public void delete(@PathVariable Long id) {
    userService.deleteEntity(id);
  }

  // Route: PUT /users/{id}
  @Override
  @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
  public Optional<User> update(@PathVariable Long id, User user) {
    return userService.updateEntity(id, user);
  }

  // Route: GET /test
  @Override
  @RequestMapping(value = "/test", method = RequestMethod.GET)
  public String test() {
    return "Test done";
  }
}
