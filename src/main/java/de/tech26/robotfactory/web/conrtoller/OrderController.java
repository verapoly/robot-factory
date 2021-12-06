package de.tech26.robotfactory.web.conrtoller;


import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.tech26.robotfactory.domain.Order;
import de.tech26.robotfactory.dto.RequestOrderDto;
import de.tech26.robotfactory.dto.ResponseOrderDto;
import de.tech26.robotfactory.service.abstact.OrderService;
import de.tech26.robotfactory.service.abstact.PartCatalogService;

@RestController
@RequestMapping("orders")
public class OrderController {

    private OrderService orderService;

    private PartCatalogService partCatalogService;

    private ObjectMapper mapper;

    public OrderController(final OrderService orderService,
        final PartCatalogService robotPartService, final ObjectMapper mapper) {
        this.orderService = orderService;
        this.partCatalogService = robotPartService;
        this.mapper = mapper;
    }

    @PostMapping
    ResponseEntity<ResponseOrderDto> postOrder(@RequestBody @Valid RequestOrderDto requestDto) {

        Order order = new Order();
        // populate components
        order.setComponents(requestDto.getComponents().stream().map(
            p ->partCatalogService.getRobotPartItem(p)).collect(Collectors.toList()));

        //check validity, availability and create
        orderService.create(order);
        ResponseOrderDto dto = mapper.convertValue(order, ResponseOrderDto.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

}
