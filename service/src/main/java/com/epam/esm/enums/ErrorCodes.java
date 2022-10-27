package com.epam.esm.enums;

public enum ErrorCodes {

    ERROR_MESSAGE_NOT_FOUND("ERROR_MESSAGE_NOT_FOUND", "Error message with code %s not found"),
    OBJECT_ALREADY_EXIST("OBJECT_ALREADY_EXIST", "%s by this %s already exist"),
    OBJECT_NOT_FOUND_BY_FIELD("OBJECT_NOT_FOUND_BY_FIELD", "%s by this %s not found"),
    OBJECT_NOT_FOUND("OBJECT_NOT_FOUND", "%s not found"),
    OBJECT_NOT_FOUND_ID("OBJECT_NOT_FOUND_ID", "%s with provided id: %s not found"),
    OBJECT_IS_DELETED("OBJECT_IS_DELETED", "%s with provided id: %s is deleted"),
    OBJECT_IS_NULL("OBJECT_IS_NULL", "Provided object: %s is null"),
    OBJECT_NOT_NULL("OBJECT_NOT_NULL", "Provided object: %s is not null"),
    OBJECT_ID_REQUIRED("OBJECT_ID_REQUIRED", "%s id not provided"),
    OBJECT_GIVEN_FIELD_REQUIRED("OBJECT_GIVEN_FIELD_REQUIRED", "Provided field : %s of %s is required"),
    ACCESS_DENIED("ACCESS_DENIED", "You do not have access for %s"),
    OBJECT_SHOULD_BE("OBJECT_SHOULD_BE", "Provider object: %s should be %s"),
    FIELD_LENGTH_SHOULD_BE("FIELD_LENGTH_SHOULD_BE", "%s length should be between %s and %s"),
    METHOD_NOT_ALLOWED("METHOD_NOT_ALLOWED", "Method not allowed"),
    FIELD_IN_CORRECT("FIELD_IN_CORRECT", "Provided %s field was written incorrect");

    public final String code;
    public final String message;

    ErrorCodes(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
