package com.essers.wms.movement.views;

import com.essers.wms.movement.data.entity.Company;
import com.essers.wms.movement.data.service.CompanyService;
import com.essers.wms.movement.data.service.PickingListService;
import static com.essers.wms.movement.util.ErrorAlert.message;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import javax.annotation.security.PermitAll;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
@PermitAll
@Route(value = "company", layout = MainView.class)
@PageTitle("CompanyList")
public final class CompanyView extends VerticalLayout {

    private final transient PickingListService pickingListService;

    public CompanyView(CompanyService companyService, PickingListService pickingListService) {
        this.pickingListService = pickingListService;
        ComboBox<Company> companyComboBox = new ComboBox<>("Company");
        companyComboBox.setItems(companyService.getAll());
        companyComboBox.setItemLabelGenerator(Company::getName);
        Button button = new Button("Select", buttonClickEvent -> selectCompany(companyComboBox.getValue()));
        add(companyComboBox, button);
    }

    public boolean selectCompany(Company company) {

        if (!pickingListService.getByCompany(company).isEmpty()) {
            UI.getCurrent().navigate("pickinglist/" + company.getId());
            return true;
        } else {
            message("No pickinglist");
            return false;
        }

    }
}
