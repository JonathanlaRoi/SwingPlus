package org.bigfoot.swingplus.configurable.components.calendar;

import org.bigfoot.swingplus.util.JPDateUtils;
import org.jdesktop.swingx.JXDatePicker;

import java.time.LocalDate;
import java.util.Date;
import java.util.Locale;

/**
 * @author Jonathan la Roi
 * @since 30/07/2021
 */
public class JXPDatePicker extends JXDatePicker {

    public JXPDatePicker() {
        this((Date) null, Locale.getDefault());
    }

    public JXPDatePicker(Date selected) {
        this(selected, Locale.getDefault());
    }

    public JXPDatePicker(LocalDate selection) {
        this(selection, Locale.getDefault());
    }

    public JXPDatePicker(Locale locale) {
        this((Date) null, locale);
    }

    public JXPDatePicker(LocalDate selection, Locale locale) {
        this(JPDateUtils.mapLocalDateToUtilDate(selection), locale);
    }

    public JXPDatePicker(Date selection, Locale locale) {
        super(selection, locale);
    }

    public void setLocalDate(LocalDate selection) {
        setDate(JPDateUtils.mapLocalDateToUtilDate(selection));
    }

    public LocalDate getLocalDate() {
        return JPDateUtils.mapUtilDateToLocalDate(getDate());
    }

}
