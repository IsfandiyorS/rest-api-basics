package com.epam.esm.exceptions;

import com.epam.esm.enums.ErrorCodes;
import com.epam.esm.response.ExceptionResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import static org.springframework.http.HttpStatus.*;

/**
 * Class {@code GlobalExceptionHandler} presents entity which will be returned from controller
 * in case generating exceptions
 *
 * @author Sultonov Isfandiyor
 * @version 1.0
 */

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler(NoHandlerFoundException.class)
    protected ResponseEntity<Object> handleNoHandlerFoundException() {
        return new ResponseEntity<>(
                new ExceptionResponse(NOT_FOUND.value(), "No handler found"),
                NOT_FOUND
        );
    }

    @ResponseStatus(INTERNAL_SERVER_ERROR)
    @ExceptionHandler(ActionFallDaoException.class)
    public final ResponseEntity<ExceptionResponse> handleActionFallException(
            final Exception exception, final WebRequest status){
        return constructExceptionResponse(exception, status, INTERNAL_SERVER_ERROR);
    }


    @ExceptionHandler({MethodArgumentNotValidException.class, JsonProcessingException.class})
    public final ResponseEntity<Object> handleBadRequestExceptions(
            final MethodArgumentNotValidException exception, final   HttpStatus status) {
        ExceptionResponse errorResponse = new ExceptionResponse(status.value(), exception.getMessage());
        return new ResponseEntity<>(errorResponse, status);
    }

    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler(value = {ObjectNotFoundException.class, ObjectNotFoundDaoException.class})
    public ResponseEntity<ExceptionResponse> handleNotFoundException(
            final Exception ex, final WebRequest request) {
        return constructExceptionResponse(ex, request, NOT_FOUND);
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(value = {ValidationException.class, AlreadyExistException.class})
    public ResponseEntity<ExceptionResponse> handleBadRequestException(
            final Exception ex, final WebRequest request) {
        return constructExceptionResponse(ex, request, BAD_REQUEST);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public final ResponseEntity<Object> methodNotAllowedExceptionException() {
        ExceptionResponse errorResponse = new ExceptionResponse(HttpStatus.METHOD_NOT_ALLOWED.value(), ErrorCodes.METHOD_NOT_ALLOWED.message);
        return new ResponseEntity<>(errorResponse, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    public final ResponseEntity<Object> handleBadRequestExceptions() {
        ExceptionResponse errorResponse = new ExceptionResponse(BAD_REQUEST.value(), "Invalid request parameters");
        return new ResponseEntity<>(errorResponse, BAD_REQUEST);
    }


    private ResponseEntity<ExceptionResponse> constructExceptionResponse(
            final Exception ex, final WebRequest request, HttpStatus status) {
        return new ResponseEntity<>(
                new ExceptionResponse(status.value(), ex.getMessage()),
                status
        );
    }
}