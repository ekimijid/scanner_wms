package com.essers.wms.movement.views;

import com.essers.wms.movement.data.entity.Damagereport;
import com.essers.wms.movement.data.entity.Movement;
import com.essers.wms.movement.data.entity.Product;
import com.essers.wms.movement.data.service.DamageReportService;
import com.essers.wms.movement.data.service.ImageService;
import com.essers.wms.movement.data.service.MovementService;
import com.essers.wms.movement.data.service.ProductServiceImplementation;
import static com.essers.wms.movement.util.ErrorAlert.urlErrorHandler;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MultiFileMemoryBuffer;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.NotFoundException;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.logging.Logger;
import javax.annotation.security.PermitAll;
import javax.imageio.ImageIO;
import org.springframework.context.annotation.Scope;

@org.springframework.stereotype.Component
@Scope("prototype")
@PermitAll
@Route(value = "damage/:movementID", layout = MainView.class)
@PageTitle("WMS Scanner")
public final class PhotoView extends VerticalLayout implements BeforeEnterObserver {
    private static final String PIXEL = "200px";
    private static final Logger LOGGER = Logger.getLogger(PhotoView.class.getName());
    private final Damagereport damagereport;
    private Product product;
    private final transient MovementService movementService;
    private final transient ImageService imageService;
    private final transient VerticalLayout imageContainer;
    private final transient DamageReportService damageReportService;
    private final transient ProductServiceImplementation productServ;
    private Movement movement;

    public PhotoView(ImageService imageService, ProductServiceImplementation productServ,
                     DamageReportService damageReportService, MovementService movementService) {
        this.imageService = imageService;
        this.damageReportService = damageReportService;
        this.movementService = movementService;
        this.productServ = productServ;
        damagereport = new Damagereport();
        imageContainer = new VerticalLayout();
        imageContainer.setWidth(PIXEL);
        imageContainer.setHeight(PIXEL);
        imageContainer.getStyle().set("overflow-x", "auto");
        Button buttonback = new Button("Back to movements", buttonClickEvent -> routerLink(movement));

        H5 logo = new H5("Damage Raports:");
        add(buttonback, logo);

        for (Damagereport d : damageReportService.getAll()) {
            add(getContent(d));
        }
        initUploaderImage();
        add(imageContainer);
    }

    private Component getContent(Damagereport damagereport) {
        Movement mov = movementService.getById(Long.valueOf(damagereport.getMovementID()));
        TextArea info = new TextArea(null,
                "Product: " + damagereport.getProductName() + "" + "\nProductID: " + damagereport.getProductID() +
                        "\nLocation: " + mov.getLocationFrom() + "  " + "\nTimestamp: "
                        + damagereport.getTimestamp().format(DateTimeFormatter.ISO_DATE_TIME) + "\nOperator: "
                        + mov.getInProgressUser(), (String) null);
        info.setWidth("300px");
        info.setReadOnly(true);

        Image img = imageService.generateImage(damagereport);
        img.setHeight(PIXEL);
        img.setWidth(PIXEL);
        VerticalLayout content = new VerticalLayout(info, img);
        content.addClassNames("content");
        content.setSizeFull();
        return content;
    }


    private void initUploaderImage() {
        Upload upload;
        MultiFileMemoryBuffer buffer = new MultiFileMemoryBuffer();
        upload = new Upload(buffer);
        upload.setAcceptedFileTypes("image/jpeg", "image/jpg", "image/png", "image/gif");
        Button uploadButton = new Button("Take Photo...");
        ByteArrayOutputStream pngContent = new ByteArrayOutputStream();
        uploadButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        upload.setUploadButton(uploadButton);
        upload.addSucceededListener(event -> {
            String attachmentName = event.getFileName();
            try {
                ImageIO.write(ImageIO.read(buffer.getInputStream(attachmentName)), "png", pngContent);
                saveDamagePicture(pngContent.toByteArray(), product.getName(), product.getproductId(), movement);
                showImage();
            } catch (IOException e) {
                LOGGER.warning("Something went wrong with the file upload "+e.getMessage());
            }
        });
        add(upload);
    }

    private void saveDamagePicture(byte[] imageBytes, String filename, String productID, Movement movement) {
        damagereport.setProductName(filename);
        damagereport.setProductID(productID);
        damagereport.setMovementID(movement.getMovementId().toString());
        damagereport.setImage(imageBytes);
        damagereport.setTimestamp(LocalDateTime.now());
        damageReportService.saveReport(damagereport);
    }

    public void showImage() {
        Image image = imageService.generateImage(damagereport);
        image.setHeight("100%");
        imageContainer.removeAll();
        imageContainer.add(image);
    }

    private static void routerLink(Movement movement) {
        try {
            UI.getCurrent().navigate("movements/" + movement.getPickinglist().getPickingListId());
        } catch (NotFoundException e) {
            LOGGER.warning(urlErrorHandler()+e.getMessage());

        }
    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        try {
            Optional<String> id = beforeEnterEvent.getRouteParameters().get("movementID");
            if (id.isPresent()) {
                movement = movementService.getById(Long.valueOf(id.get()));
                product = productServ.getByID(movement.getProductId());
            }
        } catch (NotFoundException e) {
            LOGGER.warning(urlErrorHandler()+e.getMessage());
        }
    }
}

