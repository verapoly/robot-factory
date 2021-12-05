package de.tech26.robotfactory.util;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import de.tech26.robotfactory.domain.PartCatalogItem;
import de.tech26.robotfactory.domain.RobotPartType;
import de.tech26.robotfactory.domain.StockUnit;
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

        List<PartCatalogItem> robotPartItems = List.of(
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

        List<StockUnit> stockUnits = List.of(
            new StockUnit("A", 9),
            new StockUnit("B", 7),
            new StockUnit("C", 0),
            new StockUnit("D", 1),
            new StockUnit("E", 3),
            new StockUnit("F", 2),
            new StockUnit("G", 15),
            new StockUnit("H", 7),
            new StockUnit("I", 92),
            new StockUnit("J", 15)
        );
        stockService.createStockUnits(stockUnits);

    }

}
