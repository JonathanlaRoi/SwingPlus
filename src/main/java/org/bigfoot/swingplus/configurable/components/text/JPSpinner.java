package org.bigfoot.swingplus.configurable.components.text;

import org.bigfoot.swingplus.configurable.JPConfigurable;

import javax.swing.*;

/**
 * @author Jonathan la Roi
 * @since 26/02/2021
 */
public class JPSpinner extends JSpinner implements JPConfigurable {

    public JPSpinner(SpinnerModel model) {
        super(model);
    }

    public JPSpinner() {
        super();
    }

}
