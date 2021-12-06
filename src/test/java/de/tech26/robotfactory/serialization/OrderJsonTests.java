package de.tech26.robotfactory.serialization;


import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import org.springframework.boot.test.json.ObjectContent;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import de.tech26.robotfactory.domain.Order;
import de.tech26.robotfactory.domain.PartCatalogItem;
import de.tech26.robotfactory.domain.RobotPartType;
import de.tech26.robotfactory.dto.RequestOrderDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(SpringExtension.class)
@JsonTest
public class OrderJsonTests {

    @Autowired
    private JacksonTester<Order> jsonOrder;

    @Autowired
    private JacksonTester<RequestOrderDto> jsonRequestOrderDto;

    @Test
    public void testSerializeOrder() throws Exception {
        Order order = new Order(45, List.of(
            new PartCatalogItem("B","LCD Face", RobotPartType.FACE,24.07F),
            new PartCatalogItem("D","Arms with Hands", RobotPartType.ARMS,28.94F),
            new PartCatalogItem("F","Mobility with Wheels", RobotPartType.MOBILITY,30.7F),
            new PartCatalogItem("J","Material Metallic", RobotPartType.MATERIAL,82.31F)
            ),24.07F+28.94F+30.7F+82.31F);

        JsonContent<Order> jsonContent =  this.jsonOrder.write(order);

        Assertions.assertThat(jsonContent).hasJsonPathValue("@.order_id");
        Assertions.assertThat(jsonContent).hasJsonPathValue("@.total");
        Assertions.assertThat(jsonContent).hasJsonPathArrayValue("@.components");
    }



    @Test
    public void testDeserialize() throws Exception {
        String content =  "\n                    {\n                        \"components\": [\"A\", \"C\", \"I\", \"D\"]\n }\n ";
        Assertions.assertThat(this.jsonRequestOrderDto.parse(content))
            .isEqualTo(new RequestOrderDto(List.of("A","C","I","D")));
        Assertions.assertThat(this.jsonRequestOrderDto.parseObject(content).getComponents().get(1)).isEqualTo("C");
        Assertions.assertThat(this.jsonRequestOrderDto.parseObject(content).getComponents().get(3)).isEqualTo("D");
    }

}
