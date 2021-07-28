package org.bigfoot.swingplus.form.components.impl;

import lombok.Getter;
import lombok.Setter;
import org.bigfoot.swingplus.configurable.components.JPSlider;
import org.bigfoot.swingplus.form.components.JPFormComponent;

import javax.swing.*;

/**
 * @author Jonathan la Roi
 * @since 18/03/2021
 */
public class JPFormSlider extends JPSlider implements JPFormComponent<Integer> {

    @Getter
    @Setter
    private String id;

    @Getter
    @Setter
    private boolean updatable = true;

    public JPFormSlider(String id) {
        super(HORIZONTAL, 0, 100, 50);
        setId(id);
    }

    public JPFormSlider(String id, int orientation) {
        super(orientation, 0, 100, 50);
        setId(id);
    }

    public JPFormSlider(String id, int min, int max) {
        super(HORIZONTAL, min, max, (min + max) / 2);
        setId(id);
    }

    public JPFormSlider(String id, int min, int max, int value) {
        super(HORIZONTAL, min, max, value);
        setId(id);
    }

    public JPFormSlider(String id, int orientation, int min, int max, int value) {
        super(orientation, min, max, value);
        setId(id);
    }

    public JPFormSlider(String id, BoundedRangeModel brm) {
        super(brm);
        setId(id);
    }

    @Override
    public void setComponentValue(Integer value) {
        setValue(value);
    }

    @Override
    public Integer getComponentValue() {
        return getValue();
    }
}
