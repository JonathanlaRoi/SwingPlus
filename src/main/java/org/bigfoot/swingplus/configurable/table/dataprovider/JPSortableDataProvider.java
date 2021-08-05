package org.bigfoot.swingplus.configurable.table.dataprovider;

import org.bigfoot.swingplus.configurable.table.columns.JPTableColumn;
import org.bigfoot.swingplus.configurable.table.sorter.JPSort;
import org.bigfoot.swingplus.configurable.table.sorter.JPSortOrder;
import org.bigfoot.swingplus.configurable.tablepanel.components.JPPageable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Jonathan la Roi
 * @since 27/10/2020
 */
public abstract class JPSortableDataProvider<MODEL, SORT> extends JPTableDataProvider<MODEL> {

    private JPSort<SORT> sortProperty;

    public JPSortableDataProvider(Integer pageSize, List<JPTableColumn<MODEL, ?, ?>> jpTableColumns) {
        this(pageSize, jpTableColumns, null, JPSortOrder.UNSORTED);
    }

    public JPSortableDataProvider(Integer pageSize, List<JPTableColumn<MODEL, ?, ?>> jpTableColumns,
                                  @Nullable SORT sortProperty, @Nonnull JPSortOrder sortOrder) {
        super(pageSize, jpTableColumns);
        this.sortProperty = new JPSort<>(sortProperty, sortOrder);
    }

    @Override
    public final List<MODEL> getRowsForPage(JPPageable pageable) {
        if (getTable().getRowSorter() != null) {
            sortProperty = getSortOrderFromTable();
        } else if (getSortFromCustomSource() != null) {
            sortProperty = getSortFromCustomSource();
        }
        return getRowsForPage(pageable, sortProperty);
    }

    private JPSort<SORT> getSortOrderFromTable() {
        JPSort<SORT> sort = null;
        List<? extends RowSorter.SortKey> sortKeys = getTable().getRowSorter().getSortKeys();
        for(RowSorter.SortKey s : sortKeys) {
            JPTableColumn<MODEL, ?, ?> col = getColumns().get(s.getColumn());
            JPSortOrder sortOrder = JPSortOrder.of(s.getSortOrder());
            sort = new JPSort<>((SORT) col.getSortProperty(), sortOrder);
            break;
        };
        return sort;
    }

    /**
     * If you don't use the default jtable sorter, then you can use this method to get a custom sort from another source
     * @return {@link List<JPSort<SORT>>}
     */
    public JPSort<SORT> getSortFromCustomSource() {
        return null;
    }

    public abstract List<MODEL> getRowsForPage(JPPageable pageable, JPSort<SORT> sort);
}
