package org.bigfoot.swingplus.configurable;

import javax.swing.*;
import java.awt.*;

/**
 * @author Jonathan la Roi
 * @since 1 augustus 2019
 */
public interface JPContainer extends JPConfigurable, JPTranslator {

    Component[] getComponents();

    default void onConfigure() {
        Component[] components;
        if (this instanceof JFrame) {
            components = ((JFrame) this).getContentPane().getComponents();
        } else if (this instanceof JDialog) {
            components = ((JDialog) this).getContentPane().getComponents();
        } else if (this instanceof JWindow) {
            components = ((JWindow) this).getContentPane().getComponents();
        } else {
            components = this.getComponents();
        }
        for (Component comp : components) {
            if (comp instanceof JScrollPane) {
                comp = ((JScrollPane) comp).getViewport().getView();
            }
            if (comp instanceof JPConfigurable) {
                ((JPConfigurable) comp).onConfigure();
            }
        }
    }
}
