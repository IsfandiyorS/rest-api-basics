package com.epam.esm.service;

import com.epam.esm.entity.Identifiable;
import com.epam.esm.dto.CrudDto;
import com.epam.esm.dto.GenericDto;
import org.springframework.http.ResponseEntity;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @param <T>
 * @param <D>
 * @param <CR>
 * @param <UP>
 */
public interface GenericCrudService<T extends Identifiable, D extends GenericDto, CR extends CrudDto, UP extends CrudDto, ID extends Serializable> extends AbstractService {
    ResponseEntity<D> get(ID id);

    ResponseEntity<List<D>> getAll();

    ResponseEntity<GenericDto> create(@NotNull CR dto);

    default ResponseEntity<D> update(@NotNull UP dto) {
        return null;
    }

    ResponseEntity<Boolean> delete(@NotNull ID id);
}