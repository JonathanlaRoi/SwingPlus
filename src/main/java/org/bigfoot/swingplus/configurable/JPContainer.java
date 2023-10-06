package org.bigfoot.swingplus.configurable;

import java.awt.Component;

import javax.swing.JScrollPane;
import javax.swing.RootPaneContainer;

/**
 * @author Jonathan la Roi
 * @since 1 augustus 2019
 */
public interface JPContainer extends JPConfigurable, JPTranslator {

    Component[] getComponents();

    default void onConfigure() {
        Component[] components;
        if (this instanceof RootPaneContainer)
        {
            components = ((RootPaneContainer) this).getContentPane().getComponents();
        }
        else
        {
            components = this.getComponents();
        }
        for (Component comp : components)
        {
            if (comp instanceof JScrollPane)
            {
                comp = ((JScrollPane) comp).getViewport().getView();
            }
            if (comp instanceof JPConfigurable)
            {
                ((JPConfigurable) comp).onConfigure();
            }
        }
    }
}
