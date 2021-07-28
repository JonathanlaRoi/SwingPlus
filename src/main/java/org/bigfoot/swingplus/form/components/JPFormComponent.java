package org.bigfoot.swingplus.form.components;

import org.bigfoot.swingplus.configurable.JPConfigurable;

public interface JPFormComponent<T> extends JPConfigurable {
    void setId(String id);

    String getId();

    void setUpdatable(boolean updatable);

    boolean isUpdatable();

    /**
     * Sets the value of the component and then updates the component
     */
    void setComponentValue(T value);

    /**
     * Updates the value of the form component and returns it
     */
    T getComponentValue();

    default void setValidationLabel(String label) {

    }

    default String getValidationLabel() {
        return null;
    }

    ;

    default void markAsInvalid() {

    }

    default void markAsValid() {

    }

    default boolean isValid() {
        return true;
    }
}
