package org.bigfoot.swingplus.form.components.impl.calendar;

import lombok.Getter;
import lombok.Setter;
import org.bigfoot.swingplus.configurable.components.calendar.JPDatepickerTextField;
import org.bigfoot.swingplus.form.components.JPFormComponent;

import java.time.LocalDate;

public class JPFormDatepickerTextField extends JPDatepickerTextField implements JPFormComponent<LocalDate> {

    @Getter
    @Setter
    private String id;

    @Getter
    @Setter
    private boolean updatable = true;

    public JPFormDatepickerTextField(String id) {
        this(id, LocalDate.now(), LocalDate.now().minusYears(100), LocalDate.now().plusYears(100));
    }

    public JPFormDatepickerTextField(String id, LocalDate datum) {
        this(id, datum, LocalDate.now().minusYears(100), LocalDate.now().plusYears(100));
    }

    public JPFormDatepickerTextField(String id, LocalDate minDate, LocalDate maxDate) {
        this(id, LocalDate.now(), minDate, maxDate);
    }

    public JPFormDatepickerTextField(String id, LocalDate curDate, LocalDate minDate, LocalDate maxDate) {
        super(curDate, minDate, maxDate);
        setId(id);
    }

    @Override
    public void setComponentValue(LocalDate value) {
        setDate(value);
    }

    @Override
    public LocalDate getComponentValue() {
        return getDate();
    }
}