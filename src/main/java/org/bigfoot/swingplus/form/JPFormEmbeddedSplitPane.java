package org.bigfoot.swingplus.form;

import org.bigfoot.swingplus.configurable.JPSplitPane;

import java.awt.*;

/**
 * @author Jonathan la Roi
 * @since 13/05/2022
 */
public class JPFormEmbeddedSplitPane extends JPSplitPane implements JPFormContainer {

    public JPFormEmbeddedSplitPane() {
        super();
    }

    public JPFormEmbeddedSplitPane(int newOrientation) {
        super(newOrientation);
    }

    public JPFormEmbeddedSplitPane(int newOrientation, boolean newContinuousLayout) {
        super(newOrientation, newContinuousLayout);
    }


    public JPFormEmbeddedSplitPane(int newOrientation, Component newLeftComponent, Component newRightComponent) {
        super(newOrientation, newLeftComponent, newRightComponent);
    }


    public JPFormEmbeddedSplitPane(int newOrientation, boolean newContinuousLayout, Component newLeftComponent, Component newRightComponent) {
        super(newOrientation, newContinuousLayout, newLeftComponent, newRightComponent);
    }
}
