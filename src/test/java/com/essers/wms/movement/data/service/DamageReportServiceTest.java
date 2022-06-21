package com.essers.wms.movement.data.service;

import com.essers.wms.movement.TestDataBuilder;
import com.essers.wms.movement.data.entity.Damagereport;
import com.essers.wms.movement.data.entity.Movement;
import com.essers.wms.movement.data.entity.Product;
import com.essers.wms.movement.data.repository.DamagereportRepository;
import com.essers.wms.movement.data.repository.MovementRepository;
import com.essers.wms.movement.data.repository.ProductRepository;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
class DamageReportServiceTest {

    @Autowired
    private DamageReportService damageReportService;

    @Autowired
    private DamagereportRepository damagereportRepository;

    @Autowired
    private MovementRepository movementRepository;
    @Autowired
    private ProductRepository productRepository;

    private TestDataBuilder testDataBuilder;
    private Damagereport damagereport;

    @BeforeEach
    void setUp() {
        testDataBuilder = new TestDataBuilder();
        movementRepository.save(testDataBuilder.getMovement());
        productRepository.save(testDataBuilder.getProduct());
        damagereport = new Damagereport();
        Movement movement = movementRepository.getById(testDataBuilder.getMovement().getMovementId());
        Product product = productRepository.getById(testDataBuilder.getProduct().getproductId());
        damagereport.setMovementID(movement.getMovementId().toString());
        damagereport.setProductID(movement.getProductId());
        damagereport.setProductName(product.getName());
        damagereport.setTimestamp(LocalDateTime.now());
        damagereportRepository.save(damagereport);

    }

    @Test
    void testSaveReport() {
        damageReportService.saveReport(damagereport);
        assertEquals(damagereport.getId(), damagereportRepository.findDamagereportById(damagereport.getId()).getId());
    }

    @Test
    void testGetAll() {
        assertEquals(damageReportService.getAll().size(), damageReportService.getAll().size());
    }
}