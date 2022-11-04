package com.epam.esm.exceptions;

public class ActionFallDaoException extends Exception{
    public ActionFallDaoException() {
        super();
    }

    public ActionFallDaoException(String message) {
        super(message);
    }

    public ActionFallDaoException(String message, Throwable cause) {
        super(message, cause);
    }

    public ActionFallDaoException(Throwable cause) {
        super(cause);
    }
}
