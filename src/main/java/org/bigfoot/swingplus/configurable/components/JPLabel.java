package org.bigfoot.swingplus.configurable.components;

import org.bigfoot.swingplus.configurable.JPConfigurable;
import org.bigfoot.swingplus.eventlisteners.JPLambdaMouseClickListener;
import org.bigfoot.swingplus.eventlisteners.JPLambdaPopupMouseListener;

import javax.swing.*;
import java.awt.*;

/**
 * @author Jonathan la Roi
 * @since 1 juni 2019
 */
public class JPLabel extends JLabel implements JPConfigurable {

    public JPLabel() {
        super();
    }

    public JPLabel(String text) {
        super(text);
    }

    public JPLabel(Icon icon) {
        super(icon);
    }

    public JPLabel(String text, int alignment) {
        super(text, alignment);
    }

    public JPLabel(String text, Icon icon) {
        super(text);
        setIcon(icon);
    }

    public JPLabel(String text, Icon icon, int alignment) {
        super(text, alignment);
        setIcon(icon);
    }

    public JPLabel(String text, Icon icon, Font font) {
        super(text);
        setIcon(icon);
        setFont(font);
    }

    public JPLabel(String text, Icon icon, Font font, int alignment) {
        super(text, alignment);
        setIcon(icon);
        setFont(font);
    }

    @Override
    public void onConfigure() {

    }

    public void addMouseListener(JPLambdaPopupMouseListener adapter) {
        super.addMouseListener(adapter);
    }

    public void addMouseListener(JPLambdaMouseClickListener adapter) {
        super.addMouseListener(adapter);
    }
}