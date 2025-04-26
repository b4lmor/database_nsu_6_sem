package ru.nsu.ccfit.lisitsin.view.main;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.nsu.ccfit.lisitsin.dao.JdbcTemplateWrapper;
import ru.nsu.ccfit.lisitsin.forms.ConnectForm;

@Slf4j
@Component
@RequiredArgsConstructor
public class ConnectionButtonsSection extends HorizontalLayout {

    private final JdbcTemplateWrapper jdbcTemplateWrapper;

    @PostConstruct
    private void init() {
        setWidthFull();
        setJustifyContentMode(FlexComponent.JustifyContentMode.END);
        setSpacing(true);
        setPadding(true);

        Button exitButton = new Button("Отключиться");
        exitButton.getStyle()
                .set("color", "white")
                .set("background-color", "var(--lumo-error-color)");

        exitButton.addClickListener(e -> jdbcTemplateWrapper.close());

        Button connectButton = new Button("Подключиться");
        connectButton.getStyle()
                .set("color", "white")
                .set("background-color", "var(--lumo-success-color)");

        connectButton.addClickListener(e -> new ConnectForm(jdbcTemplateWrapper).open());

        add(exitButton, connectButton);
    }

}
