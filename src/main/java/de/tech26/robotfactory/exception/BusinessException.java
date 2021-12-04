package de.tech26.robotfactory.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class BusinessException extends ResponseStatusException {

    public BusinessException(final HttpStatus status,final String reason) {
        super(status, reason);
    }
}
