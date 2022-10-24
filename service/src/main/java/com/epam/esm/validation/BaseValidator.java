package com.epam.esm.validation;

import com.epam.esm.dto.CrudDto;

import java.util.Map;

/**
 * @param <CR>
 * @param <UP>
 */
public interface BaseValidator<CR extends CrudDto, UP extends CrudDto> {

    boolean isCreateDtoValid(CR createDto);

    default boolean isUpdateDtoValid(UP updateDto, Map<String , String> updateFieldsMap){return false;};

}
