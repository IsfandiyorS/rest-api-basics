package com.epam.esm.exceptions;

import com.epam.esm.enums.ErrorCodes;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // fixme should resolve problem with NoHandlerFoundException
    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler(NoHandlerFoundException.class)
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
                                                                   HttpStatus status, WebRequest request) {
        return new ResponseEntity<>(
                new ExceptionResponse(status.value(), ex.getMessage()),
                status
        );
    }

    @ExceptionHandler({MethodArgumentNotValidException.class, JsonProcessingException.class})
    public final ResponseEntity<Object> handleBadRequestExceptions(
            final MethodArgumentNotValidException exception, final   HttpStatus status) {
        ExceptionResponse errorResponse = new ExceptionResponse(status.value(), exception.getMessage());
        return new ResponseEntity<>(errorResponse, status);
    }

    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler(value = {ObjectNotFoundException.class, DaoException.class})
    public ResponseEntity<ExceptionResponse> handleNotFoundException(
            final RuntimeException ex, final WebRequest request) {
        return constructExceptionResponse(ex, request, NOT_FOUND);
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(value = {ValidationException.class, IdRequiredException.class,
            AlreadyExistException.class})
    public ResponseEntity<ExceptionResponse> handleBadRequestException(
            final RuntimeException ex, final WebRequest request) {
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
            final RuntimeException ex, final WebRequest request, HttpStatus status) {
        return new ResponseEntity<>(
                new ExceptionResponse(status.value(), ex.getMessage()),
                status
        );
    }
}