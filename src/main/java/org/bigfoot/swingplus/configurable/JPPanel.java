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
public class JPPanel extends JPanel implements JPContainer, JPListener, JPSender {

    public JPPanel(LayoutManager arg0, boolean arg1) {
        super(arg0, arg1);
        subscribe();
    }

    public JPPanel(LayoutManager arg0) {
        super(arg0);
        subscribe();
    }

    public JPPanel(boolean arg0) {
        super(arg0);
        subscribe();
    }

    public JPPanel() {
        super();
        subscribe();
    }

    public void addMouseListener(JPLambdaPopupMouseListener adapter) {
        super.addMouseListener(adapter);
    }

    public void addMouseListener(JPLambdaMouseClickListener adapter) {
        super.addMouseListener(adapter);
    }
}
