package org.bigfoot.swingplus.eventlisteners;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Interface voor combineren van een MouseListener met lambda's
 *
 * @author Jonathan la Roi
 * @since 4 juli 2019
 */
public interface JPLambdaMouseClickListener extends MouseListener {

    void handle(MouseEvent event);

    @Override
    default void mouseClicked(MouseEvent arg0) {
        if (!arg0.isPopupTrigger()) {
            handle(arg0);
        }
    }

    @Override
    default void mouseEntered(MouseEvent arg0) {
        //donothing
    }

    @Override
    default void mouseExited(MouseEvent arg0) {
        //donothing
    }

    @Override
    default void mousePressed(MouseEvent arg0) {
        //donothing
    }

    @Override
    default void mouseReleased(MouseEvent arg0) {
        //donothing
    }

}
