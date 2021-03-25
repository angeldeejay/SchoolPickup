package com.uniajc.schoolpickup.services;

import com.uniajc.schoolpickup.entities.Parent;
import com.uniajc.schoolpickup.generics.GenericService;
import com.uniajc.schoolpickup.repositories.ParentRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ParentService extends GenericService<Parent> {

  @Autowired ParentRepository repository;

  @Override
  public List<Parent> findAllEntities() {
    return repository.findAll();
  }

  @Override
  public Optional<Parent> findEntityById(Long id) {
    return repository.findById(id);
  }

  @Override
  public Parent saveEntity(Parent entity) {
    if (entity != null) {
      return repository.save(entity);
    }
    return new Parent();
  }

  @Override
  public void deleteEntity(Long id) {
    if (repository.findById(id).isPresent()) {
      repository.deleteById(id);
    }
  }

  @Override
  public Optional<Parent> updateEntity(Long id, Parent data) {
    Optional<Parent> entity = repository.findById(id);
    if (entity.isPresent()) {
      Parent entityTarget = entity.get();
      entityTarget.setEmail(data.getEmail());
      entityTarget.setUser(data.getUser());
      return Optional.of(repository.save(entityTarget));
    }
    return Optional.empty();
  }
}
