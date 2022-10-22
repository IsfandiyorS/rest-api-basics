package com.epam.esm.mapper;

import com.epam.esm.dto.CrudDto;
import com.epam.esm.dto.GenericDto;
import com.epam.esm.entity.Identifiable;

public interface GenericConverter<T extends Identifiable, D extends GenericDto, CR extends CrudDto, UP extends CrudDto> {

    D convertObjectToDto(T obj);

    T convertDtoToObject(CR createDto);

    T convertUpdateDtoToObject(UP updateDto);

}
