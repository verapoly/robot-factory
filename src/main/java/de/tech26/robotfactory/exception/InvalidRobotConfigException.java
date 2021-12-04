package de.tech26.robotfactory.exception;

import org.springframework.http.HttpStatus;

public class InvalidRobotConfigException extends BusinessException {

    public InvalidRobotConfigException(final String reason) {

        super(HttpStatus.UNPROCESSABLE_ENTITY, reason);
    }
}
