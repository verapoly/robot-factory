package de.tech26.robotfactory.repository;


import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Repository;

import de.tech26.robotfactory.domain.Order;
import de.tech26.robotfactory.domain.PartCatalogItem;

@Repository
public class OrderRepository {

    private ConcurrentHashMap<Long, Order> ordersStorage;

    public OrderRepository() {
        this.ordersStorage = new ConcurrentHashMap<>();
    }

    private static final AtomicLong idGenerator = new AtomicLong(0);



    public void createOne(Order entity) {

        entity.setId(idGenerator.incrementAndGet());
        ordersStorage.put(entity.getId(),entity );
    }

    public Optional<Order> getById(Long id) {

        return Optional.ofNullable(ordersStorage.get(id));
    }


}
