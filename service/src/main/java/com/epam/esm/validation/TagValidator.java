package com.epam.esm.validation;

import com.epam.esm.entity.Tag;
import com.epam.esm.enums.ErrorCodes;
import com.epam.esm.exceptions.ValidationException;
import lombok.experimental.UtilityClass;

import java.util.List;

import static java.lang.String.format;

/**
 * Class {@code TagValidator} provides methods to validate fields of {@link com.epam.esm.entity.Tag}.
 *
 * @author Sultonov Isfandiyor
 * @version 1.0
 */
@UtilityClass
public class TagValidator {
    private static final int NAME_MAX_LENGTH = 30;
    private static final int NAME_MIN_LENGTH = 1;


    /**
     * Validate all fields of gift certificate which will send for create gift certificate.
     *
     * @param createEntity is entity thrown from controller.
     */
    public void isCreateEntityValid(Tag createEntity) throws ValidationException {
        if (BaseValidationUtils.isEmptyString(createEntity.getName())) {
            throw new ValidationException(format(ErrorCodes.OBJECT_SHOULD_BE.message, "name", "written"));
        }
        if (!BaseValidationUtils.isStringLengthValid(createEntity.getName(), NAME_MIN_LENGTH, NAME_MAX_LENGTH)) {
            throw new ValidationException(format(ErrorCodes.FIELD_LENGTH_SHOULD_BE.message, "Name",
                    NAME_MIN_LENGTH, NAME_MAX_LENGTH));
        }
    }

    /**
     * Validate exist tags of gift certificate.
     *
     * @param tagList the list of tags
     */
    public void validateListOfTags(List<Tag> tagList) throws ValidationException {
        if (BaseValidationUtils.isEmptyList(tagList)) {
            return;
        }
        for (Tag tag : tagList) {
            isCreateEntityValid(tag);
        }
    }

}
