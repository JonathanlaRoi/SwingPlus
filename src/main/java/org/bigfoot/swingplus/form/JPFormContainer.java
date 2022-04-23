package org.bigfoot.swingplus.form;

import java.awt.Component;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JScrollPane;

import org.bigfoot.swingplus.configurable.JPContainer;
import org.bigfoot.swingplus.form.components.JPEditable;
import org.bigfoot.swingplus.form.components.JPFormComponent;

public interface JPFormContainer extends JPContainer, JPEditable {

    default void setEditable(boolean editable) {
        JPFormHelper.setEditable(editable, this);
    }

    default boolean isEditable() {
        return isEnabled();
    }

    boolean isEnabled();

    Component[] getComponents();

    default List<JPFormComponent<?>> getFormComponents() {
        List<JPFormComponent<?>> formComponents = new ArrayList<>();
        Component[] comps = getComponents();
        for (Component comp : comps) {
            //If component is jscrollpane, then we get the viewport
            if (comp instanceof JScrollPane) {
                comp = ((JScrollPane) comp).getViewport().getView();
            }
            if (comp instanceof JPFormComponent) {
                formComponents.add((JPFormComponent<?>) comp);
            }
            if (comp instanceof JPFormContainer) {
                formComponents.addAll(((JPFormContainer) comp).getFormComponents());
            }
        }
        return formComponents;
    }
}
