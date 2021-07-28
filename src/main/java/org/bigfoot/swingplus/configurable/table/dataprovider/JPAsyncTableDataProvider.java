package org.bigfoot.swingplus.configurable.table.dataprovider;

import lombok.extern.apachecommons.CommonsLog;
import org.bigfoot.swingplus.configurable.table.JPTable;
import org.bigfoot.swingplus.configurable.table.JPTableModel;
import org.bigfoot.swingplus.configurable.table.columns.JPTableColumn;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * @author Jonathan la Roi
 * @since 10/5/2020
 */
@CommonsLog
public abstract class JPAsyncTableDataProvider<MODEL> extends JPTableDataProvider<MODEL> {
    public JPAsyncTableDataProvider(Integer pageSize, List<JPTableColumn<MODEL, ?, ?>> jpTableColumns) {
        super(pageSize, jpTableColumns);
    }

    @Override
    protected void internalLoad(long page) {
        onStart(getTable());
        JPTableModel<MODEL> emptyModel = new JPTableModel<>(new ArrayList<>());
        emptyModel.setColumns(getColumns());
        getTable().setModel(emptyModel);

        new SwingWorker<List<MODEL>, Void>() {
            @Override
            protected List<MODEL> doInBackground() throws Exception {
                getPageable().setTotal(size());
                getPageable().setPage(Math.min(page, getPageable().getMaxPage()));
                return getRowsForPage(getPageable());
            }

            @Override
            protected void done() {
                JPTableModel<MODEL> model;
                try {
                    model = new JPTableModel<>(get());
                } catch (InterruptedException | ExecutionException e) {
                    model = new JPTableModel<>(new ArrayList<>());
                    log.error("", e);
                }
                model.setColumns(getColumns());
                getTable().setModel(model);
                setTablePageUI(getTable());
                JPAsyncTableDataProvider.this.onDone(getTable());
            }
        }.execute();
    }

    public void onStart(JPTable<MODEL> table){}

    public void onDone(JPTable<MODEL> table){}
}
