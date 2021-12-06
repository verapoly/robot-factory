package de.tech26.robotfactory.service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import de.tech26.robotfactory.domain.Order;
import de.tech26.robotfactory.domain.PartCatalogItem;
import de.tech26.robotfactory.domain.RobotPartType;
import de.tech26.robotfactory.exception.InvalidRobotConfigException;
import de.tech26.robotfactory.repository.OrderRepository;
import de.tech26.robotfactory.service.abstact.OrderService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class OrderServiceTest {

    @Value("${error.msg.unprocessable.entity.missing.parts}")
    private String msgUnprocessableEntity;

    @Value("${error.msg.unprocessable.entity.wrong.number.parts}")
    private String msgUnprocessableEntityWrongPartsNumber;
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderService orderService;

    @Test
    void createOrder() {
        Order order = new Order();
        order.setComponents(List.of(
            new PartCatalogItem("B", "LCD Face", RobotPartType.FACE, 24.07F),
            new PartCatalogItem("D", "Arms with Hands", RobotPartType.ARMS, 28.94F),
            new PartCatalogItem("F", "Mobility with Wheels", RobotPartType.MOBILITY, 30.7F),
            new PartCatalogItem("J", "Material Metallic", RobotPartType.MATERIAL, 82.31F)));
        order.setTotal(24.07F + 28.94F + 30.7F + 82.31F);
        orderService.create(order);
        Assertions.assertNotNull(order.getId());
        Assertions.assertFalse(orderRepository.getById(order.getId()).isEmpty());
    }

    @Test
    void getWrongConfiguraion() {
        Order order = new Order();
        order.setComponents(List.of(
            new PartCatalogItem("B", "LCD Face", RobotPartType.FACE, 24.07F),
            new PartCatalogItem("A", "Humanoid Face", RobotPartType.FACE, 10.28F),
            new PartCatalogItem("F", "Mobility with Wheels", RobotPartType.MOBILITY, 30.7F),
            new PartCatalogItem("E", "Material Metallic", RobotPartType.MATERIAL, 82.31F)));
        order.setTotal(24.07F + 28.94F + 30.7F + 82.31F);
        InvalidRobotConfigException thrown = Assertions.assertThrows(
            InvalidRobotConfigException.class,
            () -> orderService.create(order),
            "Expected orderService.create to throw InvalidRobotConfigException for C code, but it hasn't");

        Assertions.assertEquals(thrown.getReason(), String.format(msgUnprocessableEntity, Set.of(RobotPartType.ARMS)));
    }

    @Test
    void getWrongConfiguraionWrongNumber() {
        Order order = new Order();
        order.setComponents(List.of(
            new PartCatalogItem("B", "LCD Face", RobotPartType.FACE, 24.07F),
            new PartCatalogItem("A", "Humanoid Face", RobotPartType.FACE, 10.28F),
            new PartCatalogItem("F", "Mobility with Wheels", RobotPartType.MOBILITY, 30.7F),
            new PartCatalogItem("E", "Material Metallic", RobotPartType.MATERIAL, 82.31F),
            new PartCatalogItem("O", "Arms bla-bla", RobotPartType.ARMS, 82.31F)));
        order.setTotal(24.07F + 28.94F + 30.7F + 82.31F);
        InvalidRobotConfigException thrown = Assertions.assertThrows(
            InvalidRobotConfigException.class,
            () -> orderService.create(order),
            "Expected orderService.create to throw InvalidRobotConfigException for C code, but it hasn't");

        Assertions.assertEquals(thrown.getReason(),
            String.format(msgUnprocessableEntityWrongPartsNumber, RobotPartType.values().length,
                order.getComponents().size()));
    }
}
