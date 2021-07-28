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
        JPEventManager.addListener(this);
    }

    public JPScrollPane(Component view) {
        this(view, VERTICAL_SCROLLBAR_AS_NEEDED, HORIZONTAL_SCROLLBAR_AS_NEEDED);
        JPEventManager.addListener(this);
    }

    public JPScrollPane(int vsbPolicy, int hsbPolicy) {
        this(null, vsbPolicy, hsbPolicy);
        JPEventManager.addListener(this);
    }

    public JPScrollPane() {
        this(null, VERTICAL_SCROLLBAR_AS_NEEDED, HORIZONTAL_SCROLLBAR_AS_NEEDED);
        JPEventManager.addListener(this);
    }
}
