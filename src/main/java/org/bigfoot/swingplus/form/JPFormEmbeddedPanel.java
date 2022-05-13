package org.bigfoot.swingplus.form;

import org.bigfoot.swingplus.configurable.JPPanel;

import java.awt.*;

/**
 * @author Jonathan la Roi
 * @since 10 september 2019
 */
public class JPFormEmbeddedPanel extends JPPanel implements JPFormContainer {

    public JPFormEmbeddedPanel(LayoutManager arg0, boolean arg1) {
        super(arg0, arg1);
    }

    public JPFormEmbeddedPanel(LayoutManager arg0) {
        super(arg0);
    }

    public JPFormEmbeddedPanel(boolean arg0) {
        super(arg0);
    }

    public JPFormEmbeddedPanel() {
        super();
    }
}
