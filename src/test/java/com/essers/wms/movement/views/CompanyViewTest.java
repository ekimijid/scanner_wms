package com.essers.wms.movement.views;

import com.essers.wms.movement.TestDataBuilder;
import com.essers.wms.movement.data.entity.Company;
import com.essers.wms.movement.data.repository.CompanyRepository;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.testbench.ButtonElement;
import com.vaadin.flow.component.combobox.ComboBox;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
class CompanyViewTest {
    @Autowired
    private CompanyView companyView;

    @Autowired
    private CompanyRepository companyRepository;

    private TestDataBuilder testDataBuilder;


    @BeforeEach
    void setUp() {
        testDataBuilder = new TestDataBuilder();
        companyRepository.save(testDataBuilder.getCompany());
    }

    @Test
    void testComboboxCompany() {
        ComboBox<Company> comboBox = new ComboBox<>();
        comboBox.setItems(testDataBuilder.getCompanies());
        comboBox.setValue(testDataBuilder.getCompany());
        Assert.assertEquals(comboBox.getValue(), testDataBuilder.getCompany());
    }
    
}