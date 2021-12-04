package de.tech26.robotfactory.web.conrtoller;


import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.tech26.robotfactory.domain.Order;
import de.tech26.robotfactory.dto.RequestOrderDto;
import de.tech26.robotfactory.service.abstact.OrderService;
import de.tech26.robotfactory.service.abstact.PartCatalogService;
import de.tech26.robotfactory.service.abstact.StockService;

@RestController
public class OrderController {

    private OrderService orderService;

    private PartCatalogService partCatalogService;

    private StockService stockService;

    private ObjectMapper mapper;

    public OrderController(final OrderService orderService,
        final PartCatalogService robotPartService, final StockService stockService, final ObjectMapper mapper) {
        this.orderService = orderService;
        this.partCatalogService = robotPartService;
        this.stockService = stockService;
        this.mapper = mapper;
    }

    @PostMapping
    ResponseEntity<Order> postOrder(@RequestBody @Valid RequestOrderDto components) {
        // convert to domain object
        Order order = mapper.convertValue(components, Order.class);
        // populate components
        order.setComponents(order.getComponents().stream().map(p -> partCatalogService.getRobotPartItem(p.getCode()))
            .collect(Collectors.toList()));

        //check validity
        orderService.validate(order);

        //check availability
        order.getComponents().forEach(p -> stockService.checkAvailability(p.getCode()));

        //create Order
        orderService.create(order);

        //align stock
        order.getComponents().forEach(p -> stockService.allocateStockItem(p.getCode()));

        return ResponseEntity.status(HttpStatus.CREATED).body(order);
    }

}
