package org.bigfoot.swingplus.configurable.table;

import lombok.Getter;
import lombok.Setter;
import org.bigfoot.swingplus.configurable.table.columns.JPTableColumn;

import javax.annotation.Nonnull;
import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JPTableModel<MODEL> extends AbstractTableModel {

    @Getter
    private List<JPTableColumn<MODEL, ?, ?>> columns = new ArrayList<>();

    @Setter
    @Getter
    private List<MODEL> records;

    public JPTableModel() {
        super();
        this.records = new ArrayList<>();
    }

    public JPTableModel(@Nonnull List<MODEL> records) {
        super();
        this.records = records;
    }

    public JPTableModel<MODEL> setColumns(List<JPTableColumn<MODEL, ?, ?>> columns) {
        this.columns = columns != null ? columns : new ArrayList<>();
        return this;
    }

    public JPTableModel<MODEL> addColumn(JPTableColumn<MODEL, ?, ?> column) {
        if (columns == null) {
            columns = new ArrayList<>();
        }
        columns.add(column);
        return this;
    }

    @Override
    public Object getValueAt(int row, int col) {
        if (columns.size() > 0 && records.size() > 0) {
            return columns.get(col).getValueForRow(records.get(row));
        }
        return null;
    }

    public Object getTooltipAt(int row, int col) {
        return Optional.ofNullable(columns.get(col))
                .map(c -> c.getTooltipValueForRow(records.get(row)))
                .orElse(null);
    }

    @Override
    public Class<?> getColumnClass(int col) {
        return columns != null ? columns.get(col).getType() : Object.class;
    }

    @Override
    public String getColumnName(int col) {
        return columns != null ? columns.get(col).getHeader() : null;
    }

    @Override
    public int getColumnCount() {
        return columns != null ? columns.size() : 0;
    }

    @Override
    public int getRowCount() {
        return records != null ? records.size() : 0;
    }

    public MODEL getRowAt(int row) {
        return records.get(row);
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columns.get(columnIndex).isEditable();
    }
}
