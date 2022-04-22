package org.bigfoot.swingplus.configurable.components.calendar;

import org.bigfoot.swingplus.configurable.JPConfigurable;
import org.bigfoot.swingplus.util.JPDateUtils;
import org.jdesktop.swingx.JXDatePicker;

import java.time.LocalDate;
import java.util.Date;
import java.util.Locale;

/**
 * A SwingPlus version of the {@link JXDatePicker} from swingx with a few small additions for using {@link LocalDate}
 *
 * @author Jonathan la Roi
 * @since 30/07/2021
 */
public class JXPDatePicker extends JXDatePicker implements JPConfigurable {

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

    public void setLowerBound(LocalDate date) {
        if (date != null) {
            getMonthView().setLowerBound(java.sql.Date.valueOf(date));
        }
    }

    public void setUpperBound(LocalDate date) {
        if (date != null) {
            getMonthView().setUpperBound(java.sql.Date.valueOf(date));
        }
    }

}
