package com.epam.esm.service;

import com.epam.esm.dao.GenericDao;
import com.epam.esm.dto.CrudDto;
import com.epam.esm.dto.GenericDto;
import com.epam.esm.entity.Identifiable;

import java.util.List;

public abstract class AbstractCrudService<T extends Identifiable, D extends GenericDto, CR extends CrudDto, UP extends CrudDto, R extends GenericDao> {

    public D get(Long id) {
        return null;
    }

    public List<D> getAll() {
        return null;
    }

    public GenericDto create(CR dto) {
        return null;
    }

    public Boolean update(UP dto) {
        return null;
    }

    public Boolean delete(Long id) {
        return null;
    }

}
