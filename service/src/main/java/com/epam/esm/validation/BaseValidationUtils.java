package com.epam.esm.validation;

import lombok.experimental.UtilityClass;

import java.util.List;

/**
 * Class {@code BaseValidationUtils} used for basic Validation operations
 *
 * @author Sultonov Isfandiyor
 * @version 1.0
 */
@UtilityClass
public class BaseValidationUtils {

    public boolean isEmptyString(String s) {
        return s == null || s.isEmpty();
    }

    public boolean isEmptyList(List<?> items) {
        return items == null || items.isEmpty();
    }

    public boolean isEmptyObject(Object l) {
        return l == null;
    }

    public boolean isStringLengthValid(String field, int minLength, int maxLength){
        return field.length()>=minLength && field.length()<=maxLength;
    }

}
