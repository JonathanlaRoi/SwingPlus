package org.bigfoot.swingplus.configurable.components;

import org.bigfoot.swingplus.configurable.JPConfigurable;

import javax.swing.*;
import java.awt.*;
import java.util.Optional;

/**
 * @author Jonathan la Roi
 * @since 05/05/2021
 */
public class JPPopupMenu extends JPopupMenu implements JPConfigurable {

    public JPPopupMenu() {
        this(null);
    }

    public JPPopupMenu(String label) {
        super(label);
    }

    @Override
    public void onConfigure() {
        Component[] components = Optional.of(this)
                .map(Container::getComponents)
                .orElse(new Component[0]);
        for (Component comp : components) {
            if (comp instanceof JPConfigurable) {
                ((JPConfigurable) comp).onConfigure();
            }
        }
    }
}
