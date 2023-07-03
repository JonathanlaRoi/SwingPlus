package org.bigfoot.swingplus.configurable;

import lombok.extern.apachecommons.CommonsLog;
import org.bigfoot.swingplus.event.JPListener;
import org.bigfoot.swingplus.event.v2.JPSender;

import javax.swing.*;
import java.awt.*;

/**
 * @author Jonathan la Roi
 * @since 8/24/2020
 */
@CommonsLog
public class JPSplitPane extends JSplitPane implements JPContainer, JPListener, JPSender {

    public JPSplitPane() {
        super();
        subscribe();
    }

    public JPSplitPane(int newOrientation) {
        super(newOrientation);
        subscribe();
    }

    public JPSplitPane(int newOrientation, boolean newContinuousLayout) {
        super(newOrientation, newContinuousLayout);
        subscribe();
    }


    public JPSplitPane(int newOrientation, Component newLeftComponent, Component newRightComponent) {
        super(newOrientation, newLeftComponent, newRightComponent);
        subscribe();
    }


    public JPSplitPane(int newOrientation, boolean newContinuousLayout, Component newLeftComponent, Component newRightComponent) {
        super(newOrientation, newContinuousLayout, newLeftComponent, newRightComponent);
        subscribe();
    }
}
