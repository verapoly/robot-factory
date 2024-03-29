package de.tech26.robotfactory.repository;


import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Repository;

import de.tech26.robotfactory.domain.Order;

@Repository
public class OrderRepository {

    private ConcurrentHashMap<Integer, Order> ordersStorage;

    public OrderRepository() {
        this.ordersStorage = new ConcurrentHashMap<>();
    }

    private static final AtomicInteger idGenerator = new AtomicInteger(0);


    public void createOne(Order entity) {

        entity.setId(idGenerator.incrementAndGet());
        ordersStorage.put(entity.getId(), entity);
    }

    public Optional<Order> getById(Integer id) {

        return Optional.ofNullable(ordersStorage.get(id));
    }


}
