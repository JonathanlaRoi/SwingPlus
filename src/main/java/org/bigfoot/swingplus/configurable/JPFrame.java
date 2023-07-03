package org.bigfoot.swingplus.configurable;

import lombok.extern.apachecommons.CommonsLog;
import org.bigfoot.swingplus.event.JPListener;
import org.bigfoot.swingplus.event.v2.JPSender;

import javax.swing.*;
import java.awt.*;

/**
 * @author Jonathan la Roi
 * @since 1 augustus 2019
 */
@CommonsLog
public class JPFrame extends JFrame implements JPContainer, JPListener, JPResizable, JPSender {

    public JPFrame() {
        super();
        subscribe();
    }

    public JPFrame(GraphicsConfiguration arg0) {
        super(arg0);
        subscribe();
    }

    public JPFrame(String arg0) {
        super(arg0);
        subscribe();
    }

    public JPFrame(String arg0, GraphicsConfiguration arg1) {
        super(arg0, arg1);
        subscribe();
    }

    @Override
    public void dispose() {
        unsubscribe();
        super.dispose();
    }

    @Override
    public void onConfigure() {
        JPContainer.super.onConfigure();
        MenuBar bar = this.getMenuBar();
        if (bar instanceof JPConfigurable) {
            ((JPConfigurable) bar).onConfigure();
        }
    }
}
