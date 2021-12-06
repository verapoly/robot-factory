package de.tech26.robotfactory.util;


import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import de.tech26.robotfactory.domain.PartCatalogItem;
import de.tech26.robotfactory.domain.RobotPartType;
import de.tech26.robotfactory.domain.StockItem;
import de.tech26.robotfactory.service.abstact.PartCatalogService;
import de.tech26.robotfactory.service.abstact.StockService;

@Component
public class DataInitializer implements ApplicationRunner {

    private PartCatalogService robotPartService;

    private StockService stockService;

    @Autowired
    public DataInitializer(PartCatalogService robotPartService,
        StockService stockService) {
        this.robotPartService = robotPartService;
        this.stockService = stockService;
    }

    @Override
    public void run(ApplicationArguments args) {

        Set<PartCatalogItem> robotPartItems = Set.of(
            new PartCatalogItem("A", "Humanoid Face", RobotPartType.FACE, 10.28F),
            new PartCatalogItem("B", "LCD Face", RobotPartType.FACE, 24.07F),
            new PartCatalogItem("C", "Steampunk Face", RobotPartType.FACE, 13.30F),
            new PartCatalogItem("D", "Arms with Hands", RobotPartType.ARMS, 28.94F),
            new PartCatalogItem("E", "Arms with Grippers", RobotPartType.ARMS, 12.39F),
            new PartCatalogItem("F", "Mobility with Wheels", RobotPartType.MOBILITY, 30.77F),
            new PartCatalogItem("G", "Mobility with Legs", RobotPartType.MOBILITY, 55.13F),
            new PartCatalogItem("H", "Mobility with Tracks", RobotPartType.MOBILITY, 50.00F),
            new PartCatalogItem("I", "Material Bioplastic", RobotPartType.MATERIAL, 90.12F),
            new PartCatalogItem("J", "Material Metallic", RobotPartType.MATERIAL, 82.31F)
        );
        robotPartService.createAll(robotPartItems);

        stockService.addStockBatch(IntStream.rangeClosed(1, 9)
            .mapToObj(p -> new StockItem("A", p)).collect(Collectors.toSet()));
        stockService.addStockBatch(IntStream.rangeClosed(1, 7)
            .mapToObj(p -> new StockItem("B", p)).collect(Collectors.toSet()));

        stockService.addStockBatch(IntStream.rangeClosed(1, 1)
            .mapToObj(p -> new StockItem("D", p)).collect(Collectors.toSet()));
        stockService.addStockBatch(IntStream.rangeClosed(1, 1)
            .mapToObj(p -> new StockItem("D", p)).collect(Collectors.toSet()));
        stockService.addStockBatch(IntStream.rangeClosed(1, 3)
            .mapToObj(p -> new StockItem("E", p)).collect(Collectors.toSet()));
        stockService.addStockBatch(IntStream.rangeClosed(1, 2)
            .mapToObj(p -> new StockItem("F", p)).collect(Collectors.toSet()));
        stockService.addStockBatch(IntStream.rangeClosed(1, 15)
            .mapToObj(p -> new StockItem("G", p)).collect(Collectors.toSet()));
        stockService.addStockBatch(IntStream.rangeClosed(1, 7)
            .mapToObj(p -> new StockItem("H", p)).collect(Collectors.toSet()));
        stockService.addStockBatch(IntStream.rangeClosed(1, 92)
            .mapToObj(p -> new StockItem("I", p)).collect(Collectors.toSet()));
        stockService.addStockBatch(IntStream.rangeClosed(1, 15)
            .mapToObj(p -> new StockItem("J", p)).collect(Collectors.toSet()));


    }

}
