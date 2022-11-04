package com.epam.esm.exceptions;

/**
 * Class {@code ValidationException} used in the case of which given parameters of object is not correct.
 *
 * @author Sultonov Isfandiyor
 * @version 1.0
 */
public class ValidationException extends Exception{

    public ValidationException() {
        super();
    }

    public ValidationException(String message) {
        super(message);
    }

    public ValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ValidationException(Throwable cause) {
        super(cause);
    }
}
