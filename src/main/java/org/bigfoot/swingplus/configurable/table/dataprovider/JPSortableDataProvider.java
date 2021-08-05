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

    private List<JPSort<SORT>> sortProperties;

    public JPSortableDataProvider(Integer pageSize, List<JPTableColumn<MODEL, ?, ?>> jpTableColumns) {
        this(pageSize, jpTableColumns, null, JPSortOrder.UNSORTED);
    }

    public JPSortableDataProvider(Integer pageSize, List<JPTableColumn<MODEL, ?, ?>> jpTableColumns,
                                  @Nullable SORT sortProperty, @Nonnull JPSortOrder sortOrder) {
        super(pageSize, jpTableColumns);
        if(sortProperty != null) {
            this.sortProperties = new ArrayList<>(Collections.singletonList(new JPSort<>(sortProperty, sortOrder)));
        } else {
            this.sortProperties = new ArrayList<>();
        }
    }

    public JPSortableDataProvider(Integer pageSize, List<JPTableColumn<MODEL, ?, ?>> jpTableColumns,
                                  @Nonnull List<JPSort<SORT>> sortProperties) {
        super(pageSize, jpTableColumns);
        this.sortProperties = sortProperties;
    }

    @Override
    public final List<MODEL> getRowsForPage(JPPageable pageable) {
        if (getTable().getRowSorter() != null) {
            sortProperties = getSortOrderFromTable();
        } else if (getSortFromCustomSource() != null) {
            sortProperties = getSortFromCustomSource();
        }
        return getRowsForPage(pageable, sortProperties);
    }

    private List<JPSort<SORT>> getSortOrderFromTable() {
        List<JPSort<SORT>> sort = new ArrayList<>();
        List<? extends RowSorter.SortKey> sortKeys = getTable().getRowSorter().getSortKeys();
        sortKeys.forEach(s -> {
            JPTableColumn<MODEL, ?, ?> col = getColumns().get(s.getColumn());
            JPSortOrder sortOrder = JPSortOrder.of(s.getSortOrder());
            sort.add(new JPSort<SORT>((SORT) col.getSortProperty(), sortOrder));
        });
        return sort;
    }

    /**
     * If you don't use the default jtable sorter, then you can use this method to get a custom sort from another source
     * @return {@link List<JPSort<SORT>>}
     */
    public List<JPSort<SORT>> getSortFromCustomSource() {
        return new ArrayList<>();
    }

    public abstract List<MODEL> getRowsForPage(JPPageable pageable, List<JPSort<SORT>> sort);
}
