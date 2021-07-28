package org.bigfoot.swingplus.configurable.components.step;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.awt.*;
import java.util.Objects;

/**
 * @author Jonathan la Roi
 * @since 06/11/2020
 */
@Getter
@AllArgsConstructor
public class JPStep<COMPONENT extends Component> {

    private String naam;

    private COMPONENT component;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o instanceof String) {
            return Objects.equals(naam, o);
        } else if (o == null || getClass() != o.getClass()) {
            return false;
        }
        JPStep<?> jpStep = (JPStep<?>) o;
        return Objects.equals(naam, jpStep.naam);
    }

    @Override
    public int hashCode() {
        return Objects.hash(naam);
    }
}
