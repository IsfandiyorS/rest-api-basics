package com.epam.esm.dao;

import com.epam.esm.dto.CrudDto;
import com.epam.esm.dto.GenericDto;
import com.epam.esm.entity.BaseDomain;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface GenericCrudDao<T extends BaseDomain> extends GenericDao {
    Long save(T item);

    default void update(Map<String, String> item) {
    }

    void removeById(Long id);

    Optional<T> findById(Long id);

    List<T> getAll();
}
