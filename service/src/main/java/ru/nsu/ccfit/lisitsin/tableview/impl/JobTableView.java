package ru.nsu.ccfit.lisitsin.tableview.impl;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.router.Route;
import ru.nsu.ccfit.lisitsin.dao.JobRepository;
import ru.nsu.ccfit.lisitsin.entity.Job;
import ru.nsu.ccfit.lisitsin.entity.JobTitle;
import ru.nsu.ccfit.lisitsin.forms.FormBuilder;
import ru.nsu.ccfit.lisitsin.tableview.TableView;

@Route("Должности сотрудников")
public class JobTableView extends TableView<Job> {

    private final JobRepository jobRepository;

    public JobTableView(JobRepository jobRepository) {
        super(Job.class, jobRepository);
        this.jobRepository = jobRepository;

        registerForm("Добавить должность сотруднику", registerForm());
    }

    private FormBuilder registerForm() {
        return (form, dialog) -> {

            IntegerField employeeIdField = new IntegerField("ID Сотрудника");
            IntegerField tpIdField = new IntegerField("ID Торговой точки");
            DatePicker startDateField = new DatePicker("Дата начала работы");
            NumberField salaryField = new NumberField("Зарплата");
            Select<JobTitle> jobTitleField = new Select<>();

            jobTitleField.setLabel("Должность");
            jobTitleField.setItems(JobTitle.values());

            Button saveButton = new Button(
                    "Сохранить",
                    e -> {

                        jobRepository.create(
                                employeeIdField.getValue(),
                                tpIdField.getValue(),
                                startDateField.getValue(),
                                salaryField.getValue(),
                                jobTitleField.getValue().name()
                        );

                        dialog.close();
                        refreshData();
                    }
            );

            form.add(employeeIdField, tpIdField, startDateField, salaryField, jobTitleField, saveButton);
        };
    }

}
