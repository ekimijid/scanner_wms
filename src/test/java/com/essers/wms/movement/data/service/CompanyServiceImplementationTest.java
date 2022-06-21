package com.essers.wms.movement.data.service;

import com.essers.wms.movement.TestDataBuilder;
import com.essers.wms.movement.data.repository.CompanyRepository;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
class CompanyServiceImplementationTest {
    private TestDataBuilder testDataBuilder;

    @Autowired
    CompanyService companyService;

    @Autowired
    CompanyRepository companyRepository;

    @BeforeEach
    void setUp() {
        testDataBuilder = new TestDataBuilder();
        companyRepository.save(testDataBuilder.getCompany());
    }

    @Test
    void testGetById() {

        Assert.assertEquals(testDataBuilder.getCompany().getName(), companyService.getById(testDataBuilder.getCompany().getId()).getName());
    }
}