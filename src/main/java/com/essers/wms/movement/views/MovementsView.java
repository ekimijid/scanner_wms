package com.essers.wms.movement.views;

import com.essers.wms.movement.data.entity.Movement;
import com.essers.wms.movement.data.entity.Pickinglist;
import com.essers.wms.movement.data.entity.State;
import com.essers.wms.movement.data.service.MovementService;
import com.essers.wms.movement.data.service.PickingListService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.NotFoundException;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import javax.annotation.security.PermitAll;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import static com.essers.wms.movement.util.ErrorAlert.urlErrorHandler;
import org.springframework.context.annotation.Scope;

@org.springframework.stereotype.Component
@Scope("prototype")
@PermitAll
@Route(value = "movements/:pickinglistID", layout = MainView.class)
@PageTitle("Movements")
public final class MovementsView extends VerticalLayout implements BeforeEnterObserver {
    private static final Logger LOGGER = Logger.getLogger(MovementsView.class.getName());
    private final transient PickingListService pickingListService;
    private final transient MovementService movementService;

    private Pickinglist pickinglist;

    public MovementsView(PickingListService pickingListService, MovementService movementService) {
        this.pickingListService = pickingListService;
        this.movementService = movementService;
        Button button = new Button("Back to pickinglist", buttonClickEvent -> routerLinkBack(pickinglist));
        H5 logo = new H5("Movements");
        add(button, logo);
        addClassName("list-view");
        setSizeFull();
    }


    private static void routerLinkBack(Pickinglist pickinglist) {
        try {
            UI.getCurrent().navigate("pickinglist/" + pickinglist.getCompany().getId());

        } catch (NotFoundException e) {
            String error=urlErrorHandler()+e.getMessage();
            LOGGER.warning(error);

        }
    }

    private static Component getContent(Movement movement) {
        boolean isNotPicked = true;
        String username = "";
        movement.getInProgressUser();
        if (movement.getState() == (State.PICKED)) {
            username = movement.getHandledUser();
            isNotPicked = false;
        }
        if (movement.getState() == (State.IN_PROCESS)) {
            username = movement.getInProgressUser();
            isNotPicked = false;
        }

        TextArea info = new TextArea(null,
                "Movement" + movement.getMovementId()
                        + "                  " + movement.getState() + "\n" + movement.getMovementType()
                        + "                                       " + username + "\n"
                        + movement.getWmsCompany() + "\n" + movement.getWmsSite() + " - "
                        + movement.getWmsWarehouse(), (String) null);
        info.setWidth("300px");
        info.setReadOnly(true);

        Button scanButton = new Button("Scan barcode ", VaadinIcon.BARCODE.create(),
                buttonClickEvent -> routerLink(movement));
        scanButton.setEnabled(isNotPicked);

        Icon prodicon = new Icon(VaadinIcon.PACKAGE);
        Span quantity = new Span(movement.getQuantity() + " " + movement.getMovementType());
        HorizontalLayout layout = new HorizontalLayout(prodicon, quantity);

        Icon locIcon = new Icon(VaadinIcon.MAP_MARKER);
        Span from = new Span("From: " + movement.getLocationFrom());
        HorizontalLayout layout1 = new HorizontalLayout(locIcon, from);

        Icon locIcon1 = new Icon(VaadinIcon.MAP_MARKER);
        Span to = new Span("To: " + movement.getLocationTo());
        HorizontalLayout layout2 = new HorizontalLayout(locIcon1, to);

        VerticalLayout content = new VerticalLayout(info, layout, layout1, layout2, scanButton);
        content.getStyle().set("font-size", "14px").set("font-weight", "bold").set("margin", "1px, 1.5px");
        content.setSizeFull();
        content.addClassNames("content");
        return content;
    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        try {
            Optional<String> id = beforeEnterEvent.getRouteParameters().get("pickinglistID");
            if (id.isPresent()) {
                pickinglist = pickingListService.getById(Long.valueOf(id.get()));
                List<Movement> movements = movementService.getByPickinglist(pickinglist);
                for (Movement m : movements) {
                    add(getContent(m));
                }
            }
        } catch (NotFoundException e) {
            LOGGER.warning(urlErrorHandler()+e.getMessage());
        }
    }

    private static void routerLink(Movement value) {
        try {
            UI.getCurrent().navigate("scanner/" + value.getMovementId());
        } catch (NotFoundException e) {
            LOGGER.warning(urlErrorHandler()+e.getMessage());

        }

    }
}
