package org.bigfoot.swingplus.configurable;

import lombok.extern.apachecommons.CommonsLog;
import org.bigfoot.swingplus.event.JPEventManager;
import org.bigfoot.swingplus.event.JPListener;

import javax.swing.*;
import java.awt.*;

/**
 * @author Jonathan la Roi
 * @since 1 augustus 2019
 */
@CommonsLog
public class JPFrame extends JFrame implements JPContainer, JPListener, JPResizable {

    public JPFrame() {
        super();
        register();
    }

    public JPFrame(GraphicsConfiguration arg0) {
        super(arg0);
        register();
    }

    public JPFrame(String arg0) {
        super(arg0);
        register();
    }

    public JPFrame(String arg0, GraphicsConfiguration arg1) {
        super(arg0, arg1);
        register();
    }

    @Override
    public void dispose() {
        JPEventManager.removeListener(this);
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
