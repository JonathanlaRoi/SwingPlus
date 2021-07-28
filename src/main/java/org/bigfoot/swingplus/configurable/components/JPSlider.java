package org.bigfoot.swingplus.configurable.components;

import org.bigfoot.swingplus.configurable.JPConfigurable;

import javax.swing.*;

/**
 * @author Jonathan la Roi
 * @since 18/03/2021
 */
public class JPSlider extends JSlider implements JPConfigurable {

    public JPSlider() {
        super(HORIZONTAL, 0, 100, 50);
    }

    public JPSlider(int orientation) {
        super(orientation, 0, 100, 50);
    }

    public JPSlider(int min, int max) {
        super(HORIZONTAL, min, max, (min + max) / 2);
    }

    public JPSlider(int min, int max, int value) {
        super(HORIZONTAL, min, max, value);
    }

    public JPSlider(int orientation, int min, int max, int value) {
        super(orientation, min, max, value);
    }

    public JPSlider(BoundedRangeModel brm) {
        super(brm);
    }
}
