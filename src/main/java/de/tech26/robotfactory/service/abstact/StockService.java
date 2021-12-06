package de.tech26.robotfactory.service.abstact;

import java.util.Set;

import de.tech26.robotfactory.domain.StockItem;

public interface StockService {


    void addStockBatch(final Set<StockItem> stockUnits);


    void allocateStockItem(final String code);

}
