package com.epam.esm.enums;

/**
 * Enum {@code ErrorCodes} presents values which will be thrown with exceptions
 *
 * @author Sultonov Isfandiyor
 * @version 1.0
 */
public enum ErrorCodes {

    ERROR_MESSAGE_NOT_FOUND("ERROR_MESSAGE_NOT_FOUND", "Error message with code not found"),
    OBJECT_ALREADY_EXIST("OBJECT_ALREADY_EXIST", "Object by this name already exist"),
    OBJECT_NOT_FOUND_BY_FIELD("OBJECT_NOT_FOUND_BY_FIELD", "Object by this %s not found"),
    OBJECT_NOT_FOUND("OBJECT_NOT_FOUND", "Object not found"),
    OBJECT_NOT_FOUND_ID("OBJECT_NOT_FOUND_ID", "Object with provided id not found"),
    OBJECT_IS_DELETED("OBJECT_IS_DELETED", "Object with provided id is deleted"),
    OBJECT_IS_NULL("OBJECT_IS_NULL", "Provided object %s is null"),
    OBJECT_NOT_NULL("OBJECT_NOT_NULL", "Provided object is not null"),
    OBJECT_ID_REQUIRED("OBJECT_ID_REQUIRED", "Object id not provided"),
    OBJECT_GIVEN_FIELD_REQUIRED("OBJECT_GIVEN_FIELD_REQUIRED", "Provided field is required"),
    OBJECT_SHOULD_BE("OBJECT_SHOULD_BE", "Provider object: %s should be %s"),
    FIELD_LENGTH_SHOULD_BE("FIELD_LENGTH_SHOULD_BE", "%s length should be between %s and %s"),
    METHOD_NOT_ALLOWED("METHOD_NOT_ALLOWED", "Method not allowed"),
    FIELD_IN_CORRECT("FIELD_IN_CORRECT", "Provided %s field was written incorrect"),
    ACTION_FALL("ACTION_FALL", "Object %s action fall");

    public final String code;
    public final String message;

    ErrorCodes(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
