package de.tech26.robotfactory.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ResponseOrderDto {

    @JsonProperty(required = true)
    private String id;

    @JsonProperty
    private String summary;

    @JsonProperty(required = true)
    private Float total;

    public ResponseOrderDto(final String id,final String summary,final Float total) {
        this.id = id;
        this.summary = summary;
        this.total = total;
    }

    public ResponseOrderDto() {
    }
}
