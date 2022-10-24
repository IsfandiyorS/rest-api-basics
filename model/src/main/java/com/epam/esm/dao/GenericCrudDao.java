package com.epam.esm.dao;

import com.epam.esm.dto.CrudDto;
import com.epam.esm.dto.GenericDto;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface GenericCrudDao<D extends GenericDto, CR extends CrudDto> extends GenericDao {
    Long save(CR item);

    default void update(Map<String, String> item) {
    }

    void removeById(Long id);

    Optional<D> findById(Long id);

    List<D> getAll();
}
