package com.epam.esm.service;

import com.epam.esm.dto.CrudDto;
import com.epam.esm.dto.GenericDto;
import com.epam.esm.entity.Identifiable;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

/**
 * @param <T>
 * @param <D>
 * @param <CR>
 * @param <UP>
 */
public interface GenericCrudService<T extends Identifiable, D extends GenericDto, CR extends CrudDto, UP extends CrudDto, ID extends Serializable> extends AbstractService {
    D get(ID id);

    List<D> getAll();

    GenericDto create(CR dto);

    default Boolean update(UP dto) {
        return null;
    }

    Boolean delete(ID id);

    void validate(Optional<T> entity, Long id, String entityName);
}