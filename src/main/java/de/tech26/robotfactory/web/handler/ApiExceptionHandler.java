package de.tech26.robotfactory.web.handler;


import javax.validation.ValidationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

import de.tech26.robotfactory.dto.ErrorDto;

@ControllerAdvice
public class ApiExceptionHandler {

    // handle controller exceptions
    @ExceptionHandler({ValidationException.class, IllegalArgumentException.class})
    public ResponseEntity<ErrorDto> handle(final RuntimeException e) {
        return new ResponseEntity<>(new ErrorDto(HttpStatus.BAD_REQUEST.value(),
            HttpStatus.BAD_REQUEST.getReasonPhrase(), e.getMessage()),HttpStatus.BAD_REQUEST);
    }

    // handle business exceptions
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ErrorDto> handle(final ResponseStatusException e) {
        return new ResponseEntity<>(new ErrorDto(e.getStatus().value(),e.getReason(), e.getMessage()),e.getStatus());
    }

}
