package de.tech26.robotfactory.service.impl;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.tech26.robotfactory.domain.StockUnit;
import de.tech26.robotfactory.exception.DomainNotFoundException;
import de.tech26.robotfactory.exception.UnsuffcientStockException;
import de.tech26.robotfactory.repository.StockRepository;
import de.tech26.robotfactory.service.abstact.StockService;

@Service
public class StockServiceImpl implements StockService {

    private StockRepository stockRepository;

    @Autowired
    public StockServiceImpl(final StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    @Override
    public void createStockUnit(final List<StockUnit> stockUnits){
        stockRepository.createAll(stockUnits);
    }

    @Override
    public void allocateStockItem(final String code) {
        StockUnit unit = stockRepository.getUnit(code).orElseThrow( () -> new DomainNotFoundException
            (String.format("Stock Item for code %s is unavailable", code)));

        if(unit.getAvailableCount() == 0){
            throw new UnsuffcientStockException(String.format("Stock content for code %s is insufficient",code));
        }
        unit.setAvailableCount(unit.getAvailableCount()-1);
        stockRepository.updateUnit(unit);
    }

}
