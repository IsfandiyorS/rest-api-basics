package com.epam.esm.service;

import com.epam.esm.dao.GenericDao;
import com.epam.esm.dto.CrudDto;
import com.epam.esm.dto.GenericDto;
import com.epam.esm.entity.Identifiable;
import org.springframework.http.ResponseEntity;

import javax.validation.constraints.NotNull;
import java.util.List;

public abstract class AbstractCrudService<T extends Identifiable, D extends GenericDto, CR extends CrudDto, UP extends CrudDto, R extends GenericDao> {

    public ResponseEntity<D> get(Long id) {
        return null;
    }

    public ResponseEntity<List<D>> getAll() {
        return null;
    }

    public ResponseEntity<GenericDto> create(@NotNull CR dto) {
        return null;
    }

    public ResponseEntity<D> update(@NotNull UP dto) {
        return null;
    }

    public ResponseEntity<Boolean> delete(@NotNull Long id) {
        return null;
    }


}
