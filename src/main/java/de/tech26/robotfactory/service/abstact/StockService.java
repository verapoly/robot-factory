package de.tech26.robotfactory.service.abstact;

import java.util.List;

import de.tech26.robotfactory.domain.StockUnit;

public interface StockService {


    void createStockUnit(final List<StockUnit> stockUnits);


    void allocateStockItem(final String code);


    void checkAvailability(final String code);
}
