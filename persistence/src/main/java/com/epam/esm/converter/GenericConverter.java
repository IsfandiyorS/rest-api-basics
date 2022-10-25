package com.epam.esm.converter;

import com.epam.esm.dto.GenericCrudDto;
import com.epam.esm.dto.GenericDto;
import com.epam.esm.entity.BaseDomain;

public interface GenericConverter<T extends BaseDomain, CD extends GenericCrudDto, D extends GenericDto>{

    T convertCreatedDtoToEntity(CD createdDto);

    D convertEntityToDto(T entity);
}
