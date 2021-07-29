package org.bigfoot.swingplus.configurable.components;

import org.bigfoot.swingplus.configurable.JPContainer;

import javax.swing.*;

/**
 * @author Jonathan la Roi
 * @since 05/05/2021
 */
public class JPPopupMenu extends JPopupMenu implements JPContainer {

    public JPPopupMenu() {
        this(null);
    }

    public JPPopupMenu(String label) {
        super(label);
    }
}
