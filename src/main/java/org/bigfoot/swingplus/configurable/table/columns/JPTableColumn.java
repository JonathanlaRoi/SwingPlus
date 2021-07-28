package org.bigfoot.swingplus.configurable.table.columns;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.bigfoot.swingplus.configurable.table.sorter.JPSortable;

import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import java.util.function.Function;

@Data
public class JPTableColumn<MODEL, PROPERTY, SORT> implements JPSortable<SORT> {

    private final String header;

    private final Class<PROPERTY> type;

    private final Function<MODEL, PROPERTY> getter;

    private final Function<MODEL, String> tooltipGetter;

    @Getter
    private final SORT sortProperty;

    private final boolean editable;

    @Setter
    @Accessors(chain = true)
    private Integer maxWidth;

    @Setter
    @Accessors(chain = true)
    private Integer minWidth;

    @Setter
    @Accessors(chain = true)
    private TableCellRenderer tableCellRenderer;

    @Setter
    @Accessors(chain = true)
    private TableCellEditor tableCellEditor;

    public JPTableColumn(String header, Class<PROPERTY> type, Function<MODEL, PROPERTY> getter) {
        this(header, type, getter, null, null);
    }

    public JPTableColumn(String header, Class<PROPERTY> type, Function<MODEL, PROPERTY> getter, SORT sortProperty) {
        this(header, type, getter, null, sortProperty);
    }

    public JPTableColumn(String header, Class<PROPERTY> type, Function<MODEL, PROPERTY> getter, Function<MODEL, String> tooltipGetter) {
        this(header, type, getter, tooltipGetter, false);
    }

    public JPTableColumn(String header, Class<PROPERTY> type, Function<MODEL, PROPERTY> getter, Function<MODEL, String> tooltipGetter, SORT sortProperty) {
        this(header, type, getter, tooltipGetter, false, sortProperty);
    }

    public JPTableColumn(String header, Class<PROPERTY> type, Function<MODEL, PROPERTY> getter, Function<MODEL, String> tooltipGetter,
                         boolean editable) {
        this(header, type, getter, tooltipGetter, editable, null);
    }

    public JPTableColumn(String header, Class<PROPERTY> type, Function<MODEL, PROPERTY> getter, Function<MODEL, String> tooltipGetter,
                         boolean editable, SORT sortProperty) {
        this.header = header;
        this.type = type;
        this.getter = getter;
        this.tooltipGetter = tooltipGetter;
        this.editable = editable;
        this.sortProperty = sortProperty;
    }

    public static <MODEL, PROPERTY, SORT> JPTableColumn<MODEL, PROPERTY, SORT> of(String header, Class<PROPERTY> type, Function<MODEL, PROPERTY> getter) {
        return new JPTableColumn<>(header, type, getter);
    }

    public static <MODEL, PROPERTY, SORT> JPTableColumn<MODEL, PROPERTY, SORT> of(String header, Class<PROPERTY> type, Function<MODEL, PROPERTY> getter,
                                                                      Function<MODEL, String> tooltipGetter) {
        return new JPTableColumn<>(header, type, getter, tooltipGetter);
    }

    public JPTableColumn<MODEL, PROPERTY, SORT> setWidth(Integer width) {
        this.maxWidth = width;
        this.minWidth = width;
        return this;
    }

    public PROPERTY getValueForRow(MODEL model) {
        return getGetter() != null ? getGetter().apply(model) : null;
    }

    public String getTooltipValueForRow(MODEL model) {
        return getTooltipGetter() != null ? getTooltipGetter().apply(model) : null;
    }

}
