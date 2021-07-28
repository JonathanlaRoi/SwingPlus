package org.bigfoot.swingplus.form.components.impl.select;

import lombok.Getter;
import lombok.Setter;
import org.bigfoot.swingplus.configurable.components.select.JPMultiSelect;
import org.bigfoot.swingplus.form.components.JPFormComponent;

import java.util.List;

/**
 * @author Jonathan la Roi
 * @since 8/24/2020
 */
public class JPFormMultiSelect<MODEL> extends JPMultiSelect<MODEL> implements JPFormComponent<List<MODEL>> {

    @Getter
    @Setter
    private String id;

    @Getter
    @Setter
    private boolean updatable = true;

    public JPFormMultiSelect(String id, String label) {
        super(label);
        setId(id);
    }

    public JPFormMultiSelect(String id, String label, List<MODEL> items) {
        super(label, items);
        setId(id);
    }

    public JPFormMultiSelect(String id, String label, MODEL... items) {
        super(label, items);
        setId(id);
    }

    @Override
    public void setComponentValue(List<MODEL> value) {
        if (value != null) {
            super.setSelectedItems(value);
        } else {
            super.clearSelection();
        }
    }

    @Override
    public List<MODEL> getComponentValue() {
        return super.getSelectedItems();
    }
}
