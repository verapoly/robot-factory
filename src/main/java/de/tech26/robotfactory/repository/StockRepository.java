package de.tech26.robotfactory.repository;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Repository;

import de.tech26.robotfactory.domain.StockUnit;
import de.tech26.robotfactory.exception.DomainNotFoundException;


@Repository
public class StockRepository {

    private ConcurrentHashMap<String, StockUnit> stock;

    public StockRepository() {
        this.stock = new ConcurrentHashMap<>();
    }

    public void allocate(final String code) {
        stock.computeIfPresent(code, (key, val) -> {
            int count = val.getAvailableCount();
            if (count == 0) {
                throw new DomainNotFoundException(String.format("Stock resource %s not found",
                    code));
            }
            val.setAvailableCount(--count);
            return val;
        });
    }


    public void createAll(final List<StockUnit> units) {

        units.forEach(p -> stock.put(p.getCode(), p));
    }

    public Optional<StockUnit> getUnit(final String code) {
        return Optional.ofNullable(stock.get(code));
    }
}
