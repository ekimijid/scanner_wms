package com.essers.wms.movement;

import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.server.PWA;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jmx.JmxAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.MultipartAutoConfiguration;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication(exclude = {
        MultipartAutoConfiguration.class,
        JmxAutoConfiguration.class,
})
@PWA(name = "WMS", shortName = "WMS", offlinePath = "off.html", offlineResources = {"images/off.png"})
public class MovementApplication extends SpringBootServletInitializer implements AppShellConfigurator {

    public static void main(String[] args) {
        SpringApplication.run(MovementApplication.class, args);
    }

}
