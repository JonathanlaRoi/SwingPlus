package org.bigfoot.swingplus.configurable.components;

import org.bigfoot.swingplus.configurable.JPConfigurable;
import org.bigfoot.swingplus.eventlisteners.JPLambdaMouseClickListener;
import org.bigfoot.swingplus.eventlisteners.JPLambdaPopupMouseListener;
import org.bigfoot.swingplus.util.JPComponentUtil;

import javax.swing.*;
import java.awt.event.ActionListener;

/**
 * @author Jonathan la Roi
 * @since 1 juni 2019
 */
public class JPCheckBox extends JCheckBox implements JPConfigurable {

    public JPCheckBox() {
        super();
    }

    public JPCheckBox(String label) {
        super(label);
    }

    public JPCheckBox(String label, ActionListener al) {
        this(label);
        addActionListener(al);
    }

    @Override
    public void onConfigure() {

    }

    public void addActionListener(ActionListener listener, KeyStroke... keyStrokes) {
        JPComponentUtil.addActionListener(this, listener, keyStrokes);
    }

    public void addActionListener(String name, ActionListener listener, KeyStroke... keyStrokes) {
        JPComponentUtil.addActionListener(this, name, listener, keyStrokes);
    }

    public void addMouseListener(JPLambdaPopupMouseListener adapter) {
        super.addMouseListener(adapter);
    }

    public void addMouseListener(JPLambdaMouseClickListener adapter) {
        super.addMouseListener(adapter);
    }
}
