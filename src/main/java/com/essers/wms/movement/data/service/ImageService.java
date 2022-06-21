package com.essers.wms.movement.data.service;

import com.essers.wms.movement.data.entity.Damagereport;
import com.essers.wms.movement.data.repository.DamagereportRepository;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.server.StreamResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;

@Service
public class ImageService {

    private DamagereportRepository damagereportRepository;

    @Autowired
    public ImageService(DamagereportRepository damagereportRepository) {
        this.damagereportRepository = damagereportRepository;
    }

    public Image generateImage(Damagereport damagereport) {
        Long id = damagereport.getId();
        StreamResource sr = new StreamResource("report", () -> {
            Damagereport attached = damagereportRepository.getById(id);
            return new ByteArrayInputStream(attached.getImage());
        });
        sr.setContentType("image/png");
        return new Image(sr, "damage-picture");

    }

}
