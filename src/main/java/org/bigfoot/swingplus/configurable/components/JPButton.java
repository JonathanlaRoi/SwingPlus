package org.bigfoot.swingplus.configurable.components;

import org.bigfoot.swingplus.configurable.JPConfigurable;
import org.bigfoot.swingplus.eventlisteners.JPLambdaMouseClickListener;
import org.bigfoot.swingplus.eventlisteners.JPLambdaPopupMouseListener;
import org.bigfoot.swingplus.util.JPComponentUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * @author Jonathan la Roi
 * @since 1 juni 2019
 */
public class JPButton extends JButton implements JPConfigurable {

    public JPButton(String text, Icon icon) {
        super(text, icon);
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    public JPButton(Icon icon) {
        super(icon);
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    public JPButton(String text) {
        super(text);
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    public JPButton(ActionListener al) {
        super();
        addActionListener(al);
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    public JPButton() {
        super();
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    public JPButton(String text, Icon icon, ActionListener al) {
        this(text, icon);
        addActionListener(al);
    }

    public JPButton(String text, ActionListener al) {
        this(text);
        addActionListener(al);
    }

    public JPButton(Icon icon, ActionListener al) {
        this(icon);
        addActionListener(al);
    }

    public void addActionListener(ActionListener listener, KeyStroke... keyStrokes) {
        JPComponentUtils.addActionListener(this, listener, keyStrokes);
    }

    public void addActionListener(String name, ActionListener listener, KeyStroke... keyStrokes) {
        JPComponentUtils.addActionListener(this, name, listener, keyStrokes);
    }

    public void addMouseListener(JPLambdaPopupMouseListener adapter) {
        super.addMouseListener(adapter);
    }

    public void addMouseListener(JPLambdaMouseClickListener adapter) {
        super.addMouseListener(adapter);
    }
}
