package de.tech26.robotfactory.service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import de.tech26.robotfactory.domain.StockItem;
import de.tech26.robotfactory.domain.StockItemStatus;
import de.tech26.robotfactory.exception.UnsuffcientStockException;
import de.tech26.robotfactory.repository.StockRepository;
import de.tech26.robotfactory.service.abstact.StockService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith({SpringExtension.class, MockitoExtension.class})
@SpringBootTest
public class StockServiceTest {

    @Autowired
    private StockService stockService;

    @MockBean
    private StockRepository stockRepository;

    @BeforeEach
    public void setUp() {
        Set<StockItem> stockExpected1 = Set.of(new StockItem("C", 1
            , StockItemStatus.RESERVED), new StockItem("A", 2, StockItemStatus.RESERVED));

        Mockito.when(stockRepository.getBatchByCatalogCode("C"))
            .thenReturn(Optional.of(stockExpected1));

        Set<StockItem> stockExpected2 = new HashSet<>();
        stockExpected2.add(new StockItem("A", 1));
        stockExpected2.add(new StockItem("A", 2, StockItemStatus.RESERVED));
        stockExpected2.add(new StockItem("A", 3));
        Mockito.when(stockRepository.getBatchByCatalogCode("A"))
            .thenReturn(Optional.of(stockExpected2));
    }

    @Test
    void getUnsuffcientStock() {

        UnsuffcientStockException thrown = Assertions.assertThrows(
            UnsuffcientStockException.class,
            () -> stockService.allocateStockItem("Z"),
            "Expected stockService.allocateStockItem to throw UnsuffcientStockException for Z code, but it hasn't");

        Assertions.assertEquals(thrown.getReason(), String.format("Stock content for code %s is insufficient", "Z"));
    }

    @Test
    void getUnsuffcientStockBecauseOfReserved() {

        UnsuffcientStockException thrown = Assertions.assertThrows(
            UnsuffcientStockException.class,
            () -> stockService.allocateStockItem("C"),
            "Expected stockService.allocateStockItem to throw UnsuffcientStockException for C code, but it hasn't");

        Assertions.assertEquals(thrown.getReason(), String.format("Stock content for code %s is insufficient", "C"));
    }

    @Test
    void getAllocatedItem() {
        stockService.allocateStockItem("A");
        Assertions.assertTrue(stockRepository.getBatchByCatalogCode("A").isPresent());
        Assertions.assertEquals(1, stockRepository.getBatchByCatalogCode("A").get()
            .stream().filter(p -> p.getStatus() == StockItemStatus.AVAILABLE).count());
    }

    @Test
    void getAddBatchSuccessful() {

        stockService.addStockBatch(Set.of(new StockItem("A", 4),
            new StockItem("A", 5)));
        Assertions.assertTrue(stockRepository.getBatchByCatalogCode("A").isPresent());
        Assertions.assertEquals(stockRepository.getBatchByCatalogCode("A").get().size(), 5);

    }

}
