package de.tech26.robotfactory.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.tech26.robotfactory.domain.PartCatalogItem;
import de.tech26.robotfactory.exception.DomainNotFoundException;
import de.tech26.robotfactory.repository.PartCatalogRepository;
import de.tech26.robotfactory.service.abstact.PartCatalogService;

@Service
public class PartCatalogServiceImpl implements PartCatalogService {

    private PartCatalogRepository partCatalogRepository;

    @Autowired
    public PartCatalogServiceImpl(PartCatalogRepository robotPartRepository) {
        this.partCatalogRepository = robotPartRepository;
    }

    @Override
    public void createAll(List<PartCatalogItem> items) {
        partCatalogRepository.createAll(items);
    }

    @Override
    public PartCatalogItem getRobotPartItem(String code) {
        return partCatalogRepository.getById(code).orElseThrow(() -> new DomainNotFoundException(
            String.format("Catalog Item for code %s is not found ", code)
        ));
    }

}
