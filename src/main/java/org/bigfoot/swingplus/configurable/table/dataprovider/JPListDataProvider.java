package org.bigfoot.swingplus.configurable.table.dataprovider;

import org.bigfoot.swingplus.configurable.table.columns.JPTableColumn;
import org.bigfoot.swingplus.configurable.tablepanel.components.JPPageable;

import java.util.List;

/**
 * @author Jonathan la Roi
 * @since 27/10/2020
 */
public abstract class JPListDataProvider<MODEL> extends JPTableDataProvider<MODEL> {

    public JPListDataProvider(List<JPTableColumn<MODEL, ?, ?>> jpTableColumns) {
        super(Integer.MAX_VALUE, jpTableColumns);
    }

    @Override
    public void load(long page) {
        super.load(1);
    }

    @Override
    public List<MODEL> getRowsForPage(JPPageable pageable) {
        return getRows();
    }

    @Override
    public Long size() {
        return Long.MAX_VALUE;
    }

    public abstract List<MODEL> getRows();
}
