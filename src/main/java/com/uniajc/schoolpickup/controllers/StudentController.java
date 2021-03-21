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

import com.uniajc.schoolpickup.entities.Student;
import com.uniajc.schoolpickup.generics.GenericController;
import com.uniajc.schoolpickup.services.StudentService;

@RestController
@RequestMapping(value = "/students")
public class StudentController extends GenericController<Student> {

    @Autowired
    StudentService studentService;

    // Route: GET /students
    @Override
    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<Student> getAll() {
        return studentService.findAllEntities();
    }

    // Route: GET /students/{id}
    @Override
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Optional<Student> getById(@PathVariable Long id) {
        return studentService.findEntityById(id);
    }

    // Route: POST /students
    @Override
    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    public Student add(Student student) {
        return studentService.saveEntity(student);
    }

    // Route: DELETE /students/{id}
    @Override
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        studentService.deleteEntity(id);
    }

    // Route: PUT /students/{id}
    @Override
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Optional<Student> update(@PathVariable Long id, Student student) {
        return studentService.updateEntity(id, student);
    }

    // Route: GET /test
    @Override
    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String test() {
        return "Test done";
    }
}
