package org.bigfoot.swingplus.configurable.components.calendar;

import lombok.Getter;
import org.bigfoot.swingplus.configurable.components.text.JPTextField;
import org.bigfoot.swingplus.eventlisteners.JPLambdaMouseClickListener;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class JPDatepickerTextField extends JPTextField {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    @Getter
    private LocalDate date;

    private JPopupMenu window;

    private JPCalendarPanel calendar;

    public JPDatepickerTextField() {
        this(LocalDate.now());
    }

    public JPDatepickerTextField(LocalDate curDate) {
        this(curDate, curDate.minusYears(100), curDate.plusYears(100));
    }

    public JPDatepickerTextField(LocalDate minDate, LocalDate maxDate) {
        this(LocalDate.now(), minDate, maxDate);
    }

    public JPDatepickerTextField(LocalDate curDate, LocalDate minDate, LocalDate maxDate) {
        super();
        this.date = curDate;
        setEditable(false);
        window = new JPopupMenu();
        window.setVisible(false);
        window.setLayout(new BorderLayout());

        calendar = new JPCalendarPanel(curDate, maxDate, minDate) {
            @Override
            public void onDateChange() {
                super.onDateChange();
                window.repaint();
                window.revalidate();
            }

            @Override
            public void onSelect() {
                JPDatepickerTextField.this.setDate(getDate());
                window.setVisible(false);
                window.repaint();
                window.revalidate();
                onChange();
            }
        };
        window.add(calendar, BorderLayout.CENTER);
        window.pack();

        this.addMouseListener((JPLambdaMouseClickListener) e -> {
            if (!window.isVisible() && isEnabled()) {
                if (date != null && (date.isBefore(getMin()) || date.isAfter(getMax()))) {
                    date = getMin();
                    calendar.setDate(date);
                } else if (date == null) {
                    calendar.setJaar(getMin().getYear());
                    calendar.setMaand(getMin().getYear());
                }
                Point p = JPDatepickerTextField.this.getLocationOnScreen();
                window.setInvoker(JPDatepickerTextField.this);
                window.setLocation((int) p.getX(),
                        (int) p.getY() + JPDatepickerTextField.this.getHeight());
                window.setVisible(true);
            } else if (window.isVisible()) {
                window.setVisible(false);
            }
        });
    }

    public void setDate(LocalDate date) {
        this.date = date;
        calendar.setDate(this.date);
        this.setText(date != null ? date.format(formatter) : null);
    }

    public void setMax(LocalDate date) {
        calendar.setMax(date);
    }

    public void setMin(LocalDate date) {
        calendar.setMin(date);
    }

    public LocalDate getMax() {
        return calendar.getMax();
    }

    public LocalDate getMin() {
        return calendar.getMin();
    }

    public void onChange() {

    }

    public void setMaandEnabled(boolean enabled) {
        calendar.setMaandEnabled(enabled);
    }

    public void setJaarEnabled(boolean enabled) {
        calendar.setJaarEnabled(enabled);
    }

    public void setPreviousIcon(Icon icon) {
        calendar.setPreviousIcon(icon);
    }

    public void setNextIcon(Icon icon) {
        calendar.setNextIcon(icon);
    }
}
