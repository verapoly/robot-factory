package de.tech26.robotfactory.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Order {

    @JsonProperty(required = true,value="order_id")
    private Long id;

    private List<PartCatalogItem> components;

    private Float total;

    public Order(Long id, List<PartCatalogItem> components, Float total) {
        this.id = id;
        this.components = components;
        this.total = total;
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

    public String getSummary() {
        return "{" +
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
