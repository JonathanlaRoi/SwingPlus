package org.bigfoot.swingplus.eventlisteners;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * @author Jonathan la Roi
 * @since 12 augustus 2019
 */
public abstract class JPPopupMouseAdapter extends MouseAdapter {
    public void mousePressed(MouseEvent event) {
        if (event.isPopupTrigger()) {
            onEvent(event);
        }
    }

    public void mouseReleased(MouseEvent event) {
        if (event.isPopupTrigger()) {
            onEvent(event);
        }
    }

    public abstract void onEvent(MouseEvent event);
}
