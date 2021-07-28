package org.bigfoot.swingplus.configurable.components;

import org.bigfoot.swingplus.configurable.JPConfigurable;
import org.bigfoot.swingplus.eventlisteners.JPLambdaMouseClickListener;
import org.bigfoot.swingplus.eventlisteners.JPLambdaPopupMouseListener;
import org.bigfoot.swingplus.util.JPComponentUtil;

import javax.swing.*;
import java.awt.event.ActionListener;

/**
 * @author Jonathan la Roi
 * @since 14 march 2020
 */
public class JPMenuItem extends JMenuItem implements JPConfigurable {

    public JPMenuItem() {
        super(null, (Icon) null);
    }

    public JPMenuItem(Icon icon) {
        super(null, icon);
    }

    public JPMenuItem(String text) {
        super(text, (Icon) null);
    }

    public JPMenuItem(Action a) {
        super(a);
    }

    public JPMenuItem(String text, Icon icon) {
        super(text, icon);
    }

    public JPMenuItem(String text, int mnemonic) {
        super(text, mnemonic);
    }

    public JPMenuItem(String text, ActionListener a) {
        super(text);
        addActionListener(a);
    }

    public JPMenuItem(Icon icon, ActionListener a) {
        super(icon);
        addActionListener(a);
    }

    public JPMenuItem(String text, Icon icon, ActionListener a) {
        super(text, icon);
        addActionListener(a);
    }

    public void addMouseListener(JPLambdaPopupMouseListener adapter) {
        super.addMouseListener(adapter);
    }

    public void addMouseListener(JPLambdaMouseClickListener adapter) {
        super.addMouseListener(adapter);
    }

    public void addActionListener(ActionListener listener, KeyStroke... keyStrokes) {
        JPComponentUtil.addActionListener(this, listener, keyStrokes);
    }

    public void addActionListener(String name, ActionListener listener, KeyStroke... keyStrokes) {
        JPComponentUtil.addActionListener(this, name, listener, keyStrokes);
    }
}
