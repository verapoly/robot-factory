package de.tech26.robotfactory.service.abstact;

import java.util.List;
import java.util.Optional;

import de.tech26.robotfactory.domain.PartCatalogItem;

public interface PartCatalogService {


    void createAll(final List<PartCatalogItem> items);

    PartCatalogItem getRobotPartItem(final String code);

}
