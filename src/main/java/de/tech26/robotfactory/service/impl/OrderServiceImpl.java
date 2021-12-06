package de.tech26.robotfactory.service.impl;

import java.util.EnumSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import de.tech26.robotfactory.domain.Order;
import de.tech26.robotfactory.domain.PartCatalogItem;
import de.tech26.robotfactory.domain.RobotPartType;
import de.tech26.robotfactory.exception.InvalidRobotConfigException;
import de.tech26.robotfactory.repository.OrderRepository;
import de.tech26.robotfactory.service.abstact.OrderService;
import de.tech26.robotfactory.service.abstact.StockService;

@Service
public class OrderServiceImpl implements OrderService {

    @Value("${error.msg.unprocessable.entity.missing.parts}")
    private String msgUnprocessableEntity;

    @Value("${error.msg.unprocessable.entity.wrong.number.parts}")
    private String msgUnprocessableEntityWrongPartsNumber;

    private StockService stockService;

    private OrderRepository orderRepository;

    @Autowired
    public OrderServiceImpl(final StockService stockService,final OrderRepository orderRepository) {
        this.stockService = stockService;
        this.orderRepository = orderRepository;
    }

    /* supposed to be transactional for implementation with db */
    @Override
    public synchronized void create(final Order order) {

        //check validity
        validate(order);

        //check and allocate components in stock
        order.getComponents().forEach(p -> stockService.allocateStockItem(p.getCode()));

        // calculate total price
        order.setTotal(order.getComponents().stream().map(PartCatalogItem::getPrice).reduce(0F, Float::sum));

        // create order
        orderRepository.createOne(order);
    }

    private void validate(final Order order) {
        EnumSet<RobotPartType> allParts = EnumSet.allOf(RobotPartType.class);
        if (order.getComponents().size() != allParts.size()) {
            throw new InvalidRobotConfigException(String.format(msgUnprocessableEntityWrongPartsNumber,
                allParts.size(), order.getComponents().size()));
        }
        Set<RobotPartType> orderedParts = order.getComponents().stream()
            .map(PartCatalogItem::getPart).collect(Collectors.toSet());

        if (orderedParts.size() != allParts.size()) {
            allParts.removeAll(orderedParts);
            throw new InvalidRobotConfigException(
                String.format(msgUnprocessableEntity, allParts));
        }
    }


}
