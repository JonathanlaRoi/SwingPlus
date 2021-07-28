package org.bigfoot.swingplus.configurable.components.behaviour;

import lombok.AllArgsConstructor;

import java.awt.*;
import java.util.List;

@AllArgsConstructor
public class JPComponentConfig<COMPONENT extends Component> {

    private final List<JPComponentBehaviour<COMPONENT>> behaviours;

    public void apply(COMPONENT... component) {
        behaviours.forEach(b -> b.apply(component));
    }

    public COMPONENT apply(COMPONENT component) {
        behaviours.forEach(b -> b.apply(component));
        return component;
    }
}
