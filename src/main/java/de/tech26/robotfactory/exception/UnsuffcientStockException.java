package de.tech26.robotfactory.exception;

import org.springframework.http.HttpStatus;

public class UnsuffcientStockException extends BusinessException {

    public UnsuffcientStockException(final String reason) {
        super(HttpStatus.UNPROCESSABLE_ENTITY, reason);
    }
}
