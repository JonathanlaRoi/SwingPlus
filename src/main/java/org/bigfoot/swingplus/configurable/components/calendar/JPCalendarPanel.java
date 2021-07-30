package org.bigfoot.swingplus.configurable.components.calendar;

import lombok.Getter;
import net.miginfocom.swing.MigLayout;
import org.bigfoot.swingplus.configurable.JPConfigurable;
import org.bigfoot.swingplus.configurable.JPPanel;
import org.bigfoot.swingplus.configurable.components.JPButton;
import org.bigfoot.swingplus.configurable.components.JPLabel;

import javax.swing.*;
import java.awt.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.TextStyle;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.Locale;

public class JPCalendarPanel extends JPanel implements JPConfigurable {

    private JPanel dagenPanel;

    private YearMonthSelector jmSelect;

    private LocalDate date, min, max;

    private boolean jaarEnabled = true, maandEnabled = true;

    @Getter
    private Icon nextIcon, previousIcon;

    public JPCalendarPanel() {
        this(LocalDate.now());
    }

    public JPCalendarPanel(LocalDate date) {
        this(date, date.minusYears(100), date.plusYears(100));
    }

    public JPCalendarPanel(LocalDate date, LocalDate max, LocalDate min) {
        if (max.isBefore(min)) {
            throw new IllegalArgumentException("max cannot be before min!");
        }
        this.min = min;
        this.max = max;
        this.date = date != null ? date : LocalDate.now();

        if (this.date.isAfter(this.max))
            this.date = this.max;
        if (this.date.isBefore(min))
            this.date = this.min;

        init();
    }

    @Override
    public void onConfigure() {
        init();
    }

    private void init() {
        this.removeAll();

        setLayout(new MigLayout("fillx", "[]", "[25px][grow]"));
        setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

        jmSelect = new YearMonthSelector() {
            @Override
            public void onChange(Year jaar, Month maand) {
                LocalDate now = LocalDate.now();
                LocalDate newDate = LocalDate.of(jaar.getYear(), maand.getMonth(), 1);
                if (now.getYear() == newDate.getYear() && now.getMonthValue() == newDate.getMonthValue()) {
                    newDate = newDate.withDayOfMonth(now.getDayOfMonth());
                }

                if (max != null && newDate.isAfter(max)) {
                    newDate = max;
                }
                if (min != null && newDate.isBefore(min)) {
                    newDate = min;
                }
                setDate(newDate);
            }
        };

        add(jmSelect, "cell 0 0, growx, shrinkx");
        dagenPanel = new JPanel();
        add(dagenPanel, "cell 0 1, grow");
        dagenPanel.setLayout(new MigLayout("al center center", "[grow]", "[30px]"));
        dagenPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        laadDagen();

        this.repaint();
        this.revalidate();
        onDateChange();
    }

    private void laadDagen() {
        dagenPanel.add(new JLabel(DayOfWeek.of(1).getDisplayName(TextStyle.FULL, Locale.getDefault())), "cell 1 0");
        dagenPanel.add(new JLabel(DayOfWeek.of(2).getDisplayName(TextStyle.FULL, Locale.getDefault())), "cell 2 0");
        dagenPanel.add(new JLabel(DayOfWeek.of(3).getDisplayName(TextStyle.FULL, Locale.getDefault())), "cell 3 0");
        dagenPanel.add(new JLabel(DayOfWeek.of(4).getDisplayName(TextStyle.FULL, Locale.getDefault())), "cell 4 0");
        dagenPanel.add(new JLabel(DayOfWeek.of(5).getDisplayName(TextStyle.FULL, Locale.getDefault())), "cell 5 0");
        dagenPanel.add(new JLabel(DayOfWeek.of(6).getDisplayName(TextStyle.FULL, Locale.getDefault())), "cell 6 0");
        dagenPanel.add(new JLabel(DayOfWeek.of(7).getDisplayName(TextStyle.FULL, Locale.getDefault())), "cell 7 0");

        LocalDate firstDayOfMonth = getFirstDayOfMonth();
        LocalDate firstDayOfNextMonth = getLastDayOfMonth().plusDays(1);
        LocalDate dayToAdd = firstDayOfMonth;
        int col = getDayOfWeek(dayToAdd);
        int row = 1;
        boolean addWeekLbl = true;
        while (!dayToAdd.equals(firstDayOfNextMonth)) {
            if (col > 7) {
                row++;
                col = 1;
                addWeekLbl = true;
            }
            if (addWeekLbl) {
                dagenPanel.add(new JLabel(getWeekName(dayToAdd)), "cell 0 " + row);
                addWeekLbl = false;
            }
            JPCalendarButton btn = new JPCalendarButton(dayToAdd.getDayOfMonth() + "");
            btn.setDate(dayToAdd);
            btn.addActionListener(event -> {
                setDate(btn.getDate());
                onSelect();
            });
            dagenPanel.add(btn, "cell " + col + " " + row);
            btn.setSelected(dayToAdd.equals(date));
            if (dayToAdd.isBefore(min) || dayToAdd.isAfter(max)) {
                btn.setEnabled(false);
            }
            dayToAdd = dayToAdd.plusDays(1);
            col++;

        }
    }

    private LocalDate getFirstDayOfMonth() {
        return getFirstDayOfMonth(date.getYear(), date.getMonthValue());
    }

    private LocalDate getFirstDayOfMonth(int jaar, int maand) {
        return LocalDate.of(jaar, maand, 1);
    }

    private LocalDate getLastDayOfMonth() {
        return getLastDayOfMonth(date.getYear(), date.getMonthValue());
    }

    private LocalDate getLastDayOfMonth(int jaar, int maand) {
        YearMonth yearMonthObject = YearMonth.of(jaar, maand);
        int daysInMonth = yearMonthObject.lengthOfMonth();
        LocalDate date = LocalDate.of(jaar, maand, 1);
        return date.withDayOfMonth(daysInMonth);
    }

    private int getTotalDaysInMonth() {
        return getTotalDaysInMonth(date.getYear(), date.getMonthValue());
    }

    private int getTotalDaysInMonth(int jaar, int maand) {
        YearMonth yearMonthObject = YearMonth.of(jaar, maand);
        return yearMonthObject.lengthOfMonth();
    }

    private int getDayOfWeek(LocalDate date) {
        return date.getDayOfWeek().getValue();
    }

    private String getWeekName(LocalDate date) {
        TemporalField woy = WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear();
        if (woy != null) {
            return "<html><b>" + date.get(woy) + "</b></html>";
        } else {
            return null;
        }
    }

    public void setJaar(int year) {
        LocalDate compare = getDate() != null ? getDate() : getMin();
        if (compare != null && (compare.withYear(year).isBefore(min) || compare.withYear(year).isAfter(max))) {
            jmSelect.setValue(Year.of(year), jmSelect.getMaand());
            init();
        }
    }

    public void setMaand(int maand) {
        if (maand > 12 || maand < 1) {
            jmSelect.setValue(jmSelect.getJaar(), Month.of(1));
            init();
        } else {
            LocalDate compare = getDate() != null ? getDate() : getMin();
            if (compare != null && (compare.withMonth(maand).isBefore(min) || compare.withMonth(maand).isAfter(max))) {
                jmSelect.setValue(jmSelect.getJaar(), Month.of(2));
                init();
            }
        }
    }

    public void setDate(LocalDate date) {
        this.date = date != null ? date : LocalDate.now();
        init();
    }

    public void setMax(LocalDate max) {
        if (max == null) {
            max = date.plusYears(100);
        }
        this.max = max;
        init();
    }

    public void setMin(LocalDate min) {
        if (min == null) {
            min = date.minusYears(100);
        }
        this.min = min;
        init();
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalDate getSelectedDate() {
        return date;
    }

    public LocalDate getMax() {
        return max;
    }

    public LocalDate getMin() {
        return min;
    }

    public void setJaarEnabled(boolean enabled) {
        jaarEnabled = enabled;
        init();
    }

    public void setMaandEnabled(boolean enabled) {
        maandEnabled = enabled;
        init();
    }

    public void setNextIcon(Icon icon) {
        this.nextIcon = icon;
        init();
    }

    public void setPreviousIcon(Icon icon) {
        this.previousIcon = icon;
        init();
    }

    class YearMonthSelector extends JPPanel {

        @Getter
        private Year jaar = Year.of(date.getYear());

        @Getter
        private Month maand = Month.of(date.getMonthValue());

        private JPButton btnJaarNext, btnMaandNext, btnJaarPrev, btnMaandPrev;

        private JPLabel jaarLbl, maandLbl;

        protected YearMonthSelector() {
            super(new MigLayout("fillx", "[][][][][][]", "[25px]"));
            btnJaarNext = new JPButton("", e -> {
                if (max.getYear() >= jaar.getYear() + 1) {
                    setValue(Year.of(jaar.getYear() + 1), maand);
                    onChange(jaar, maand);
                }
            });
            btnMaandNext = new JPButton("", e -> {
                if (max.getYear() > jaar.getYear() || max.getYear() == jaar.getYear() && max.getMonth().getValue() >= maand.getMonth() + 1) {
                    if (maand.getMonth() == 12) {
                        setValue(Year.of(jaar.getYear() + 1), Month.of(1));
                    } else {
                        setValue(jaar, Month.of(maand.getMonth() + 1));
                    }

                    onChange(jaar, maand);
                }
            });
            btnJaarPrev = new JPButton("", e -> {
                if (min.getYear() <= jaar.getYear() - 1) {
                    setValue(Year.of(jaar.getYear() - 1), maand);
                    onChange(jaar, maand);
                }
            });
            btnMaandPrev = new JPButton("", e -> {
                if (min.getYear() < jaar.getYear() || min.getYear() == jaar.getYear() && min.getMonth().getValue() <= maand.getMonth() - 1) {
                    if (maand.getMonth() == 1) {
                        setValue(Year.of(jaar.getYear() - 1), Month.of(12));
                    } else {
                        setValue(jaar, Month.of(maand.getMonth() - 1));
                    }
                    onChange(jaar, maand);
                }
            });

            if (nextIcon == null) {
                btnJaarNext.setText(">>");
                btnMaandNext.setText(">>");
            } else {
                btnJaarNext.setIcon(nextIcon);
                btnMaandNext.setIcon(nextIcon);
            }

            if (previousIcon == null) {
                btnJaarPrev.setText("<<");
                btnMaandPrev.setText("<<");
            } else {
                btnJaarPrev.setIcon(previousIcon);
                btnMaandPrev.setIcon(previousIcon);
            }

            jaarLbl = new JPLabel();
            maandLbl = new JPLabel();

            btnJaarNext.setContentAreaFilled(false);
            btnJaarNext.setBorderPainted(false);
            btnJaarPrev.setContentAreaFilled(false);
            btnJaarPrev.setBorderPainted(false);
            btnMaandNext.setContentAreaFilled(false);
            btnMaandNext.setBorderPainted(false);
            btnMaandPrev.setContentAreaFilled(false);
            btnMaandPrev.setBorderPainted(false);

            add(btnJaarPrev, "cell 0 0, shrink");
            add(jaarLbl, "cell 1 0, growx, shrinky");
            add(btnJaarNext, "cell 2 0, shrink");
            add(btnMaandPrev, "cell 3 0, shrink");
            add(maandLbl, "cell 4 0, growx, shrinky");
            add(btnMaandNext, "cell 5 0, shrink");
            setValue(jaar, maand);
            onConfigure();
        }

        @Override
        public void onConfigure() {
            btnJaarNext.setEnabled(jaarEnabled && jaar.getYear() < max.getYear());
            btnJaarPrev.setEnabled(jaarEnabled && jaar.getYear() > min.getYear());
            btnMaandNext.setEnabled(maandEnabled && //maand.getMaand() < 12 &&
                    (jaar.getYear() < max.getYear() || jaar.getYear() == max.getYear() && maand.getMonth() < max.getMonthValue()));
            btnMaandPrev.setEnabled(maandEnabled && //maand.getMaand() > 1 &&
                    (jaar.getYear() > min.getYear() || jaar.getYear() == min.getYear() && maand.getMonth() > min.getMonthValue()));
        }

        protected void setValue(Year jaar, Month maand) {
            this.jaar = jaar;
            this.maand = maand;
            jaarLbl.setText(jaar + "");
            maandLbl.setText(maand + "");
        }

        public void onChange(Year jaar, Month maand) {

        }
    }

    public void onDateChange() {

    }

    public void onSelect() {

    }
}