package de.tech26.robotfactory.dto;

import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RequestOrderDto {

    @JsonProperty
    @NotNull(message = "Components list must not be null.")
    @NotEmpty(message = "Components list must not be empty.")
    private List<String> components;

    public List<String> getComponents() {
        return components;
    }

    public void setComponents(List<String> components) {
        this.components = components;
    }

    public RequestOrderDto(List<String> components) {
        this.components = components;
    }

    public RequestOrderDto() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RequestOrderDto)) {
            return false;
        }

        RequestOrderDto that = (RequestOrderDto) o;

        return components != null ? components.equals(that.components) : that.components == null;
    }

    @Override
    public int hashCode() {
        return components != null ? components.hashCode() : 0;
    }
}
