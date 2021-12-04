package de.tech26.robotfactory.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class DomainNotFoundException extends ResponseStatusException {


    public DomainNotFoundException(final String reason) {
        super(HttpStatus.NOT_FOUND, reason);
    }
}
