package de.tech26.robotfactory.service.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import de.tech26.robotfactory.domain.StockUnit;
import de.tech26.robotfactory.exception.DomainNotFoundException;
import de.tech26.robotfactory.exception.UnsuffcientStockException;
import de.tech26.robotfactory.repository.StockRepository;
import de.tech26.robotfactory.service.abstact.StockService;

@Service
public class StockServiceImpl implements StockService {

    private StockRepository stockRepository;

    @Value("${error.msg.stockiunit.not.found}")
    private String resourceNotFoundMsg;

    @Value("${error.msg.not.acceptable}")
    private String resourceUnsufficientMsg;

    @Autowired
    public StockServiceImpl(final StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    @Override
    public void createStockUnits(final List<StockUnit> stockUnits) {
        stockRepository.createAll(stockUnits);
    }

    @Override
    public void allocateStockItem(final String code) {
        StockUnit unit = stockRepository.getUnit(code).orElseThrow(() -> new DomainNotFoundException
            (String.format(resourceNotFoundMsg, code)));

        if (unit.getAvailableCount() == 0) {
            throw new UnsuffcientStockException(String.format(resourceUnsufficientMsg, code));
        }
        unit.setAvailableCount(unit.getAvailableCount() - 1);
        stockRepository.updateUnit(unit);
    }

}
