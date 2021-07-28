package org.bigfoot.swingplus.configurable.components;

import org.bigfoot.swingplus.configurable.JPConfigurable;
import org.bigfoot.swingplus.configurable.components.text.JPTextField;

/**
 * @author Jonathan la Roi
 * @since 24/02/2021
 */
public class JPTextLabel extends JPTextField implements JPConfigurable {

    public JPTextLabel() {
        this(null);
    }

    public JPTextLabel(String text) {
        super();
        setText(text);
        setEditable(false);
        setBackground(null);
        setBorder(null);
    }
}
