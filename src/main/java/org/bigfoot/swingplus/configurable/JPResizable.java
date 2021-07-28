package org.bigfoot.swingplus.configurable;

import java.awt.*;

/**
 * @author Jonathan la Roi
 * @since 10/7/2020
 */
public interface JPResizable {

    int getHeight();

    int getWidth();

    void setSize(Dimension dimension);

    void pack();

    Container getContentPane();

    default void setSizeBasedOnComponent(Component component) {
        pack();
        setSize(component.getSize());
    }

    default void setWidthBasedOnComponent(int height, Component component) {
        pack();
        setSize(new Dimension(component.getWidth(), height));
    }

    default void setWidthBasedOnComponent(Component component) {
        setWidthBasedOnComponent(getHeight(), component);
    }

    default void setHeightBasedOnComponent(int width, Component component) {
        pack();
        setSize(new Dimension(width, component.getHeight()));
    }

    default void setHeightBasedOnComponent(Component component) {
        setHeightBasedOnComponent(getWidth(), component);
    }

    default void setWidthBasedOnContentPane() {
        setWidthBasedOnComponent(getContentPane());
    }

    default void setHeightBasedOnContentPane() {
        setHeightBasedOnComponent(getContentPane());
    }

    default void setWidthBasedOnContentPane(int height) {
        setWidthBasedOnComponent(height, getContentPane());
    }

    default void setHeightBasedOnContentPane(int width) {
        setHeightBasedOnComponent(width, getContentPane());
    }

    default void setSizeBasedOnContentPane() {
        setSizeBasedOnComponent(getContentPane());
    }

    default void setWidthBasedOnPack(int height) {
        pack();
        setSize(new Dimension(getWidth(), height));
    }

    default void setHeightBasedOnPack(int width) {
        pack();
        setSize(new Dimension(width, getHeight()));
    }

}
