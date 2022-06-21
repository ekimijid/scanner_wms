package com.essers.wms.movement.data.service;

import com.essers.wms.movement.TestDataBuilder;
import com.essers.wms.movement.data.repository.StockRepository;
import static org.junit.Assert.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
class StockServiceImplementationTest {
    @Autowired
    private StockService stockservice;

    @Autowired
    private StockRepository stockRepository;

    private TestDataBuilder testDataBuilder;

    @BeforeEach
    void setUp() {
        testDataBuilder = new TestDataBuilder();
        stockRepository.save(testDataBuilder.getStock());
    }

    @Test
    void testGetAll() {
        assertEquals(41, testDataBuilder.getStocks().size(), stockservice.getAll().size());
    }

    @Test
    void testGetById() {
        assertEquals(testDataBuilder.getStock().getId(), stockservice.getById(testDataBuilder.getStock().getId()).getId());
    }
}