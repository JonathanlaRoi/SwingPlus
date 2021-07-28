package org.bigfoot.swingplus.form.components.impl;

import lombok.Getter;
import lombok.Setter;
import org.bigfoot.swingplus.configurable.components.JPTextLabel;
import org.bigfoot.swingplus.form.components.JPFormComponent;
import org.bigfoot.swingplus.form.components.JPReadOnly;

/**
 * @author Jonathan la Roi
 * @since 24/02/2021
 */
public class JPFormTextLabel extends JPTextLabel implements JPFormComponent<Object>, JPReadOnly {

    @Getter
    @Setter
    private String id;

    @Getter
    @Setter
    private boolean updatable = true;

    public JPFormTextLabel(String id) {
        super();
        setId(id);
    }

    @Override
    public void setComponentValue(Object value) {
        if (value != null) {
            if (value instanceof String) {
                setText((String) value);
            } else {
                setText(value.toString());
            }
        } else {
            setText(null);
        }
    }

    @Override
    public Object getComponentValue() {
        return getText();
    }
}
