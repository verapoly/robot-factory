package de.tech26.robotfactory.service.impl;


import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import de.tech26.robotfactory.domain.StockItem;
import de.tech26.robotfactory.domain.StockItemStatus;
import de.tech26.robotfactory.exception.UnsuffcientStockException;
import de.tech26.robotfactory.repository.StockRepository;
import de.tech26.robotfactory.service.abstact.StockService;

@Service
public class StockServiceImpl implements StockService {

    private StockRepository stockRepository;


    @Value("${error.msg.not.acceptable}")
    private String resourceUnsufficientMsg;


    @Autowired
    public StockServiceImpl(final StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    @Override
    /* supposed to be transactional for implementation with db */
    public synchronized void addStockBatch(final Set<StockItem> stockUnits) {
        if (stockUnits != null && stockUnits.size() > 0) {
            String catalogCode = stockUnits.stream().findFirst().get().getStockItemPk().getCatalogCode();

            Set<StockItem> batchInitial = stockRepository.getBatchByCatalogCode(catalogCode).orElseGet(
                HashSet::new);

            batchInitial.addAll(stockUnits);
            stockRepository.mergeStockBatch(catalogCode, batchInitial);
        }
    }

    @Override
    /* supposed to be transactional for implementation with db */
    public synchronized void allocateStockItem(final String catalogCode) {
        Set<StockItem> batch = stockRepository.getBatchByCatalogCode(catalogCode).orElseThrow(() ->
            new UnsuffcientStockException(String.format(resourceUnsufficientMsg, catalogCode)));

        StockItem item = batch.stream().filter(p -> p.getStatus() == StockItemStatus.AVAILABLE)
            .findFirst()
            .orElseThrow(() -> new UnsuffcientStockException(
                String.format(resourceUnsufficientMsg, catalogCode)));

        item.setStatus(StockItemStatus.RESERVED);
        stockRepository.updateStockItem(item);
    }

}
