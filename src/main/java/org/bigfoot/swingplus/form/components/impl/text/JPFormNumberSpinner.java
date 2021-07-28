package org.bigfoot.swingplus.form.components.impl.text;

import lombok.Getter;
import lombok.Setter;
import org.bigfoot.swingplus.configurable.components.text.JPNumberSpinner;
import org.bigfoot.swingplus.form.components.JPFormComponent;

import javax.swing.*;

/**
 * @author Jonathan la Roi
 * @since 26/02/2021
 */
@Getter
@Setter
public class JPFormNumberSpinner extends JPNumberSpinner implements JPFormComponent<Integer> {

    private String id;

    private String label;

    private boolean updatable = true;

    public JPFormNumberSpinner(String id) {
        super();
        setId(id);
    }

    @Override
    public void setComponentValue(Integer value) {
        setValue(value);
    }

    @Override
    public Integer getComponentValue() {
        if (getValue() != null) {
            return Integer.valueOf(getValue().toString());
        } else {
            return null;
        }
    }
}
