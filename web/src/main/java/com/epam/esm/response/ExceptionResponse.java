package com.epam.esm.response;

import java.time.LocalDateTime;

/**
 * Class {@code ExceptionResponse} represents objects that will be returned as
 * a response when an error is generated.
 *
 * @author Sultonov Isfandiyor
 * @version 1.0
 */
public class ExceptionResponse {
    private int errorCode;
    private String errorMessage;
    private LocalDateTime time = LocalDateTime.now();

    public ExceptionResponse(int errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getTime() {
        return time.toString();
    }

}
