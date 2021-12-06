package de.tech26.robotfactory.service.abstact;

import java.util.Collection;

import de.tech26.robotfactory.domain.PartCatalogItem;

public interface PartCatalogService {


    void createAll(final Collection<PartCatalogItem> items);

    PartCatalogItem getRobotPartItem(final String code);

}
