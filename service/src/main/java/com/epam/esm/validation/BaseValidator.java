package com.epam.esm.validation;

import com.epam.esm.dto.CrudDto;
import com.epam.esm.dto.GenericDto;
import com.epam.esm.entity.Identifiable;

/**
 * @param <CR>
 * @param <UP>
 */
public interface BaseValidator<CR extends CrudDto, UP extends CrudDto> {

    boolean isCreateDtoValid(CR createDto);

    default boolean isUpdateDtoValid(UP updateDto){return false;};

}
