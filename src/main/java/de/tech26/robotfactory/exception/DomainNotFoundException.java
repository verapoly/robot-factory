package de.tech26.robotfactory.exception;

import org.springframework.http.HttpStatus;

public class DomainNotFoundException extends BusinessException {


    public DomainNotFoundException(final String reason) {
        super(HttpStatus.NOT_FOUND, reason);
    }
}
