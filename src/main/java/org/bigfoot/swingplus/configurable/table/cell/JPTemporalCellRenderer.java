package org.bigfoot.swingplus.configurable.table.cell;

import javax.annotation.Nonnull;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;

/**
 * @author Jonathan la Roi
 * @since 10/26/2020
 */
public class JPTemporalCellRenderer extends DefaultTableCellRenderer {

    private final DateTimeFormatter formattter;

    public JPTemporalCellRenderer(@Nonnull String format) {
        this.formattter = DateTimeFormatter.ofPattern(format);
    }

    public JPTemporalCellRenderer(@Nonnull DateTimeFormatter formattter) {
        this.formattter = formattter;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        if (value instanceof TemporalAccessor) {
            value = formattter.format((TemporalAccessor) value);
        }
        return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
    }
}
