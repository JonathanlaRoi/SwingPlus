package org.bigfoot.swingplus.form.components;

import org.bigfoot.swingplus.configurable.JPConfigurable;

public interface JPFormComponent<T> extends JPConfigurable {

    /**
     * The ID of the component, this relates to the property in the model that it has to update
     *
     * @param id {@link String}
     */
    void setId(String id);

    /**
     * Returns the ID of the component
     *
     * @return {@link String}
     */
    String getId();

    /**
     * Sets if the component can be updated by a {@link org.bigfoot.swingplus.form.JPFormPanel<T>}
     *
     * @param updatable boolean
     */
    void setUpdatable(boolean updatable);

    /**
     * Returns if the component is updatable by a {@link org.bigfoot.swingplus.form.JPFormPanel<T>}
     *
     * @return
     */
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


    default void markAsInvalid() {

    }

    default void markAsValid() {

    }

    default boolean isValid() {
        return true;
    }
}
