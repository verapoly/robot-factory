package de.tech26.robotfactory.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import de.tech26.robotfactory.repository.PartCatalogRepository;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class PartCatalogItemServiceTest {

    @Value("${error.msg.catalogitem.not.found}")
    private String notFoundMsg;

    @MockBean
    private PartCatalogRepository partCatalogRepository;

}
