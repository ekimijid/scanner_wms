package com.essers.wms.movement.util;

import com.essers.wms.movement.views.PortalView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.ErrorParameter;
import com.vaadin.flow.router.HasErrorParameter;
import com.vaadin.flow.router.NotFoundException;

import javax.servlet.http.HttpServletResponse;

import static com.essers.wms.movement.util.ErrorAlert.message;

@Tag(Tag.DIV)
public class RouteNotFoundError extends Component implements HasErrorParameter<NotFoundException> {

    @Override
    public int setErrorParameter(BeforeEnterEvent event, ErrorParameter<NotFoundException> parameter) {
        message(event.getLocation().getPath() + " not found");
        event.rerouteTo(PortalView.class);
        return HttpServletResponse.SC_NOT_FOUND;
    }


}
