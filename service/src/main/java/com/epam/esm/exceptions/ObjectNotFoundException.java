package com.epam.esm.exceptions;

/**
 * Class {@code ObjectNotFoundException} used in the case of there will not find object by given field.
 *
 * @author Sultonov Isfandiyor
 * @version 1.0
 */
// fixme concat Two exceptions to onw
public class ObjectNotFoundException extends Exception {
    public ObjectNotFoundException() {
    }

    public ObjectNotFoundException(String message) {
        super(message);
    }

    public ObjectNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ObjectNotFoundException(Throwable cause) {
        super(cause);
    }
}
