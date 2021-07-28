package org.bigfoot.swingplus.configurable.components.renderer;

import org.bigfoot.swingplus.configurable.components.JPLabel;

import javax.swing.*;
import java.awt.*;

/**
 * @author Jonathan la Roi
 * @since 05/11/2020
 */
public abstract class JPAbstractListCellRenderer<TYPE> extends JPLabel implements ListCellRenderer<TYPE> {

    private Component parent;

    public JPAbstractListCellRenderer() {
        this(null);
    }

    public JPAbstractListCellRenderer(Component parent) {
        super();
        setOpaque(true);
        this.parent = parent;
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends TYPE> list, TYPE value, int index, boolean isSelected, boolean cellHasFocus) {
        setText(getValue(value));
        setToolTipText(getToolTipValue(value));
        if (isSelected) {
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
        } else {
            setBackground(list.getBackground());
            setForeground(list.getForeground());
        }
        return this;
    }

    public abstract String getValue(TYPE value);

    public String getToolTipValue(TYPE value) {
        return null;
    }
}
