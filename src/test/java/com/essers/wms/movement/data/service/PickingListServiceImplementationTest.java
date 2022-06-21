package com.essers.wms.movement.data.service;

import com.essers.wms.movement.TestDataBuilder;
import com.essers.wms.movement.data.entity.Pickinglist;
import com.essers.wms.movement.data.repository.CompanyRepository;
import com.essers.wms.movement.data.repository.MovementRepository;
import com.essers.wms.movement.data.repository.PickinglistRepository;
import com.essers.wms.movement.data.repository.SiteRepository;
import com.essers.wms.movement.data.repository.WarehouseRepository;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
class PickingListServiceImplementationTest {

    @Autowired
    private
    PickingListService pickingListService;

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
    void testGetAll() {
        Assert.assertEquals(pickingListService.getAll().size(), pickingListService.getAll().size());
    }

    @Test
    void testGetById() {
        Assert.assertEquals(testDataBuilder.getPickinglist().getPickingListId(), pickingListService.getById(testDataBuilder.getPickinglist().getPickingListId()).getPickingListId());
    }

    @Test
    void testGetByCompany() {
        Assert.assertEquals(testDataBuilder.getPickinglists().size(), pickingListService.getByCompany(testDataBuilder.getCompany()).size());
    }

    @Test
    void testSave() {
        Pickinglist pl = new Pickinglist();
        pl.setLocation("test location");
        pickingListService.save(pl);
        Assert.assertEquals(pl.getLocation(), pickingListService.getById(pl.getPickingListId()).getLocation());
    }
}