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
import de.tech26.robotfactory.domain.StockUnit;
import de.tech26.robotfactory.exception.DomainNotFoundException;
import de.tech26.robotfactory.exception.UnsuffcientStockException;
import de.tech26.robotfactory.repository.StockRepository;
import de.tech26.robotfactory.service.abstact.StockService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class StockServiceTest {

    @Value("${error.msg.stockiunit.not.found}")
    private String resourceNotFoundMsg;

    @Value("${error.msg.not.acceptable}")
    private String resourceUnsufficientMsg;
    
    @Autowired
    private StockService stockService;

    @MockBean
    private StockRepository stockRepository;

    @BeforeEach
    public  void setUp() {
        StockUnit stockExpected1 = new StockUnit("C",0);
        StockUnit stockExpected2 = new StockUnit("A",2);

        Mockito.when(stockRepository.getUnit(stockExpected1.getCode()))
            .thenReturn(Optional.of(stockExpected1));

        Mockito.when(stockRepository.getUnit(stockExpected2.getCode()))
            .thenReturn(Optional.of(stockExpected2));
    }

    @Test
    void getNotFoundStock() {

        DomainNotFoundException thrown = Assertions.assertThrows(
            DomainNotFoundException.class,
            () -> stockService.allocateStockItem("Z"),
            "Expected stockService.allocateStockItem to throw DomainNotFoundException for Z code, but it hasn't");

        Assertions.assertEquals(thrown.getReason(),String.format(resourceNotFoundMsg, "Z"));
    }

    @Test
    void getUnsuffcientStock() {

        UnsuffcientStockException thrown = Assertions.assertThrows(
            UnsuffcientStockException.class,
            () -> stockService.allocateStockItem("C"),
            "Expected stockService.allocateStockItem to throw UnsuffcientStockException for C code, but it hasn't");

        Assertions.assertEquals(thrown.getReason(),String.format(resourceUnsufficientMsg, "C"));
    }

    @Test
    void getAllocatedItem() {
         stockService.allocateStockItem("A");
         Assertions.assertEquals(stockRepository.getUnit("A").get().getAvailableCount(),1);
    }

    @Test
    void getBatchCreateSuccessful() {
        List<StockUnit> list = List.of(new StockUnit("G",1),
            new StockUnit("K",2),
            new StockUnit("L",3));
        stockService.createStockUnits(list);
        Assertions.assertNotNull(stockRepository.getUnit("G"),"Found Item is null");
        Assertions.assertNotNull(stockRepository.getUnit("K"),"Found Item is null");
        Assertions.assertNotNull(stockRepository.getUnit("L"),"Found Item is null");
    }

}
