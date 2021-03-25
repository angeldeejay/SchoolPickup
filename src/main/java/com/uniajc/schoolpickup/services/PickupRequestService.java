package com.uniajc.schoolpickup.services;

import com.uniajc.schoolpickup.entities.Parent;
import com.uniajc.schoolpickup.entities.PickupRequest;
import com.uniajc.schoolpickup.entities.Student;
import com.uniajc.schoolpickup.generics.GenericService;
import com.uniajc.schoolpickup.repositories.PickupRequestRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PickupRequestService extends GenericService<PickupRequest> {

  @Autowired PickupRequestRepository repository;

  @Override
  public List<PickupRequest> findAllEntities() {
    return repository.findAll();
  }

  @Override
  public Optional<PickupRequest> findEntityById(Long id) {
    return repository.findById(id);
  }

  @Override
  public PickupRequest saveEntity(PickupRequest entity) {
    if (entity != null) {
      return repository.save(entity);
    }
    return new PickupRequest();
  }

  @Override
  public void deleteEntity(Long id) {
    if (repository.findById(id).isPresent()) {
      repository.deleteById(id);
    }
  }

  @Override
  public Optional<PickupRequest> updateEntity(Long id, PickupRequest data) {
    Optional<PickupRequest> entity = repository.findById(id);
    if (entity.isPresent()) {
      PickupRequest entityTarget = entity.get();
      entityTarget.setSlot(data.getSlot());
      entityTarget.setParent(data.getParent());
      entityTarget.setStudent(data.getStudent());
      return Optional.of(repository.save(entityTarget));
    }
    return Optional.empty();
  }

  public List<PickupRequest> findPending() {
    return repository.findPendingRequests();
  }

  public List<PickupRequest> findPending(Student student) {
    return repository.findPendingRequests(student);
  }

  public List<PickupRequest> findPending(Parent parent) {
    return repository.findPendingRequests(parent);
  }

  public void deleteAllAssociations(Student student) {
    repository.findByEntity(student).forEach((pr) -> repository.deleteById(pr.getId()));
  }

  public void deleteAllAssociations(Parent parent) {
    repository.findByEntity(parent).forEach((pr) -> repository.deleteById(pr.getId()));
  }
}