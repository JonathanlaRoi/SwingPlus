package org.bigfoot.swingplus.form;

import lombok.extern.apachecommons.CommonsLog;
import org.bigfoot.swingplus.configurable.components.calendar.JPDatepickerTextField;
import org.bigfoot.swingplus.form.components.JPEditable;
import org.bigfoot.swingplus.form.components.JPFormComponent;
import org.bigfoot.swingplus.form.components.JPReadOnly;
import org.bigfoot.swingplus.form.exception.JPFormException;
import org.reflections.Reflections;

import javax.swing.text.JTextComponent;
import java.awt.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

@CommonsLog
public class JPFormHelper {

    //Internal class
    private JPFormHelper() {
    }

    ;

    /**
     * @param id
     * @param obj
     * @return
     */
    protected static Method getSetterMethod(String id, Object obj) throws NullPointerException, RuntimeException {
        if (obj == null) {
            throw new JPFormException("Parent of property " + id + " is null!");
        }

        Method method = null;
        Field field = null;
        Exception ex = null;

        try {
            field = obj.getClass().getDeclaredField(id);
        } catch (SecurityException | NoSuchFieldException e) {
            ex = e;
        }

        List<String> methodNames = Arrays.asList(
                "set" + id.substring(0, 1).toUpperCase() + id.substring(1),
                "set" + id,
                id
        );
        if (field != null) {
            for (String methodName : methodNames) {
                try {
                    method = obj.getClass().getMethod(methodName, field.getType());
                    break;
                } catch (SecurityException | NoSuchMethodException e) {
                    e.getStackTrace();
                    ex = e;
                }
            }
        }
        if (method == null) {
            for (String methodName : methodNames) {
                for (Method classMethod : obj.getClass().getDeclaredMethods()) {
                    if (classMethod.getName().equals(methodName)) {
                        method = classMethod;
                        break;
                    }
                }
            }
        }
        if (method == null) {
            throw new JPFormException("No setter method found for field " + id, ex);
        }
        return method;
    }

    /**
     * @param id
     * @return
     */
    protected static Method getGetterMethod(String id, Object obj) {
        if (obj == null) {
            throw new JPFormException("Parent of property " + id + " is null!");
        }

        Method method = null;
        Exception ex = null;

        List<String> methodNames = Arrays.asList(
                "get" + id.substring(0, 1).toUpperCase() + id.substring(1),
                "is" + id.substring(0, 1).toUpperCase() + id.substring(1),
                "get" + id,
                "is" + id,
                id
        );
        for (String methodName : methodNames) {
            try {
                method = obj.getClass().getMethod(methodName);
            } catch (SecurityException | NoSuchMethodException e) {
                ex = e;
            }
        }
        if (method == null) {
            for (String methodName : methodNames) {
                for (Method classMethod : obj.getClass().getDeclaredMethods()) {
                    if (classMethod.getName().equals(methodName)) {
                        method = classMethod;
                        break;
                    }
                }
            }
        }
        if (method == null) {
            throw new JPFormException("No getter method found for field " + id, ex);
        }
        return method;
    }

    protected static Field getField(String id, Object obj) throws NoSuchFieldException, SecurityException {
        return obj.getClass().getDeclaredField(id);
    }

    protected static Object getNewInstanceOfProperty(String id, Object property, Field field)
            throws IllegalAccessException, IllegalArgumentException, InvocationTargetException,
            InstantiationException, NoSuchMethodException, SecurityException {
        getSetterMethod(id, property).invoke(property, getNewInstanceOf(field.getType()));
        return getGetterMethod(id, property).invoke(property);
    }

    protected static Object getNewInstanceOf(Class<?> type)
            throws InstantiationException, IllegalAccessException, IllegalArgumentException,
            InvocationTargetException, NoSuchMethodException, SecurityException {
        if (type.isInterface()) {
            Object impl = getSubInstanceOf(type);
            if (impl == null) {
                throw new JPFormException("Cannot find implementation of interface! Cannot initialize model");
            }
            return impl;
        } else {
            return type.getConstructor().newInstance();
        }
    }

    private static Object getSubInstanceOf(Class<?> type) {
        Reflections reflections = new Reflections(type);
        Object sub = null;
        for (Class<?> subType : reflections.getSubTypesOf(type)) {
            try {
                sub = getNewInstanceOf(subType);
                break;
            } catch (InstantiationException | IllegalAccessException | IllegalArgumentException |
                    InvocationTargetException | NoSuchMethodException | SecurityException e) {
                throw new JPFormException("Cannot create instance of class " + type, e);
            }
        }
        return sub;
    }

    public static void setEditable(boolean editable, JPFormContainer container) {
        List<JPFormComponent<?>> components = container.getFormComponents();
        for (JPFormComponent<?> comp : components) {
            if (comp instanceof Component) {
                if (comp instanceof JTextComponent && !(comp instanceof JPDatepickerTextField)) {
                    ((JTextComponent) comp).setEditable(editable);
                } else if (comp instanceof JPEditable) {
                    ((JPEditable) comp).setEditable(editable);
                } else if (!(comp instanceof JPReadOnly)) {
                    ((Component) comp).setEnabled(editable);
                }
            }
        }
    }

    public static JPFormPanel<?> getFormOfComponent(JPFormComponent<?> component) {
        if (component instanceof Component) {
            return getFormOfComponent((Component) component);
        }
        return null;
    }

    public static JPFormPanel<?> getFormOfComponent(Component component) {
        if (component != null) {
            if (component instanceof JPFormPanel) {
                return (JPFormPanel<?>) component;
            } else {
                Container parent = component.getParent();
                if (parent instanceof JPFormPanel) {
                    return (JPFormPanel<?>) parent;
                } else if (parent != null) {
                    return getFormOfComponent(parent);
                }
            }
        }
        return null;
    }
}
