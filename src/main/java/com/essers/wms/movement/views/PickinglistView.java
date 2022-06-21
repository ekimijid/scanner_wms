package com.essers.wms.movement.views;

import com.essers.wms.movement.data.entity.Company;
import com.essers.wms.movement.data.entity.Pickinglist;
import com.essers.wms.movement.data.service.CompanyService;
import com.essers.wms.movement.data.service.PickingListService;
import static com.essers.wms.movement.util.ErrorAlert.urlErrorHandler;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.NotFoundException;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import java.util.Optional;
import java.util.logging.Logger;
import javax.annotation.security.PermitAll;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
@PermitAll
@Route(value = "pickinglist/:companyID", layout = MainView.class)
@PageTitle("PickingList")
public final class PickinglistView extends VerticalLayout implements BeforeEnterObserver {
    private static final Logger LOGGER=Logger.getLogger(PickinglistView.class.getName());
    private final Grid<Pickinglist> grid = new Grid<>(Pickinglist.class);
    private final transient PickingListService pickingListService;
    private final transient CompanyService companyService;
    private Company company;

    public PickinglistView(PickingListService pickingListService, CompanyService companyService) {
        this.pickingListService = pickingListService;
        this.companyService = companyService;
        super.setSizeFull();
        this.configureGrid();
        this.updateList();
        super.add(getContent());
    }

    private void configureGrid() {
        grid.setColumns("pickingListId");
        grid.addColumn(pickinglist -> pickinglist.getCompany().getName()).setHeader("Company");
        grid.addColumn(pickinglist -> pickinglist.getWmsSite().getName()).setHeader("Site");
        grid.addColumn(pickinglist -> pickinglist.getWmsWarehouse().getName()).setHeader("Warehouse");
        grid.asSingleSelect().addValueChangeListener(event -> routerLink(event.getValue()));
    }

    private void updateList() {
        grid.setItems(pickingListService.getByCompany(company));
    }

    private VerticalLayout getContent() {
        H5 logo = new H5("Picking-list");
        VerticalLayout content = new VerticalLayout(logo, grid);
        content.addClassNames("content");
        content.setSizeFull();
        return content;
    }

    private static void routerLink(Pickinglist value) {
        try {
            UI.getCurrent().navigate("movements/" + value.getPickingListId());
        } catch (NotFoundException e) {
            LOGGER.warning(urlErrorHandler()+e.getMessage());

        }
    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        try {
            Optional<String> id = beforeEnterEvent.getRouteParameters().get("companyID");
            if (id.isPresent()) {
                company = companyService.getById(Long.valueOf(id.get()));
                updateList();
            }
        } catch (NotFoundException e) {
            LOGGER.warning(urlErrorHandler()+e.getMessage());
        }
    }
}
