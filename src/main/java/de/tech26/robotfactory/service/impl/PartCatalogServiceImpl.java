package de.tech26.robotfactory.service.impl;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import de.tech26.robotfactory.domain.PartCatalogItem;
import de.tech26.robotfactory.exception.DomainNotFoundException;
import de.tech26.robotfactory.repository.PartCatalogRepository;
import de.tech26.robotfactory.service.abstact.PartCatalogService;

@Service
public class PartCatalogServiceImpl implements PartCatalogService {

    @Value("${error.msg.catalogitem.not.found}")
    private String notFoundMsg;

    private PartCatalogRepository partCatalogRepository;

    @Autowired
    public PartCatalogServiceImpl(final PartCatalogRepository robotPartRepository) {
        this.partCatalogRepository = robotPartRepository;
    }

    @Override
    /* supposed to be transactional for implementation with db */
    public synchronized void createAll(Collection<PartCatalogItem> items) {
        partCatalogRepository.createAll(items);
    }

    @Override
    /* supposed to be transactional for implementation with db */
    public synchronized PartCatalogItem getRobotPartItem(String code) {
        return partCatalogRepository.getById(code).orElseThrow(() -> new DomainNotFoundException(
            String.format(notFoundMsg, code)
        ));
    }

}
