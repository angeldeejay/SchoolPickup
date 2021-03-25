package com.uniajc.schoolpickup.controllers;

import com.uniajc.schoolpickup.entities.Student;
import com.uniajc.schoolpickup.generics.EntityController;
import com.uniajc.schoolpickup.services.StudentService;
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
@RequestMapping(value = "/students")
public class StudentController extends AbstractController implements EntityController<Student> {

  @Autowired StudentService studentService;

  // Route: GET /students
  @Override
  @ResponseBody
  @RequestMapping(
      value = "",
      method = RequestMethod.GET,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public List<Student> getAll() {
    return studentService.findAllEntities();
  }

  // Route: GET /students/{id}
  @Override
  @ResponseBody
  @RequestMapping(
      value = "/{id}",
      method = RequestMethod.GET,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public Optional<Student> getById(@PathVariable Long id) {
    return studentService.findEntityById(id);
  }

  // Route: POST /students
  @Override
  @ResponseBody
  @RequestMapping(
      value = "",
      method = RequestMethod.POST,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(value = HttpStatus.CREATED)
  public Student add(Student student) {
    return studentService.saveEntity(student);
  }

  // Route: DELETE /students/{id}
  @Override
  @RequestMapping(
      value = "/{id}",
      method = RequestMethod.DELETE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(value = HttpStatus.NO_CONTENT)
  public void delete(@PathVariable Long id) {
    studentService.deleteEntity(id);
  }

  // Route: PUT /students/{id}
  @Override
  @ResponseBody
  @RequestMapping(
      value = "/{id}",
      method = RequestMethod.PUT,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public Optional<Student> update(@PathVariable Long id, Student student) {
    return studentService.updateEntity(id, student);
  }
}
