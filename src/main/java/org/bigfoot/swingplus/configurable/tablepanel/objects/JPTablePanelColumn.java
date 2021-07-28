package org.bigfoot.swingplus.configurable.tablepanel.objects;

import java.awt.Component;
import java.util.function.Function;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.swing.*;

@Deprecated
@AllArgsConstructor
@Getter
@Setter
public class JPTablePanelColumn<OBJECT, VALUE, SORT> {

    private String header;

    private SORT sortProperty;

    private Function<OBJECT, VALUE> getter;

    private String migLayoutProperties;

    private String migLayoutCellProperties;

    public VALUE get(OBJECT object) {
        if (getter == null) {
            return null;
        }
        return getter.apply(object);
    }

    public Component getComponent(OBJECT model) {
        return createComponent(model, get(model));
    }

    public Component createComponent(OBJECT model, VALUE value) {
        return new JLabel(value != null ? value.toString() : null);
    }
}
