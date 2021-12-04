package de.tech26.robotfactory.service.impl;

import java.util.EnumSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.tech26.robotfactory.domain.Order;
import de.tech26.robotfactory.domain.PartCatalogItem;
import de.tech26.robotfactory.domain.RobotPartType;
import de.tech26.robotfactory.exception.InvalidRobotConfigException;
import de.tech26.robotfactory.repository.OrderRepository;
import de.tech26.robotfactory.service.abstact.OrderService;

@Service
public class OrderServiceImpl implements OrderService {


    private OrderRepository orderRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }


    @Override
    public void create(final Order order) {
        validate(order);
        // calculate total
        order.setTotal(order.getComponents().stream().map(PartCatalogItem::getPrice).reduce(0F,Float::sum));
        orderRepository.createOne(order);
    }

    @Override
    public void validate(final Order order) {
        EnumSet<RobotPartType> allParts = EnumSet.allOf(RobotPartType.class);
        if (order.getComponents().size() != allParts.size()){
            throw new InvalidRobotConfigException(String.format("Wrong number of robot components ordered "
                + "( required = %d , actual %d )", allParts.size(), order.getComponents().size()));
        }
        Set<RobotPartType> orderedParts =  order.getComponents().stream()
            .map(PartCatalogItem::getPart).collect(Collectors.toSet());

        if(orderedParts.size() != allParts.size()){
            allParts.removeAll(orderedParts);
            throw new InvalidRobotConfigException(String.format("Robot components mismatch: missing parts - %s" ,allParts));
        }
    }

}
