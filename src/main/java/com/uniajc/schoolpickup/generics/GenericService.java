package com.uniajc.schoolpickup.generics;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public abstract class GenericService<T> {

    public abstract List<T> findAllEntities();

    public abstract Optional<T> findEntityById(Long id);

    public abstract T saveEntity(T entity);

    public abstract void deleteEntity(Long id);

    public abstract Optional<T> updateEntity(Long id, T entity);
}
