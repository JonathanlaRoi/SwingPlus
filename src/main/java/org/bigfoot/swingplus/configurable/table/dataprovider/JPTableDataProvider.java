package org.bigfoot.swingplus.configurable.table.dataprovider;

import lombok.AccessLevel;
import lombok.Getter;
import org.bigfoot.swingplus.configurable.table.JPTable;
import org.bigfoot.swingplus.configurable.table.JPTableModel;
import org.bigfoot.swingplus.configurable.table.columns.JPTableColumn;
import org.bigfoot.swingplus.configurable.tablepanel.components.JPPageable;

import java.util.List;

/**
 * @author Jonathan la Roi
 * @since 8/31/2020
 */
public abstract class JPTableDataProvider<MODEL> {

    @Getter
    private final List<JPTableColumn<MODEL, ?, ?>> columns;

    @Getter()
    private final JPPageable pageable;

    @Getter(AccessLevel.PROTECTED)
    private JPTable<MODEL> table;

    public JPTableDataProvider(Integer pageSize, List<JPTableColumn<MODEL, ?, ?>> columns) {
        this.pageable = new JPPageable(pageSize);
        this.columns = columns;
    }

    public void setTable(JPTable<MODEL> table) {
        this.table = table;
    }

    public void load() {
        load(pageable.getPage());
    }

    public void load(long page) {
        internalLoad(page);
    }

    protected void internalLoad(long page) {
        long size = size();
        pageable.setTotal(size);
        pageable.setPage(Math.min(page, getPageable().getMaxPage()));
        List<MODEL> result = getRowsForPage(pageable);
        JPTableModel<MODEL> model = new JPTableModel<>(result);
        model.setColumns(columns);
        table.setModel(model);
    }

    public abstract List<MODEL> getRowsForPage(JPPageable pageable);

    public abstract Long size();
}
