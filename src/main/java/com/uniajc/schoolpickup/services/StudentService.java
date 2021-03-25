package com.uniajc.schoolpickup.services;

import com.uniajc.schoolpickup.entities.Parent;
import com.uniajc.schoolpickup.entities.Student;
import com.uniajc.schoolpickup.generics.GenericService;
import com.uniajc.schoolpickup.repositories.StudentRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentService extends GenericService<Student> {

  @Autowired StudentRepository repository;

  @Override
  public List<Student> findAllEntities() {
    return repository.findAll();
  }

  @Override
  public Optional<Student> findEntityById(Long id) {
    return repository.findById(id);
  }

  @Override
  public Student saveEntity(Student entity) {
    if (entity != null) {
      return repository.save(entity);
    }
    return new Student();
  }

  @Override
  public void deleteEntity(Long id) {
    if (repository.findById(id).isPresent()) {
      repository.deleteById(id);
    }
  }

  @Override
  public Optional<Student> updateEntity(Long id, Student data) {
    Optional<Student> entity = repository.findById(id);
    if (entity.isPresent()) {
      Student entityTarget = entity.get();
      entityTarget.setFirstName(data.getFirstName());
      entityTarget.setLastName(data.getLastName());
      entityTarget.setParent(data.getParent());
      return Optional.of(repository.save(entityTarget));
    }
    return Optional.empty();
  }

  public List<Student> findByParent(Parent parent) {
	return repository.findByParent(parent);
  }
}
