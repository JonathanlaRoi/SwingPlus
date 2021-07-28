package org.bigfoot.swingplus.eventlisteners;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Functional interface for {@link MouseListener}
 *
 * @author Jonathan la Roi
 * @since 5 septmeber 2019
 */
public interface JPLambdaPopupMouseListener extends MouseListener {
    default void mouseClicked(MouseEvent e) {
    }

    default void mousePressed(MouseEvent e) {
        if (e.isPopupTrigger()) {
            onEvent(e);
        }
    }

    default void mouseReleased(MouseEvent e) {
        if (e.isPopupTrigger()) {
            onEvent(e);
        }
    }

    default void mouseEntered(MouseEvent e) {
    }

    default void mouseExited(MouseEvent e) {
    }

    void onEvent(MouseEvent e);
}
