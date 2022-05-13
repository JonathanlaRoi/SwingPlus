package org.bigfoot.swingplus.form;

import lombok.Getter;
import lombok.extern.apachecommons.CommonsLog;
import org.bigfoot.swingplus.configurable.JPModeledPanel;
import org.bigfoot.swingplus.configurable.components.calendar.JPDatepickerTextField;
import org.bigfoot.swingplus.form.components.JPEditable;
import org.bigfoot.swingplus.form.components.JPFormComponent;
import org.bigfoot.swingplus.form.components.JPReadOnly;
import org.bigfoot.swingplus.form.exception.JPFormException;
import org.bigfoot.swingplus.form.validation.JPFormValidator;

import javax.swing.text.JTextComponent;
import java.awt.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.bigfoot.swingplus.form.JPFormHelper.getField;
import static org.bigfoot.swingplus.form.JPFormHelper.getGetterMethod;
import static org.bigfoot.swingplus.form.JPFormHelper.getNewInstanceOf;
import static org.bigfoot.swingplus.form.JPFormHelper.getNewInstanceOfProperty;
import static org.bigfoot.swingplus.form.JPFormHelper.getSetterMethod;

/**
 * Form dat gebruik maakt van een model en dat model automatisch kan updaten
 * Dit gebeurt alleen voor {@link JPFormComponent} componenten
 * Updaten van het form gaat via de method {@link JPFormPanel<T>#updateForm()} of {@link JPFormPanel<T>#setComponentValue(T value)}
 * Updaten van het model gaat via de method {@link JPFormPanel<T>#updateModel()} of {@link JPFormPanel<T>#getComponentValue()}
 * <p>
 * Indien het panel wordt gebruikt als sub panel, mag het object wat die mee krijgt niet null zijn.
 * De properties kunnen anders niet geset worden
 *
 * @param <T>
 * @author Jonathan la Roi
 */
@CommonsLog
public class JPFormPanel<T> extends JPModeledPanel<T> implements JPFormComponent<T>, JPFormContainer {

    private String id;

    private String label;

    private boolean updatable = true;

    @Getter
    private boolean editable = true;

    private Class<T> clazz;

    private final List<JPFormValidator<?>> validators = new ArrayList<>();

    public JPFormPanel(String id, LayoutManager arg0, boolean arg1, T model) {
        super(arg0, arg1, model);
        setId(id);
        setClazz();
    }

    public JPFormPanel(String id, LayoutManager arg0, T model) {
        super(arg0, model);
        setId(id);
        setClazz();
    }

    public JPFormPanel(String id, boolean arg0, T model) {
        super(arg0, model);
        setId(id);
        setClazz();
    }

    public JPFormPanel(String id, T model) {
        super(model);
        setId(id);
        setClazz();
    }

    public JPFormPanel(String id, Class<T> clazz) {
        super();
        setId(id);
        this.clazz = clazz;
        setModelBasedOnType();
    }

    public JPFormPanel(Class<T> clazz) {
        super();
        this.clazz = clazz;
        setModelBasedOnType();
    }

    public JPFormPanel(T model) {
        super(model);
        setClazz();
    }

    public JPFormPanel() {
        super(null);
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setValidationLabel(String label) {
        this.label = label;
    }

    @Override
    public String getValidationLabel() {
        return label;
    }

    @Override
    public void setUpdatable(boolean updatable) {
        this.updatable = updatable;
    }

    @Override
    public boolean isUpdatable() {
        return updatable;
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);

        for (JPFormComponent<?> comp : getFormComponents()) {
            if (comp instanceof Component) {
                ((Component) comp).setEnabled(enabled);
            }
        }
    }

    /**
     * Form gets al form components en sets the editablity of each component if they are a {@link JTextComponent}
     * If the component is not a {@link JTextComponent} it is enabled/disabled instead.
     *
     * @param editable boolean
     */
    public void setEditable(boolean editable) {
        super.setEnabled(editable);
        this.editable = editable;

        for (JPFormComponent<?> comp : getFormComponents()) {
            if (comp instanceof Component) {
                if (comp instanceof JTextComponent && !(comp instanceof JPDatepickerTextField)) {
                    ((JTextComponent) comp).setEditable(editable);
                } else if (comp instanceof JPEditable) {
                    ((JPEditable) comp).setEditable(editable);
                } else if (comp instanceof JPFormPanel<?>) {
                    ((JPFormPanel<?>) comp).setEditable(editable);
                } else if (!(comp instanceof JPReadOnly)) {
                    ((Component) comp).setEnabled(editable);
                }
            }
        }
    }

    @Override
    public boolean isEditable() {
        return editable;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void setComponentValue(T value) {
        if (value == null) {
            setModelBasedOnType();
        }
        setModel(value);
        updateForm();
    }

    @Override
    public T getComponentValue() {
        updateModel();
        return getModel();
    }

    public void addValidator(JPFormValidator validator) {
        this.validators.add(validator);
    }

    public void removeValidator(JPFormValidator validator) {
        this.validators.remove(validator);
    }

    public void removeValidators() {
        this.validators.clear();
    }

    /**
     * Update model based on the form fields
     *
     * @throws JPFormException
     */
    public void updateModel() throws JPFormException {
        T backup = getModel();
        for (JPFormComponent<?> comp : getFormComponents()) {
            if (!(comp instanceof JPReadOnly)
                    && comp.isUpdatable()) {
                try {
                    setModelProperty(comp, getModel());
                } catch (IllegalAccessException | InvocationTargetException e) {
                    setModel(backup);
                    throw new JPFormException("Exception " + e.getClass() + " during update of model", e);
                }
            }
        }
    }

    /**
     * Update form based on the form fields
     *
     * @throws JPFormException
     */
    @SuppressWarnings("unchecked")
    public void updateForm() throws JPFormException {
        for (JPFormComponent<?> comp : getFormComponents()) {
            if (comp.isUpdatable()) {
                try {
                    setFormProperty((JPFormComponent<Object>) comp, getModel());
                } catch (IllegalAccessException | InvocationTargetException e) {
                    throw new JPFormException("Exception " + e.getClass() + " during update of form", e);
                }
            }
        }
    }

    /**
     * validates the form (without updating the model)
     * Also marks al components with invalid data as invalid
     * This only works on form components that have implemented the behaviour
     *
     * @return {@link List<String>}
     */
    public List<String> validateForm() {
        List<String> invalidComponents = new ArrayList<>();
        for (JPFormComponent<?> comp : getFormComponents()) {
            if (!comp.isValid()) {
                comp.markAsInvalid();
                String lbl = comp.getValidationLabel();
                if (lbl == null) {
                    lbl = comp.getId();
                }
                invalidComponents.add(lbl);
            } else {
                comp.markAsValid();
            }
        }
        repaint();
        revalidate();
        return invalidComponents;
    }

    /**
     * Validates the model and returns a list of validation warnings
     *
     * @return
     * @throws JPFormException
     */
    public List<String> validateModel() throws JPFormException {
        List<String> result = new ArrayList<>();
        for (JPFormValidator validator : validators) {
            try {
                result.add(validator.validate(getValueOfModelProperty(validator.getId(), getModel())));
            } catch (RuntimeException | IllegalAccessException | InvocationTargetException e) {
                throw new JPFormException("Exception " + e.getClass() + " during validation", e);
            }
        }
        return result.stream()
                .filter(s -> s != null)
                .collect(Collectors.toList());
    }

    /**
     * @param comp
     * @param property
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     * @throws JPFormException
     */
    private void setModelProperty(JPFormComponent<?> comp, Object property)
            throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, JPFormException {
        String[] ids = comp.getId().split("\\.");
        int count = 1;
        Method method = null;
        for (String id : ids) {
            if (count < ids.length) {
                Object temp = getGetterMethod(id, property).invoke(property);
                if (temp == null) {
                    try {
                        Field field = getField(id, property);
                        property = getNewInstanceOfProperty(id, property, field);
                    } catch (InstantiationException | NoSuchMethodException | NoSuchFieldException e) {
                        throw new JPFormException("Property " + id + " is null. Auto construct is not possible. Property " + comp.getId() + " " +
                                "cannot be set!");
                    }
                } else {
                    property = temp;
                }
            } else {
                method = getSetterMethod(id, property);
            }
            count++;
        }

        if (method != null) {
            method.invoke(property, comp.getComponentValue());
        }
    }

    /**
     * @param comp
     * @param property
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     * @throws RuntimeException
     */
    private void setFormProperty(JPFormComponent<Object> comp, Object property)
            throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, RuntimeException {
        String[] ids = comp.getId().split("\\.");
        for (String id : ids) {
            if (property != null) {
                property = getGetterMethod(id, property).invoke(property);
            } else {
                break;
            }
        }
        try {
            comp.setComponentValue(property);
        } catch (ClassCastException ex) {
            comp.setComponentValue(property != null ? property.toString() : null);
        }
    }

    /**
     * @param fullId   {@link String}
     * @param property {@link Object}
     * @return
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     */
    private Object getValueOfModelProperty(String fullId, Object property)
            throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        String[] ids = fullId.split("\\.");
        for (String id : ids) {
            if (property != null) {
                property = getGetterMethod(id, property).invoke(property);
            } else {
                break;
            }
        }
        return property;
    }

    /**
     * Tries to set the model of the form to a new instance
     * If the class of the model doesn't have a constructor without parameters, it wil throw an {@link JPFormException}
     *
     * @throws JPFormException
     */
    private void setModelBasedOnType() throws JPFormException {
        if (getClassForNewInstance() != null) {
            try {
                setModel((T) getNewInstanceOf(getClassForNewInstance()));
            } catch (InstantiationException | IllegalAccessException
                    | IllegalArgumentException | InvocationTargetException
                    | NoSuchMethodException | SecurityException e) {
                throw new JPFormException("Can't create new instance of " + getClassForNewInstance() +
                        ", make sure it has a constructor with no arguments or set the model manually", e);
            }
        } else {
            throw new JPFormException("Can't create new instance for form, class of model is unknown");
        }
    }

    /**
     * Sets the class that is used for creating a new instance. If this is null, or the wrong class, then it will throw a {@link JPFormException}
     *
     * @throws JPFormException
     */
    private void setClazz() throws JPFormException {
        try {
            this.clazz = (Class<T>) getModel().getClass();
        } catch (NullPointerException | ClassCastException e) {
            throw new JPFormException(e);
        }
    }

    @Override
    public Component add(Component comp) {
        comp = super.add(comp);
        setInitValue(comp);
        return comp;
    }

    @Override
    public Component add(Component comp, int index) {
        comp = super.add(comp, index);
        setInitValue(comp);
        return comp;
    }

    @Override
    public Component add(String name, Component comp) {
        comp = super.add(name, comp);
        setInitValue(comp);
        return comp;
    }

    @Override
    public void add(Component comp, Object constraints) {
        super.add(comp, constraints);
        setInitValue(comp);
    }

    @Override
    public void add(Component comp, Object constraints, int index) {
        super.add(comp, constraints, index);
        setInitValue(comp);
    }

    private void setInitValue(Component component) {
        if (component instanceof JPFormComponent) {
            JPFormComponent<?> formComponent = (JPFormComponent<?>) component;
            if (formComponent.isUpdatable()) {
                try {
                    setFormProperty((JPFormComponent<Object>) formComponent, getModel());
                } catch (IllegalAccessException | InvocationTargetException e) {
                    throw new JPFormException("Exception " + e.getClass() + " during update of form", e);
                }
            }
        }
    }

    /**
     * Returns the class used by the form
     *
     * @return {@link Class<T>}
     */
    public Class<T> getClassForNewInstance() {
        return clazz;
    }
}