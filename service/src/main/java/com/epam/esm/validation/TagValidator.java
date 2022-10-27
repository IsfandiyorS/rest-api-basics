package com.epam.esm.validation;

import com.epam.esm.dto.impl.TagCreateDto;
import com.epam.esm.dto.impl.TagUpdateDto;
import com.epam.esm.enums.ErrorCodes;
import com.epam.esm.exceptions.ValidationException;
import com.epam.esm.utils.BaseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static java.lang.String.format;

@Component
public class TagValidator implements BaseValidator<TagCreateDto, TagUpdateDto>{

    private static final int NAME_MAX_LENGTH = 30;
    private static final int NAME_MIN_LENGTH = 1;
    private final BaseUtils baseUtils;

    @Autowired
    public TagValidator(BaseUtils baseUtils) {
        this.baseUtils = baseUtils;
    }

    @Override
    public void isCreateDtoValid(TagCreateDto createDto) {
        if (baseUtils.isEmpty(createDto.getName())){
            throw new ValidationException(format(ErrorCodes.OBJECT_SHOULD_BE.message, "Tag name", "written"));
        }
        int length=createDto.getName().length();
        if(!baseUtils.isStringLengthValid(createDto.getName(), NAME_MIN_LENGTH, NAME_MAX_LENGTH)){
            throw new ValidationException(format(ErrorCodes.FIELD_LENGTH_SHOULD_BE.message, "Tag name",
                    NAME_MIN_LENGTH, NAME_MAX_LENGTH));
        }
    }

}
