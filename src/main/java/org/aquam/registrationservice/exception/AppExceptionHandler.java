package org.aquam.registrationservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceException;
import javax.validation.ConstraintViolationException;
import java.io.IOException;
import java.time.ZonedDateTime;

@ControllerAdvice
public class AppExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({EntityExistsException.class, IllegalStateException.class})
    public ResponseEntity<AppResponse> handleBadRequest(RuntimeException exception) {
        AppResponse response = new AppResponse(exception.getMessage(), ZonedDateTime.now(), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);    // 404
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<AppResponse> handleConstraintViolationException(ConstraintViolationException exception) {
        AppResponse response = new AppResponse(exception.getMessage(), ZonedDateTime.now(), HttpStatus.BAD_REQUEST, exception.getConstraintViolations());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);    // 404
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<AppResponse> handleNotFound(PersistenceException exception) {
        AppResponse response = new AppResponse(exception.getMessage(), ZonedDateTime.now(), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);    // 404
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<AppResponse> handleInternalServerError(Exception exception) {
        AppResponse response = new AppResponse(exception.getMessage(), ZonedDateTime.now(), HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);     // 500
    }


}
