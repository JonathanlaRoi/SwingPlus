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
public class JPToolBar extends JToolBar implements JPContainer, JPListener, JPSender {

    public JPToolBar() {
        super();
        setFloatable(false);
        subscribe();
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        for (Component component : getComponents()) {
            component.setEnabled(enabled);
        }
    }

    public void addMouseListener(JPLambdaPopupMouseListener adapter) {
        super.addMouseListener(adapter);
    }

    public void addMouseListener(JPLambdaMouseClickListener adapter) {
        super.addMouseListener(adapter);
    }
}
