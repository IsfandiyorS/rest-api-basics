package com.epam.esm.validation;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.enums.ErrorCodes;
import com.epam.esm.exceptions.ValidationException;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.util.Map;

import static com.epam.esm.constant.GiftCertificateColumn.*;
import static java.lang.String.format;

/**
 * Class {@code GiftCertificateValidator} provides methods to validate
 * fields of {@link com.epam.esm.entity.GiftCertificate}.
 *
 * @author Sultonov Isfandiyor
 * @version 1.0
 */
@UtilityClass
public class GiftCertificateValidator {

    private static final int NAME_MAX_LENGTH = 50;
    private static final int NAME_MIN_LENGTH = 1;
    private static final int DESCRIPTION_MAX_LENGTH = 200;
    private static final int DESCRIPTION_MIN_LENGTH = 1;
    private static final BigDecimal PRICE_MIN_VALUE = BigDecimal.ONE;
    private static final BigDecimal PRICE_MAX_VALUE = new BigDecimal(Integer.MAX_VALUE);
    private static final int DURATION_MIN_VALUE = 1;

    /**
     * Validate all fields of gift certificate which will send for create gift certificate.
     *
     * @param createEntity the gift certificate
     */
    public void isCreateEntityValid(GiftCertificate createEntity) throws ValidationException {
        if (isNameValid(createEntity.getName()) && isDescriptionValid(createEntity.getDescription())
                && isDurationValid(createEntity.getDuration())) {
            isPriceValid(createEntity.getPrice());
        }
    }

    /**
     * Validate fields which required for update gift certificate.
     *
     * @param updateEntity the gift certificate
     * @param updateFieldsMap used for storing fields if they will send.
     */
    public boolean isUpdateEntityValid(GiftCertificate updateEntity, Map<String, String> updateFieldsMap) {
        if (!BaseValidationUtils.isEmptyString(updateEntity.getName())) {
            updateFieldsMap.put(NAME, updateEntity.getName());
        }
        if (!BaseValidationUtils.isEmptyString(updateEntity.getDescription())) {
            updateFieldsMap.put(DESCRIPTION, updateEntity.getDescription());
        }
        if (!BaseValidationUtils.isEmptyObject(updateEntity.getDuration())) {
            updateFieldsMap.put(DURATION, updateEntity.getDuration().toString());
        }
        if (!BaseValidationUtils.isEmptyObject(updateEntity.getPrice())) {
            updateFieldsMap.put(PRICE, updateEntity.getPrice().toString());
        }
        if (!BaseValidationUtils.isEmptyObject(updateEntity.getId())) {
            updateFieldsMap.put(ID, updateEntity.getId().toString());
        }
        return updateFieldsMap.size() > 0 || updateEntity.getTagList().size() > 0;
    }

    public boolean isNameValid(String name) throws ValidationException {
        if (BaseValidationUtils.isEmptyString(name)) {
            throw new ValidationException(format(ErrorCodes.OBJECT_IS_NULL.message, "name"));
        }
        if (!BaseValidationUtils.isStringLengthValid(name, NAME_MIN_LENGTH, NAME_MAX_LENGTH)) {
            throw new ValidationException(format(ErrorCodes.FIELD_LENGTH_SHOULD_BE.message, "Name", NAME_MIN_LENGTH, NAME_MAX_LENGTH));
        }
        return true;
    }

    public boolean isDescriptionValid(String description) throws ValidationException {
        if (BaseValidationUtils.isEmptyString(description)) {
            throw new ValidationException(format(ErrorCodes.OBJECT_IS_NULL.message, "description"));
        }
        if (!BaseValidationUtils.isStringLengthValid(description, DESCRIPTION_MIN_LENGTH, DESCRIPTION_MAX_LENGTH)) {
            throw new ValidationException(format(ErrorCodes.FIELD_LENGTH_SHOULD_BE.message, "Description", DESCRIPTION_MIN_LENGTH, DESCRIPTION_MAX_LENGTH));
        }
        return true;
    }

    public void isPriceValid(BigDecimal price) throws ValidationException {
        if (BaseValidationUtils.isEmptyObject(price)) {
            throw new ValidationException(format(ErrorCodes.OBJECT_IS_NULL.message, "description"));
        }
        if (!(price.compareTo(PRICE_MIN_VALUE) >= 0 && price.compareTo(PRICE_MAX_VALUE) <= 0)) {
            throw new ValidationException(format(ErrorCodes.FIELD_LENGTH_SHOULD_BE.message, "Price", PRICE_MIN_VALUE, PRICE_MAX_VALUE));
        }
    }

    public boolean isDurationValid(int duration) throws ValidationException {
        if (duration < DURATION_MIN_VALUE) {
            throw new ValidationException(format(ErrorCodes.OBJECT_SHOULD_BE.message, "duration", "more than 0"));
        }
        return true;
    }

}
