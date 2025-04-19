package ru.nsu.ccfit.lisitsin.tableview.impl;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.router.Route;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.nsu.ccfit.lisitsin.dao.impl.TradingPointRepository;
import ru.nsu.ccfit.lisitsin.entity.TradingPoint;
import ru.nsu.ccfit.lisitsin.forms.FormBuilder;
import ru.nsu.ccfit.lisitsin.tableview.DefaultTableView;

@Route("Торговые точки")
public class TradingPointTableView extends DefaultTableView<TradingPoint> {

    private final TradingPointRepository tradingPointRepository;

    public TradingPointTableView(JdbcTemplate jdbcTemplate) {
        super(TradingPoint.class, new TradingPointRepository(jdbcTemplate));
        this.tradingPointRepository = (TradingPointRepository) this.genericRepository;

        registerForm("Прикрепить к секции", updateDepartmentForm());
    }

    private FormBuilder updateDepartmentForm() {
        return (form, dialog) -> {

            IntegerField departmentIdField = new IntegerField("ID Секции");
            IntegerField tpIdField = new IntegerField("ID Торговой точки");

            Button saveButton = new Button(
                    "Сохранить",
                    e -> {
                        tradingPointRepository.updateDepartment(
                                departmentIdField.getValue(),
                                tpIdField.getValue()
                        );

                        dialog.close();
                        refreshData();
                    }
            );

            form.add(tpIdField, departmentIdField, tpIdField, saveButton);
        };
    }

}
