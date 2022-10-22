package com.epam.esm.exceptions;

public class IdRequiredException extends GenericException{
    public IdRequiredException() {
        super();
    }

    public IdRequiredException(String message) {
        super(message);
    }

    public IdRequiredException(String message, Throwable cause) {
        super(message, cause);
    }

    public IdRequiredException(Throwable cause) {
        super(cause);
    }
}
