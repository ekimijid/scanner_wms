package com.essers.wms.movement.data.service;

import com.essers.wms.movement.TestDataBuilder;
import com.essers.wms.movement.data.entity.Movement;
import com.essers.wms.movement.data.repository.CompanyRepository;
import com.essers.wms.movement.data.repository.MovementRepository;
import com.essers.wms.movement.data.repository.PickinglistRepository;
import com.essers.wms.movement.data.repository.SiteRepository;
import com.essers.wms.movement.data.repository.WarehouseRepository;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
class MovementServiceImplementationTest {
    @Autowired
    private MovementService movementService;

    @Autowired
    private MovementRepository movementRepository;

    @Autowired
    private PickinglistRepository pickinglistRepository;
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private SiteRepository siteRepository;
    @Autowired
    private WarehouseRepository warehouseRepository;

    private TestDataBuilder testDataBuilder;

    @BeforeEach
    void setUp() {
        testDataBuilder = new TestDataBuilder();
        companyRepository.save(testDataBuilder.getCompany());
        siteRepository.save(testDataBuilder.getSite());
        warehouseRepository.save(testDataBuilder.getWarehouse());
        movementRepository.save(testDataBuilder.getMovement());
        pickinglistRepository.save(testDataBuilder.getPickinglist());
    }
    

    @Test
    void testGetById() {
        Assert.assertEquals(testDataBuilder.getMovement().getMovementId(), movementService.getById(testDataBuilder.getMovement().getMovementId()).getMovementId());
    }


    @Test
    void testRemove() {
        MovementService movementService = mock(MovementService.class);
        movementService.remove(testDataBuilder.getMovement());
        verify(movementService, times(1)).remove(testDataBuilder.getMovement());
    }

    @Test
    void testSave() {
        Movement movement = new Movement();
        movement.setPalleteNummer("111111");
        movementService.save(movement);
        Assert.assertEquals(movement.getPalleteNummer(), movementService.getById(movement.getMovementId()).getPalleteNummer());
    }
}