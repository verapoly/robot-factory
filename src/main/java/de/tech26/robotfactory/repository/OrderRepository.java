package de.tech26.robotfactory.repository;


import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.annotation.ObjectIdGenerator;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.annotation.ObjectIdGenerators.IntSequenceGenerator;
import com.fasterxml.jackson.annotation.ObjectIdGenerators.StringIdGenerator;
import de.tech26.robotfactory.domain.Order;

@Repository
public class OrderRepository {

    private static final AtomicLong idGenerator  = new AtomicLong(0);

    public OrderRepository() {
    }

    public void createOne(Order entity) {

        entity.setId(idGenerator.incrementAndGet());
    }


}
