package de.tech26.robotfactory.repository;


import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.annotation.ObjectIdGenerator;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.annotation.ObjectIdGenerators.IntSequenceGenerator;
import com.fasterxml.jackson.annotation.ObjectIdGenerators.StringIdGenerator;
import de.tech26.robotfactory.domain.Order;

@Repository
public class OrderRepository {

    private static final ObjectIdGenerator<String> idGenerator  = new StringIdGenerator();

    public OrderRepository() {
    }

    public void createOne(Order entity) {
        entity.setId(idGenerator.generateId(entity));
    }


}
