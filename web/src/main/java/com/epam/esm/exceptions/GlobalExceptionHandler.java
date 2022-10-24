package com.epam.esm.exceptions;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@ControllerAdvice
public class GlobalExceptionHandler {

    // fixme should resolve problem with NoHandlerFoundException
//    @ResponseStatus(NOT_FOUND)
//    @ExceptionHandler(NoHandlerFoundException.class)
//    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
//                                                                   HttpStatus status, WebRequest request) {
//        return new ResponseEntity<>(
//                new ExceptionResponse(status.value(), ex.getMessage()),
//                status
//        );
//    }

    @ExceptionHandler({MethodArgumentNotValidException.class, JsonProcessingException.class})
    public final ResponseEntity<Object> handleBadRequestExceptions(
            final MethodArgumentNotValidException exception, final   HttpStatus status) {
        ExceptionResponse errorResponse = new ExceptionResponse(status.value(), exception.getMessage());
        return new ResponseEntity<>(errorResponse, status);
    }

//    @ResponseStatus(NOT_FOUND)
//    @ExceptionHandler(value = {NoHandlerFoundException.class})
//    protected ResponseEntity<Object> handleNoHandlerFoundException(final NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
//        return new ResponseEntity<>(
//                new ExceptionResponse(status.value(), "ex.getMessage()"),
//                status
//        );
//    }

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

    private ResponseEntity<ExceptionResponse> constructExceptionResponse(
            final RuntimeException ex, final WebRequest request, HttpStatus status) {
        return new ResponseEntity<>(
                new ExceptionResponse(status.value(), ex.getMessage()),
                status
        );
    }

}
//    it may be not required
//    @ResponseStatus(INTERNAL_SERVER_ERROR)
//    @ExceptionHandler(value = DaoException.class)
//    public ResponseEntity<ExceptionResponsin spring boote> handleInternalServerError(
//            final RuntimeException ex, final WebRequest request
//    ) {
//        return constructExceptionResponse(ex, request, INTERNAL_SERVER_ERROR);
//    }