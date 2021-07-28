package org.bigfoot.swingplus.configurable.table.cell;

import net.miginfocom.swing.MigLayout;
import org.bigfoot.swingplus.configurable.JPPanel;
import org.bigfoot.swingplus.configurable.table.JPTable;
import org.bigfoot.swingplus.configurable.table.columns.JPTableColumn;
import org.bigfoot.swingplus.configurable.table.columns.JPTableComponentColumn;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

/**
 * @author Jonathan la Roi
 * @since 10/23/2020
 */
public class JPComponentCellEditor<MODEL> extends AbstractCellEditor implements TableCellEditor, TableCellRenderer {

    private final JPPanel content;

    public JPComponentCellEditor() {
        content = new JPPanel(new MigLayout("insets 1","[grow]","[grow]"));
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        return getComponent((JPTable<MODEL>) table, row, column, isSelected);
    }

    @Override
    public Object getCellEditorValue() {
        return null;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        return getComponent((JPTable<MODEL>) table, row, column, isSelected);
    }

    private Component getComponent(JPTable<MODEL> table, int row, int column, boolean isSelected) {
        JPTableColumn<MODEL, ?, ?> col = table.getDataProvider().getColumns().get(column);
        if (col instanceof JPTableComponentColumn) {
            Component component = ((JPTableComponentColumn<MODEL, ?>) col).getComponent(table.getJPModel().getRowAt(row));
            if (isSelected) {
                if (component instanceof Container) {
                    component.setBackground(table.getSelectionBackground());
                }
                content.setBackground(table.getSelectionBackground());
            } else {
                if (component instanceof Container) {
                    component.setBackground(table.getBackground());
                }
                content.setBackground(table.getBackground());
            }
            content.removeAll();
            content.add(component, "grow");
            content.repaint();
            content.revalidate();
            return content;
        }
        return new JPPanel();
    }
}
