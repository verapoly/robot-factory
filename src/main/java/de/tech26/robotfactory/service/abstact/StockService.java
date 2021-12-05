package de.tech26.robotfactory.service.abstact;

import java.util.List;

import de.tech26.robotfactory.domain.StockUnit;

public interface StockService {


    void createStockUnits(final List<StockUnit> stockUnits);


    void allocateStockItem(final String code);

}
