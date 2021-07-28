package org.bigfoot.swingplus.configurable.components.behaviour;

import java.awt.*;

public interface JPComponentBehaviour<COMPONENT extends Component> {

    default void apply(COMPONENT... components) {
        for (COMPONENT component : components) {
            apply(component);
        }
    }

    void apply(COMPONENT component);
}
