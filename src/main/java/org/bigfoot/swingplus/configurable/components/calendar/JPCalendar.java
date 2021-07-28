package org.bigfoot.swingplus.configurable.components.calendar;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.WindowEvent;
import java.time.LocalDate;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

public class JPCalendar extends JDialog {

    private final JPCalendarPanel calendar;

    private JButton okButton, cancelButton;

    public JPCalendar() {
        this(LocalDate.now());
    }

    public JPCalendar(LocalDate date) {
        this(date, date.plusYears(100), date.minusYears(100));
    }

    public JPCalendar(LocalDate date, LocalDate max, LocalDate min) {
        setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
        setResizable(false);
        setBounds(100, 100, 450, 300);
        getContentPane().setLayout(new BorderLayout());
        calendar = new JPCalendarPanel(date, max, min) {
            @Override
            public void onDateChange() {
                super.onDateChange();

            }
        };
        getContentPane().add(calendar, BorderLayout.CENTER);

        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        getContentPane().add(buttonPane, BorderLayout.SOUTH);
        okButton = new JButton("OK");
        okButton.addActionListener(event -> {
            internalConfirm(calendar.getDate());
        });
        buttonPane.add(okButton);
        getRootPane().setDefaultButton(okButton);

        cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(event -> {
            internalCancel();
        });
        buttonPane.add(cancelButton);
    }

    private final void internalConfirm(LocalDate date) {
        this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
        confirm(date);
    }

    private final void internalCancel() {
        this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
        cancel();
    }

    public void confirm(LocalDate date) {

    }

    public void cancel() {

    }

    public void setConfirmButtonText(String text) {
        okButton.setText(text);
    }

    public void setCancelButtonText(String text) {
        cancelButton.setText(text);
    }

    public JPCalendarPanel getCalendar() {
        return calendar;
    }

    public void setDate(LocalDate date) {
        calendar.setDate(date);
    }
}
