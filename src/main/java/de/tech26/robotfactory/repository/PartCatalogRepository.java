package de.tech26.robotfactory.repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Repository;

import de.tech26.robotfactory.domain.PartCatalogItem;

@Repository
public class PartCatalogRepository {


    private ConcurrentHashMap<String, PartCatalogItem> partsCatalog;

    public PartCatalogRepository() {
        this.partsCatalog = new ConcurrentHashMap<>();
    }

    public void createAll(Collection<PartCatalogItem> entities) {
        entities.forEach(p -> partsCatalog.put(p.getCode(), p));
    }

    public Optional<PartCatalogItem> getById(String id) {
        return Optional.ofNullable(partsCatalog.get(id));
    }
}
