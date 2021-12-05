package de.tech26.robotfactory.dto;

public class ErrorDto {

    private String message;

    private String reason;

    private Integer status;


    public ErrorDto( final Integer status, final String reason,final String message) {
        this.status = status;
        this.reason = reason;
        this.message = message;

    }
}
