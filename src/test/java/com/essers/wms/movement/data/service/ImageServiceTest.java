package com.essers.wms.movement.data.service;

import com.essers.wms.movement.TestDataBuilder;
import com.essers.wms.movement.data.entity.Damagereport;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ImageServiceTest {

    private TestDataBuilder testDataBuilder;
    private Damagereport damagereport;

    @BeforeEach
    void setUp() {
        testDataBuilder = new TestDataBuilder();
        damagereport = new Damagereport();
        damagereport.setProductName(testDataBuilder.getProduct().getName());
        damagereport.setTimestamp(LocalDateTime.now());

    }

    @Test
    void testGenerateImage() {
        ImageService imageService = mock(ImageService.class);
        imageService.generateImage(damagereport);
        verify(imageService).generateImage(damagereport);
    }

}