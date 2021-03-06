package org.bigfoot.swingplus.configurable.components;

import org.bigfoot.swingplus.configurable.JPConfigurable;
import org.bigfoot.swingplus.eventlisteners.JPLambdaMouseClickListener;
import org.bigfoot.swingplus.eventlisteners.JPLambdaPopupMouseListener;
import org.bigfoot.swingplus.util.JPComponentUtils;

import javax.swing.*;
import java.awt.event.ActionListener;

/**
 * @author Jonathan la Roi
 * @since 1 juni 2019
 */
public class JPRadioButton extends JRadioButton implements JPConfigurable {

    public void addMouseListener(JPLambdaPopupMouseListener adapter) {
        super.addMouseListener(adapter);
    }

    public void addMouseListener(JPLambdaMouseClickListener adapter) {
        super.addMouseListener(adapter);
    }

    public void addActionListener(ActionListener listener, KeyStroke... keyStrokes) {
        JPComponentUtils.addActionListener(this, listener, keyStrokes);
    }

    public void addActionListener(String name, ActionListener listener, KeyStroke... keyStrokes) {
        JPComponentUtils.addActionListener(this, name, listener, keyStrokes);
    }
}