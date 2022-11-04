package com.epam.esm.exceptions;

public class ObjectNotFoundDaoException extends Exception{
    public ObjectNotFoundDaoException() {
        super();
    }

    public ObjectNotFoundDaoException(String message) {
        super(message);
    }

    public ObjectNotFoundDaoException(String message, Throwable cause) {
        super(message, cause);
    }

    public ObjectNotFoundDaoException(Throwable cause) {
        super(cause);
    }
}
