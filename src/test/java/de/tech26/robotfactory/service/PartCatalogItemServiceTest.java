package de.tech26.robotfactory.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import de.tech26.robotfactory.domain.PartCatalogItem;
import de.tech26.robotfactory.domain.RobotPartType;
import de.tech26.robotfactory.exception.DomainNotFoundException;
import de.tech26.robotfactory.repository.PartCatalogRepository;
import de.tech26.robotfactory.service.abstact.PartCatalogService;
import io.mockk.junit5.MockKExtension;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class PartCatalogItemServiceTest {

    @Value("${error.msg.catalogitem.not.found}")
    private String notFoundMsg;

    @MockBean
    private PartCatalogRepository partCatalogRepository;

    @Autowired
    private PartCatalogService partCatalogService;


    @BeforeEach
    public  void setUp() {
        PartCatalogItem itemExpected = new PartCatalogItem("F", "Mobility with Wheels",
            RobotPartType.MOBILITY, 30.77F);

        Mockito.when(partCatalogRepository.getById(itemExpected.getCode()))
            .thenReturn(Optional.of(itemExpected));
    }

    @Test
    void getNotFoundCatalogItem() {

        DomainNotFoundException thrown = Assertions.assertThrows(
            DomainNotFoundException.class,
            () -> partCatalogService.getRobotPartItem("Z"),
            "Expected partCatalogService.getRobotPartItem to throw DomainNotFoundException for Z code, but it hasn't");

        Assertions.assertEquals(thrown.getReason(), String.format(notFoundMsg, "Z"));
    }

    @Test
    void getFoundCatalogItem() {


        PartCatalogItem catalogItem = partCatalogService.getRobotPartItem("F");
        Assertions.assertNotNull(catalogItem,"Item is null");
        Assertions.assertEquals(catalogItem.getPart(),RobotPartType.MOBILITY );
        Assertions.assertEquals(catalogItem.getCode(), "F");
        Assertions.assertEquals(catalogItem.getName(), "Mobility with Wheels");
    }

    @Test
    void getBatchCreateSuccessful() {
        List<PartCatalogItem> list = List.of(new PartCatalogItem("G","G test",RobotPartType.FACE,44.31F),
            new PartCatalogItem("K","K test",RobotPartType.ARMS,45.31F),
            new PartCatalogItem("L","L test",RobotPartType.MOBILITY,23.31F));
        partCatalogService.createAll(list);
        Assertions.assertNotNull(partCatalogRepository.getById("G"),"Found Item is null");
        Assertions.assertNotNull(partCatalogRepository.getById("K"),"Found Item is null");
        Assertions.assertNotNull(partCatalogRepository.getById("L"),"Found Item is null");
    }
}
