package ru.nsu.ccfit.lisitsin;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.server.ErrorEvent;
import com.vaadin.flow.server.ErrorHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomErrorHandler implements ErrorHandler {

    @Override
    public void error(ErrorEvent errorEvent) {
        log.error("{}", errorEvent.getThrowable().getMessage());
        if (UI.getCurrent() != null) {
            UI.getCurrent().access(() -> {
                Notification notification = Notification.show(
                        "Ошибка: " + errorEvent.getThrowable().getMessage(),
                        5000,
                        Notification.Position.MIDDLE
                );
                notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
            });
        }
    }
}
