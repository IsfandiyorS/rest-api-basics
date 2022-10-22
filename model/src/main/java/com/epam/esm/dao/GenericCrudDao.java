package com.epam.esm.dao;

import com.epam.esm.entity.Identifiable;

import java.util.List;
import java.util.Optional;

public interface GenericCrudDao<T extends Identifiable> extends GenericDao {
    Long save(T item);

    default void update(T item) {}

    void removeById(Long id);

    Optional<T> findById(Long id);

    List<T> getAll();
}
