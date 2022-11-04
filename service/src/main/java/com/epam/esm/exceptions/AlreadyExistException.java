package com.epam.esm.exceptions;

/**
 * Class {@code AlreadyExistException} used in the case of there is already created entity given by its field.
 *
 * @author Sultonov Isfandiyor
 * @version 1.0
 */
public class AlreadyExistException extends Exception {
    public AlreadyExistException() {
        super();
    }

    public AlreadyExistException(String message) {
        super(message);
    }

    public AlreadyExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public AlreadyExistException(Throwable cause) {
        super(cause);
    }
}
