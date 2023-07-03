package org.bigfoot.swingplus.configurable;

import lombok.extern.apachecommons.CommonsLog;
import org.bigfoot.swingplus.event.JPListener;
import org.bigfoot.swingplus.event.v2.JPSender;
import org.bigfoot.swingplus.eventlisteners.JPLambdaMouseClickListener;
import org.bigfoot.swingplus.eventlisteners.JPLambdaPopupMouseListener;

import javax.swing.*;
import java.awt.*;

/**
 * @author Jonathan la Roi
 * @since 1 augustus 2019
 */
@CommonsLog
public class JPWindow extends JWindow implements JPListener, JPContainer, JPResizable, JPSender {

    public JPWindow() {
        super();
        subscribe();
    }

    public JPWindow(GraphicsConfiguration arg0) {
        super(arg0);
        subscribe();
    }

    public JPWindow(Frame owner) {
        super(owner);
        subscribe();
    }

    public JPWindow(Window owner) {
        super(owner);
        subscribe();
    }

    public JPWindow(Window owner, GraphicsConfiguration gc) {
        super(owner, gc);
        subscribe();
    }

    @Override
    public void dispose() {
        unsubscribe();
        super.dispose();
    }

    public void addMouseListener(JPLambdaPopupMouseListener adapter) {
        super.addMouseListener(adapter);
    }

    public void addMouseListener(JPLambdaMouseClickListener adapter) {
        super.addMouseListener(adapter);
    }
}
