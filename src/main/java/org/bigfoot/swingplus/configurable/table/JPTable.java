package org.bigfoot.swingplus.configurable.table;

import lombok.Getter;
import lombok.Setter;
import org.bigfoot.swingplus.configurable.JPConfigurable;
import org.bigfoot.swingplus.configurable.table.dataprovider.JPTableDataProvider;

import javax.annotation.Nullable;
import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JPTable<MODEL> extends JTable implements JPConfigurable {

    @Getter
    @Setter
    private boolean useAlternateRowColors;

    @Setter
    @Getter
    private Color alternateRowColor;

    @Setter
    @Getter
    private Color rowColor;

    @Setter
    @Getter
    private JPTableDataProvider<MODEL> dataProvider;

    public JPTable() {
        super();
    }

    @Deprecated
    public JPTable(JPTableModel<MODEL> model) {
        super(model);
    }

    public JPTable(JPTableDataProvider<MODEL> dataProvider) {
        super(new JPTableModel<>());
        this.dataProvider = dataProvider;
        this.dataProvider.setTable(this);
        getTableHeader().setReorderingAllowed(false);
        onConfigure();
    }

    @Override
    public void onConfigure() {
        if (dataProvider != null) {
            dataProvider.load();
        }
    }

    public boolean isAtFirstPage() {
        if (dataProvider != null) {
            return dataProvider.getPageable().getPage() == 1;
        } else {
            throw new RuntimeException("JPTable#setPage is only supported when you use a JPDataProvider");
        }
    }

    public boolean isAtLastPage() {
        if (dataProvider != null) {
            return dataProvider.getPageable().getPage() == dataProvider.getPageable().getMaxPage();
        } else {
            throw new RuntimeException("JPTable#setPage is only supported when you use a JPDataProvider");
        }
    }

    public void nextPage() {
        setPage(getPage() + 1 < getMaxPage() ? getPage() + 1 : getMaxPage());
    }

    public void previousPage() {
        setPage(getPage() - 1 > 1 ? getPage() - 1 : 1);
    }

    public void setPage(long page) {
        if (dataProvider != null) {
            dataProvider.load(page);
        } else {
            throw new RuntimeException("JPTable#setPage is only supported when you use a JPDataProvider");
        }
    }

    public long getPage() {
        if (dataProvider != null) {
            return dataProvider.getPageable().getPage();
        } else {
            throw new RuntimeException("JPTable#getPage is only supported when you use a JPDataProvider");
        }
    }

    public int getMaxPage() {
        if (dataProvider != null) {
            return dataProvider.getPageable().getMaxPage();
        } else {
            throw new RuntimeException("JPTable#getMaxPage is only supported when you use a JPDataProvider");
        }
    }

    @Override
    public void setModel(@Nullable TableModel dataModel) {
        if (dataModel != null && !(dataModel instanceof JPTableModel)) {
            throw new IllegalArgumentException("Use JPTableModel!");
        } else if (dataModel != null) {
            try {
                super.setModel((JPTableModel<MODEL>) dataModel);
            } catch (ClassCastException ex) {
                throw new IllegalArgumentException("Use JPTableModel!", ex);
            }
        }
    }

    @Override
    public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
        Component component = super.prepareRenderer(renderer, row, column);

        if (isUseAlternateRowColors() && rowColor != null && alternateRowColor != null
                && !component.getBackground().equals(getSelectionBackground())) {
            Color bg = (row % 2 == 0 ? alternateRowColor : rowColor);
            component.setBackground(bg);
        }

        return component;
    }

    @Deprecated
    @SuppressWarnings("unchecked")
    public JPTableModel<MODEL> getJPModel() {
        return (JPTableModel<MODEL>) getModel();
    }

    public List<MODEL> getSelection() {
        List<MODEL> selected = new ArrayList<>();
        for (int i : getSelectedRows()) {
            if (getRowSorter() != null) {
                selected.add(getJPModel().getRowAt(getRowSorter().convertRowIndexToModel(i)));
            } else {
                selected.add(getJPModel().getRowAt(i));
            }
        }
        return selected;
    }

    @Override
    public String getToolTipText(MouseEvent event) {
        java.awt.Point p = event.getPoint();
        int rowIndex = rowAtPoint(p);
        int colIndex = columnAtPoint(p);
        int realColumnIndex = convertColumnIndexToModel(colIndex);

        if (getModel() instanceof JPTableModel) {
            return Optional.ofNullable(getJPModel().getTooltipAt(rowIndex, realColumnIndex)).map(Object::toString).orElse(null);
        }

        return super.getToolTipText(event);
    }
}