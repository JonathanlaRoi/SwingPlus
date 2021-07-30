package org.bigfoot.swingplus.form.components.impl.calendar;

import lombok.Getter;
import lombok.Setter;
import org.bigfoot.swingplus.configurable.components.calendar.JXPDatePicker;
import org.bigfoot.swingplus.form.components.JPFormComponent;
import org.bigfoot.swingplus.util.JPDateUtils;

import java.time.LocalDate;
import java.util.Date;
import java.util.Locale;

/**
 * @author Jonathan la Roi
 * @since 30/07/2021
 */
public class JXPFormDatepicker extends JXPDatePicker implements JPFormComponent<LocalDate> {

    @Getter
    @Setter
    private String id;

    @Getter
    @Setter
    private boolean updatable = true;

    public JXPFormDatepicker(String id) {
        this(id, (Date) null, Locale.getDefault());
    }

    public JXPFormDatepicker(String id, Date selected) {
        this(id, selected, Locale.getDefault());
    }

    public JXPFormDatepicker(String id, LocalDate selection) {
        this(id, selection, Locale.getDefault());
    }

    public JXPFormDatepicker(String id, Locale locale) {
        this(id, (Date) null, locale);
    }

    public JXPFormDatepicker(String id, LocalDate selection, Locale locale) {
        this(id, JPDateUtils.mapLocalDateToUtilDate(selection), locale);
    }

    public JXPFormDatepicker(String id, Date selection, Locale locale) {
        super(selection, locale);
        setId(id);
    }

    @Override
    public void setComponentValue(LocalDate value) {
        setLocalDate(value);
    }

    @Override
    public LocalDate getComponentValue() {
        return getLocalDate();
    }
}
