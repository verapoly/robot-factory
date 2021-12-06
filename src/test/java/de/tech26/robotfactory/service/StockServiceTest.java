package de.tech26.robotfactory.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import de.tech26.robotfactory.exception.DomainNotFoundException;
import de.tech26.robotfactory.exception.UnsuffcientStockException;
import de.tech26.robotfactory.repository.StockRepository;
import de.tech26.robotfactory.service.abstact.StockService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

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



}
