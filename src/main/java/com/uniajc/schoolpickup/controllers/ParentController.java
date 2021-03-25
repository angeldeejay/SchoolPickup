package com.uniajc.schoolpickup.controllers;

import com.uniajc.schoolpickup.entities.Parent;
import com.uniajc.schoolpickup.generics.EntityController;
import com.uniajc.schoolpickup.services.ParentService;
import com.uniajc.schoolpickup.util.AbstractController;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@RequestMapping(value = "/parents")
public class ParentController extends AbstractController implements EntityController<Parent> {

  @Autowired ParentService parentService;

  // Route: GET /parents
  @Override
  @ResponseBody
  @RequestMapping(
      value = "",
      method = RequestMethod.GET,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public List<Parent> getAll() {
    return parentService.findAllEntities();
  }

  // Route: GET /parents/{id}
  @Override
  @ResponseBody
  @RequestMapping(
      value = "/{id}",
      method = RequestMethod.GET,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public Optional<Parent> getById(@PathVariable Long id) {
    return parentService.findEntityById(id);
  }

  // Route: POST /parents
  @Override
  @ResponseBody
  @RequestMapping(
      value = "",
      method = RequestMethod.POST,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(value = HttpStatus.CREATED)
  public Parent add(Parent parent) {
    return parentService.saveEntity(parent);
  }

  // Route: DELETE /parents/{id}
  @Override
  @RequestMapping(
      value = "/{id}",
      method = RequestMethod.DELETE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(value = HttpStatus.NO_CONTENT)
  public void delete(@PathVariable Long id) {
    parentService.deleteEntity(id);
  }

  // Route: PUT /parents/{id}
  @Override
  @ResponseBody
  @RequestMapping(
      value = "/{id}",
      method = RequestMethod.PUT,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public Optional<Parent> update(@PathVariable Long id, Parent parent) {
    return parentService.updateEntity(id, parent);
  }
}
