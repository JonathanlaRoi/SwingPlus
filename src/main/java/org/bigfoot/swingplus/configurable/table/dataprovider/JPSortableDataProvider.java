package org.bigfoot.swingplus.configurable.table.dataprovider;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.bigfoot.swingplus.configurable.table.JPTable;
import org.bigfoot.swingplus.configurable.table.columns.JPTableColumn;
import org.bigfoot.swingplus.configurable.table.sorter.JPSortOrder;
import org.bigfoot.swingplus.configurable.tablepanel.components.JPPageable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

/**
 * @author Jonathan la Roi
 * @since 27/10/2020
 */
public abstract class JPSortableDataProvider<MODEL, SORT> extends JPTableDataProvider<MODEL> {

    @Getter
    @Setter
    private SORT sortProperty;

    @NonNull
    @Getter
    @Setter
    private JPSortOrder sortOrder;

    public JPSortableDataProvider(Integer pageSize, List<JPTableColumn<MODEL, ?, ?>> jpTableColumns) {
        this(pageSize, jpTableColumns, null, JPSortOrder.ASCENDING);
    }

    public JPSortableDataProvider(Integer pageSize, List<JPTableColumn<MODEL, ?, ?>> jpTableColumns,
                                  @Nullable SORT sortProperty, @Nonnull JPSortOrder sortOrder) {
        super(pageSize, jpTableColumns);
        this.sortProperty = sortProperty;
        this.sortOrder = sortOrder;
    }

    @Override
    public void setTable(JPTable<MODEL> table) {
        super.setTable(table);
    }

    @Override
    protected void internalLoad(long page) {
        super.internalLoad(page);
    }

    @Override
    public final List<MODEL> getRowsForPage(JPPageable pageable) {
        return getRowsForPage(pageable, sortProperty, sortOrder);
    }

    public abstract List<MODEL> getRowsForPage(JPPageable pageable, SORT sort, JPSortOrder sortOrder);
}
