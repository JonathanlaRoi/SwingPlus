package org.bigfoot.swingplus.configurable.components;

import org.bigfoot.swingplus.eventlisteners.JPLambdaMouseClickListener;

import java.awt.*;

/**
 * @author Jonathan la Roi
 * @since 06/11/2020
 */
public class JPClickableLabel extends JPLabel {

    public JPClickableLabel(String string, JPLambdaMouseClickListener lambdaMouseClickListener) {
        super(string);
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        addMouseListener(lambdaMouseClickListener);
    }
}
