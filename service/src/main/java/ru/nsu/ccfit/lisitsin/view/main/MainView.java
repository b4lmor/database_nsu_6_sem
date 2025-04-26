package ru.nsu.ccfit.lisitsin.view.main;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.Route;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Route("")
public class MainView extends HorizontalLayout {

    private final SqlQuerySection sqlQuerySection;

    private final TableButtonsSection tableButtonsSection;

    private final ConnectionButtonsSection connectionButtonsSection;

    @PostConstruct
    private void init() {
        setSizeFull();
        setSpacing(false);
        setPadding(false);
        setMargin(false);

        Div content = createContent();

        add(tableButtonsSection, content);
        setFlexGrow(1, content);
    }

    private Div createContent() {
        Div content = new Div();
        content.setSizeFull();
        content.getStyle().set("padding", "1em");

        content.add(connectionButtonsSection, sqlQuerySection);

        return content;
    }
}