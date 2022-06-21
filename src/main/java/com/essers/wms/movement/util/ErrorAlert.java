package com.essers.wms.movement.util;

import com.essers.wms.movement.views.PortalView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;

public final class ErrorAlert {
    private ErrorAlert() {
    }

    public static void message(String string) {
        Notification notification = Notification.show(string);
        notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
        notification.setPosition(Notification.Position.MIDDLE);
    }

    public static String urlErrorHandler() {
        message("An error has occurred! ");
        UI.getCurrent().navigate(PortalView.class);
        return "An error has occurred! ";
    }
}
