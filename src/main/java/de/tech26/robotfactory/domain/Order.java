package de.tech26.robotfactory.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Order {


    private String id;

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Float getTotal() {
        return total;
    }

    public void setTotal(Float total) {
        this.total = total;
    }

    @Override
    public String toString() {
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
