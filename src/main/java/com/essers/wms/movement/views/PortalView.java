package com.essers.wms.movement.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import javax.annotation.security.PermitAll;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
@PermitAll
@Route(value = "", layout = MainView.class)
@PageTitle("WMS Scanner")
public final class PortalView extends VerticalLayout {
    public PortalView() {
        Button fp = new Button("FULL PALLETTE", buttonClickEvent -> fullpallete());
        fp.addThemeVariants(ButtonVariant.LUMO_LARGE);

        Button pp = new Button("PART PALLETTE", buttonClickEvent -> partpallete());
        pp.addThemeVariants(ButtonVariant.LUMO_LARGE);
        Button ip = new Button("   INTERRACK   ", buttonClickEvent -> interrack());
        ip.addThemeVariants(ButtonVariant.LUMO_LARGE);
        setSizeFull();
        setAlignItems(FlexComponent.Alignment.CENTER);
        setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        add(fp, pp, ip);

    }

    private static void interrack() {
        UI.getCurrent().navigate("company");
    }

    private static void partpallete() {
        UI.getCurrent().navigate("");
    }

    private static void fullpallete() {
        UI.getCurrent().navigate("company");
    }


}
