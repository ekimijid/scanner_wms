package com.essers.wms.movement.views;

import com.essers.wms.movement.data.entity.Movement;
import com.essers.wms.movement.data.entity.State;
import com.essers.wms.movement.data.service.MovementService;
import com.essers.wms.movement.security.SecurityService;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.RouterLink;
import java.util.List;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public final class MainView extends AppLayout {

    private final transient SecurityService securityService;
    private final transient MovementService movementService;

    public MainView(SecurityService securityService, MovementService movementService) {
        this.securityService = securityService;
        this.movementService = movementService;
        this.createHeader();
        this.createDrawer();
    }

    private void createHeader() {
        H1 logo = new H1("WMS Scanner");
        logo.getStyle().set("font-size", "20px").set("color", "blue").set("font-weight", "bold").set("margin", "10,10");
        Button logout = new Button("Log out", e -> {
            movementsStatusChange();
            securityService.logout();
        });

        Avatar user = new Avatar(securityService.getAuthenticatedUser().getUsername());
        DrawerToggle dt = new DrawerToggle();
        dt.setIcon(VaadinIcon.MENU.create());
        HorizontalLayout header = new HorizontalLayout(dt, logo, user, logout);

        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        header.expand(logo);
        header.setWidth("100%");
        addToNavbar(header);

    }

    private void createDrawer() {
        RouterLink listLink = new RouterLink("Home", PortalView.class);
        listLink.setHighlightCondition(HighlightConditions.sameLocation());
        VerticalLayout layout = new VerticalLayout(listLink);
        layout.setWidth("20px");

        layout.getStyle().set("font-size", "20px").set("font-weight", "bold");
        addToDrawer(layout);
    }

    public boolean movementsStatusChange() {
        List<Movement> movements = movementService.getAll();
        String username = securityService.getAuthenticatedUser().getUsername();
        for (Movement mov : movements) {
            if (mov.getState() == State.IN_PROCESS && (mov.getInProgressUser().equals(username))) {
                mov.setState(State.PICK);
                mov.setInProgressUser(null);
                movementService.save(mov);
            }
        }
        return true;
    }
}
