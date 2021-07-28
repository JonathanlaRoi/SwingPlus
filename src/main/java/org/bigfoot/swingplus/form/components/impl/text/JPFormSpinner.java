package org.bigfoot.swingplus.form.components.impl.text;

import lombok.Getter;
import lombok.Setter;
import org.bigfoot.swingplus.configurable.components.text.JPSpinner;
import org.bigfoot.swingplus.form.components.JPFormComponent;

import javax.swing.*;

/**
 * @author Jonathan la Roi
 * @since 26/02/2021
 */
@Getter
@Setter
public class JPFormSpinner extends JPSpinner implements JPFormComponent<String> {

    private String id;

    private String label;

    private boolean updatable = true;

    public JPFormSpinner(String id){
        super();
        setId(id);
    }

    public JPFormSpinner(String id, SpinnerModel spinnerModel){
        super(spinnerModel);
        setId(id);
    }

    @Override
    public void setComponentValue(String value) {
        setValue(value);
    }

    @Override
    public String getComponentValue() {
        return getValue() != null ? getValue().toString() : null;
    }
}
