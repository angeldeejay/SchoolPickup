package com.uniajc.schoolpickup.services;

import com.uniajc.schoolpickup.entities.User;
import com.uniajc.schoolpickup.generics.GenericService;
import com.uniajc.schoolpickup.repositories.UserRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService extends GenericService<User> {

  @Autowired UserRepository repository;

  @Override
  public List<User> findAllEntities() {
    return repository.findAll();
  }

  @Override
  public Optional<User> findEntityById(Long id) {
    return repository.findById(id);
  }

  @Override
  public User saveEntity(User entity) {
    if (entity != null) {
      return repository.save(entity);
    }
    return new User();
  }

  @Override
  public void deleteEntity(Long id) {
    if (repository.findById(id).isPresent()) {
      repository.deleteById(id);
    }
  }

  @Override
  public Optional<User> updateEntity(Long id, User data) {
    Optional<User> entity = repository.findById(id);
    if (entity.isPresent()) {
      User entityTarget = entity.get();
      entityTarget.setFirstName(data.getFirstName());
      entityTarget.setLastName(data.getLastName());
      entityTarget.setIdentification(data.getIdentification());
      entityTarget.setPassword(data.getPassword());
      return Optional.of(repository.save(entityTarget));
    }
    return Optional.empty();
  }

  public User findEntityByIdentification(String identification) {
    return repository.findByIdentification(identification);
  }
}
