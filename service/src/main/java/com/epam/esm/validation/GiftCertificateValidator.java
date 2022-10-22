package com.epam.esm.validation;

import com.epam.esm.dto.impl.GiftCertificateCreateDto;
import com.epam.esm.dto.impl.GiftCertificateUpdateDto;
import com.epam.esm.enums.ErrorCodes;
import com.epam.esm.exceptions.ValidationException;
import com.epam.esm.utils.BaseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

import static java.lang.String.format;

@Component
public class GiftCertificateValidator implements BaseValidator<GiftCertificateCreateDto, GiftCertificateUpdateDto> {

    private static final int NAME_MAX_LENGTH = 50;
    private static final int NAME_MIN_LENGTH = 1;
    private static final int DESCRIPTION_MAX_LENGTH = 200;
    private static final int DESCRIPTION_MIN_LENGTH = 1;
    private static final BigDecimal PRICE_MIN_VALUE = BigDecimal.ONE;
    private static final BigDecimal PRICE_MAX_VALUE = new BigDecimal(Integer.MAX_VALUE);
    private static final int DURATION_MIN_VALUE = 1;
    private final BaseUtils baseUtils;

    @Autowired
    public GiftCertificateValidator(BaseUtils baseUtils) {
        this.baseUtils = baseUtils;
    }

    @Override
    public boolean isCreateDtoValid(GiftCertificateCreateDto createDto) {
        return isNameValid(createDto.getName()) &&
                isDescriptionValid(createDto.getDescription()) &&
                isDurationValid(createDto.getDuration()) &&
                isPriceValid(createDto.getPrice());
    }

    @Override
    public boolean isUpdateDtoValid(GiftCertificateUpdateDto updateDto) {
        return BaseValidator.super.isUpdateDtoValid(updateDto);
    }

    public boolean isNameValid(String name) {
        if (baseUtils.isEmpty(name)) {
            throw new ValidationException(format(ErrorCodes.OBJECT_IS_NULL.message, "Gift Certificate name"));
        }
        if (!baseUtils.isStringLengthValid(name, NAME_MIN_LENGTH, NAME_MAX_LENGTH)) {
            throw new ValidationException(format(ErrorCodes.FIELD_LENGTH_SHOULD_BE.message, "Gift Certificate name", NAME_MIN_LENGTH, NAME_MAX_LENGTH));
        }
        return true;
    }

    public boolean isDescriptionValid(String description) {
        if (baseUtils.isEmpty(description)) {
            throw new ValidationException(format(ErrorCodes.OBJECT_IS_NULL.message, "Gift Certificate description"));
        }
        if (!baseUtils.isStringLengthValid(description, DESCRIPTION_MIN_LENGTH, DESCRIPTION_MAX_LENGTH)) {
            throw new ValidationException(format(ErrorCodes.FIELD_LENGTH_SHOULD_BE.message, "Gift Certificate description", DESCRIPTION_MIN_LENGTH, DESCRIPTION_MAX_LENGTH));
        }
        return true;
    }

    public boolean isPriceValid(BigDecimal price) {
        if (baseUtils.isEmpty(price)) {
            throw new ValidationException(format(ErrorCodes.OBJECT_IS_NULL.message, "Gift Certificate description"));
        }
        if (!(price.compareTo(PRICE_MIN_VALUE) >= 0 && price.compareTo(PRICE_MAX_VALUE) <= 0)) {
            throw new ValidationException(format(ErrorCodes.FIELD_LENGTH_SHOULD_BE.message, "Gift Certificate price", PRICE_MIN_VALUE, PRICE_MAX_VALUE));
        }
        return true;
    }

    public boolean isDurationValid(int duration) {
        if (duration < DURATION_MIN_VALUE) {
            throw new ValidationException(format(ErrorCodes.OBJECT_SHOULD_BE.message, "Gift Certificate duration", "more than 0"));
        }
        return true;
    }

}
