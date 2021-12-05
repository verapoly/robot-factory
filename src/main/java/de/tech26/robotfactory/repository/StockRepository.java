package de.tech26.robotfactory.repository;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Repository;

import de.tech26.robotfactory.domain.StockUnit;


@Repository
public class StockRepository {

    private ConcurrentHashMap<String, StockUnit> stock;

    public StockRepository() {
        this.stock = new ConcurrentHashMap<>();
    }

    public void createAll(final List<StockUnit> units) {

        units.forEach(p -> stock.put(p.getCode(), p));
    }

    public Optional<StockUnit> getUnit(final String code) {
        return Optional.ofNullable(stock.get(code));
    }

    public void updateUnit(final StockUnit unit) {
        stock.put(unit.getCode(), unit);
    }
}
