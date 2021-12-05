package de.tech26.robotfactory.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Order {


    private Long id;

    private List<PartCatalogItem> components;

    private Float total;

    public Order(List<PartCatalogItem> components) {
        this.components = components;
    }

    public Order() {
    }

    public List<PartCatalogItem> getComponents() {
        return components;
    }

    public void setComponents(List<PartCatalogItem> components) {
        this.components = components;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getTotal() {
        return total;
    }

    public void setTotal(Float total) {
        this.total = total;
    }


    @JsonProperty(value="summary")
    public String getSummary() {
        return "Order{" +
            "components=" + components +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Order order = (Order) o;

        return id.equals(order.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
