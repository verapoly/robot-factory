package de.tech26.robotfactory.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PartCatalogItem {

    @JsonProperty
    private String  code;

    private String  name;

    private RobotPartType part;

    private Float  price;


    public PartCatalogItem(String code) {
        this.code = code;
    }

    public PartCatalogItem(String code, String name, RobotPartType part, Float price) {
        this.code = code;
        this.name = name;
        this.part = part;
        this.price = price;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RobotPartType getPart() {
        return part;
    }

    public void setPart(RobotPartType part) {
        this.part = part;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "PartCatalogItem{" +
            "code='" + code + '\'' +
            ", name='" + name + '\'' +
            ", part=" + part +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PartCatalogItem)) {
            return false;
        }

        PartCatalogItem that = (PartCatalogItem) o;

        return code.equals(that.code);
    }

    @Override
    public int hashCode() {
        return code.hashCode();
    }
}
