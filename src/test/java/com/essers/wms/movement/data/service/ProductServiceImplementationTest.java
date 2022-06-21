package com.essers.wms.movement.data.service;

import com.essers.wms.movement.TestDataBuilder;
import com.essers.wms.movement.data.repository.ProductRepository;
import static org.junit.Assert.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
class ProductServiceImplementationTest {
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductRepository productRepository;

    private TestDataBuilder testDataBuilder;

    @BeforeEach
    void setUp() {
        testDataBuilder = new TestDataBuilder();
        productRepository.save(testDataBuilder.getProduct());
    }

    @Test
    void testGetByID() {
        assertEquals(testDataBuilder.getProduct().getproductId(), productService.getByID(testDataBuilder.getProduct().getproductId()).getproductId());
    }
}