package com.essers.wms.movement.views;

import com.essers.wms.movement.data.entity.Movement;
import com.essers.wms.movement.data.entity.Product;
import com.essers.wms.movement.data.entity.State;
import com.essers.wms.movement.data.service.MovementService;
import com.essers.wms.movement.data.service.ProductService;
import com.essers.wms.movement.security.SecurityService;
import com.vaadin.flow.component.ClientCallable;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.NotFoundException;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import java.util.logging.Logger;
import javax.annotation.security.PermitAll;
import java.time.LocalDateTime;
import java.util.Optional;

import static com.essers.wms.movement.util.ErrorAlert.urlErrorHandler;
import static com.essers.wms.movement.util.ErrorAlert.message;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
@PermitAll
@Route(value = "scanner/:movementID", layout = MainView.class)
@PageTitle("WMS Scanner")
public final class ScannerWMSView extends Div implements BeforeEnterObserver {
    private static final Logger LOGGER = Logger.getLogger(ScannerWMSView.class.getName());
    private final transient MovementService movementService;
    private final transient SecurityService securityService;
    private final transient ProductService productService;
    private TextField textFieldScanner;

    public ScannerWMSView(MovementService movementService, SecurityService securityService,
                          ProductService productService) {
        this.movementService = movementService;
        this.securityService = securityService;
        this.productService = productService;
        setSizeFull();
        scannerKeyTrigger();
    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        try {
            Optional<String> id = beforeEnterEvent.getRouteParameters().get("movementID");
            if (id.isPresent()) {
                Movement movement = movementService.getById(Long.valueOf(id.get()));
                Product product = productService.getByID(movement.getProductId());
                details(movement, product);
            }
        } catch (NotFoundException e) {
            String error=urlErrorHandler()+e.getMessage();
            LOGGER.warning(error);

        }
    }

    public void details(Movement movement, Product product) {

        if (movement.getState() == (State.PICKED)) {
            message("Barcode has already been scanned!");
            Button button = new Button("Back to movements", buttonClickEvent -> routerLink(movement));
            add(button);
        } else {
            Button alertbutton = new Button("Damage ", buttonClickEvent -> imageSend(movement));
            alertbutton.setIcon(VaadinIcon.FILE.create());
            alertbutton.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_ERROR);

            movement.setInProgressUser(securityService.getAuthenticatedUser().getUsername());
            movement.setState(State.IN_PROCESS);
            movementService.save(movement);

            TextArea info = new TextArea(null, product.getName().toUpperCase() + "\n" + product.getDescription(),
                    (String) null);
            info.setWidth("300px");
            info.setReadOnly(true);

            Icon locIcon = new Icon(VaadinIcon.MAP_MARKER);
            Span from = new Span("From: " + movement.getLocationFrom());
            HorizontalLayout layout1 = new HorizontalLayout(locIcon, from);

            Icon locIcon1 = new Icon(VaadinIcon.MAP_MARKER);
            Span to = new Span("To: " + movement.getLocationTo());
            HorizontalLayout layout2 = new HorizontalLayout(locIcon1, to);

            textFieldScanner = new TextField();
            textFieldScanner.setLabel("Barcode    ");
            Button controlbutton = new Button("Enter ", buttonClickEvent -> scanLocation(movement,
                    textFieldScanner.getValue()));
            VerticalLayout contentPalletID = new VerticalLayout(alertbutton, info, layout1, layout2, textFieldScanner
                    , controlbutton);
            contentPalletID.setPadding(false);
            contentPalletID.setSpacing(false);
            add(contentPalletID);
        }

    }

    public void scanLocation(Movement movement, String barcode) {
        if (movement.getPalleteNummer().equals(barcode)) {
            textFieldScanner = new TextField();
            textFieldScanner.setLabel("Location ");

            Button button = new Button("Enter", buttonClickEvent -> updatePickinglist(movement,
                    textFieldScanner.getValue()));

            VerticalLayout contentLocation = new VerticalLayout(textFieldScanner, button);
            contentLocation.setSpacing(true);
            contentLocation.setPadding(false);
            add(contentLocation);
        } else {
            message("Pallet ID is not correct");
        }

    }

    public void updatePickinglist(Movement movement, String string) {
        if (movement.getLocation().equals(string)) {
            movement.setState(State.PICKED);
            movement.setInProgressUser("");
            movement.setHandledUser(securityService.getAuthenticatedUser().getUsername());
            movement.setInProgressTimestamp(LocalDateTime.now());
            movementService.save(movement);
            routerLink(movement);
        } else {
            message("Shipping location is not correct");
        }
    }

    private static void routerLink(Movement movement) {
        try {
            UI.getCurrent().navigate("movements/" + movement.getPickinglist().getPickingListId());
        } catch (NotFoundException e) {
            LOGGER.warning(urlErrorHandler()+e.getMessage());
        }
    }

    private static void imageSend(Movement movement) {
        try {
            UI.getCurrent().navigate("damage/" + movement.getMovementId());
        } catch (NotFoundException e) {
            LOGGER.warning(urlErrorHandler()+e.getMessage());

        }

    }

    public void scannerKeyTrigger() {
        getElement().executeJs("""
                $(document).ready(() => {$(document).keydown(e => {
                if ( $('input:focus').length > 0 ) {  return; }
                if(e.key.length !== 1){return;}
                this.$server.handleKeyPress(e.key)
                })});
                """);
    }

    @ClientCallable
    private void handleKeyPress(String key) {
        TextField textField = new TextField();
        textField.setValue(key);
        textFieldScanner.focus();
        textFieldScanner.setValue(textField.getValue());
    }
}
