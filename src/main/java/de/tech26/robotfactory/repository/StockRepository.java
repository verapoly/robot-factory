package de.tech26.robotfactory.repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Repository;

import de.tech26.robotfactory.domain.StockItem;
import de.tech26.robotfactory.exception.DomainNotFoundException;


@Repository
public class StockRepository {

    private ConcurrentHashMap<String, Set<StockItem>> stock;

    public StockRepository() {
        this.stock = new ConcurrentHashMap<>();
    }

    /* create partition for a single catalog code */
    public  void mergeStockBatch(final String catalogCode,final Set<StockItem> entities) {
        stock.put(catalogCode, entities);
    }

    public Optional<Set<StockItem>> getBatchByCatalogCode(final String code) {
        return Optional.ofNullable(stock.get(code));
    }

    public  void updateStockItem(final StockItem item) {
        stock.computeIfPresent(item.getStockItemPk().getCatalogCode(),
            (p,v) -> {  v.add(item); return v;});
    }
}
