package de.tech26.robotfactory.web.handler;


import javax.validation.ValidationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

@ControllerAdvice
public class ApiExceptionHandler {


    @ExceptionHandler({ValidationException.class, IllegalArgumentException.class})
    public ResponseEntity<Void> handle(final RuntimeException e) {
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Void> handle(final ResponseStatusException e) {
        return new ResponseEntity<>(e.getStatus());
    }

}
