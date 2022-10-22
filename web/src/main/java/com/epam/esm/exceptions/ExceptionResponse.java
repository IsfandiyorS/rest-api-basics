package com.epam.esm.exceptions;

import java.time.LocalDateTime;

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
