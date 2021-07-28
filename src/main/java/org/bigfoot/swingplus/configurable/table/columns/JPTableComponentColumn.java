package org.bigfoot.swingplus.configurable.table.columns;

import lombok.Getter;
import org.bigfoot.swingplus.configurable.table.cell.JPComponentCellEditor;
import org.bigfoot.swingplus.configurable.table.sorter.JPSortable;

import java.awt.*;
import java.util.function.Function;

/**
 * @author Jonathan la Roi
 * @since 10/26/2020
 */
public class JPTableComponentColumn<MODEL, SORT> extends JPTableColumn<MODEL, MODEL, SORT> {

    private final Function<MODEL, Component> componentFunction;

    @Getter
    private final SORT sortProperty;

    public JPTableComponentColumn(String header, Class<MODEL> type, Function<MODEL, Component> componentFunction) {
        this(header, type, null, componentFunction, null);
    }

    public JPTableComponentColumn(String header, Class<MODEL> type, Function<MODEL, Component> componentFunction, SORT sort) {
        this(header, type, null, componentFunction, sort);
    }

    public JPTableComponentColumn(String header, Class<MODEL> type, Function<MODEL, String> tooltipGetter,
                                  Function<MODEL, Component> componentFunction, SORT sortProperty) {
        super(header, type, null, tooltipGetter, true);
        this.componentFunction = componentFunction;
        this.sortProperty = sortProperty;
        setTableCellRenderer(new JPComponentCellEditor<>());
        setTableCellEditor(new JPComponentCellEditor<>());
    }

    @Override
    public MODEL getValueForRow(MODEL model) {
        return model;
    }

    public Component getComponent(MODEL row) {
        return componentFunction.apply(row);
    }
}
