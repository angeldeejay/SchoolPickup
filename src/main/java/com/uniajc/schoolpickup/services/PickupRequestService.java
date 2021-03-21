package com.uniajc.schoolpickup.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uniajc.schoolpickup.entities.PickupRequest;
import com.uniajc.schoolpickup.generics.GenericService;
import com.uniajc.schoolpickup.repositories.PickupRequestRepository;
import com.uniajc.schoolpickup.util.Encryption;

@Service
public class PickupRequestService extends GenericService<PickupRequest> {

    @Autowired
    PickupRequestRepository repository;

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
}
