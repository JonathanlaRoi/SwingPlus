package org.bigfoot.swingplus.configurable;

import lombok.extern.apachecommons.CommonsLog;
import org.bigfoot.swingplus.event.JPEventManager;
import org.bigfoot.swingplus.event.JPListener;

import javax.swing.*;
import java.awt.*;

/**
 * @author Jonathan la Roi
 * @since 18/01/2021
 */
@CommonsLog
public class JPScrollPane extends JScrollPane implements JPContainer, JPListener {

    public JPScrollPane(Component view, int vsbPolicy, int hsbPolicy) {
        super(view, vsbPolicy, hsbPolicy);
        register();
    }

    public JPScrollPane(Component view) {
        super(view);
        register();
    }

    public JPScrollPane(int vsbPolicy, int hsbPolicy) {
        super(vsbPolicy, hsbPolicy);
        register();
    }

    public JPScrollPane() {
        super();
        register();
    }
}
