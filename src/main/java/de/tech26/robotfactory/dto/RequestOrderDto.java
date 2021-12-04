package de.tech26.robotfactory.dto;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


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
}
