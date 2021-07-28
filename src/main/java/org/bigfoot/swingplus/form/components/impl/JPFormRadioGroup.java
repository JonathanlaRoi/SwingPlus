package org.bigfoot.swingplus.form.components.impl;

import lombok.Getter;
import net.miginfocom.swing.MigLayout;
import org.bigfoot.swingplus.form.components.JPFormComponent;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;

public class JPFormRadioGroup<T> extends JPanel implements JPFormComponent<T> {

    private final ButtonGroup buttonGroup;

    private String id;

    private String label;

    private boolean updatable = true;

    @Getter
    private List<T> values;

    private int columns = 3;

    public JPFormRadioGroup(String id) {
        this(id, new ArrayList<>());
    }

    @SafeVarargs
    public JPFormRadioGroup(String id, T... values) {
        this(id, Arrays.asList(values));
    }

    /**
     * @wbp.parser.constructor
     */
    public JPFormRadioGroup(String id, List<T> values) {
        super();
        buttonGroup = new ButtonGroup();
        setLayout(new MigLayout("", "[]", "[30px]"));
        setId(id);
        this.values = values;
        init();
    }

    public void init() {
        super.removeAll();
        int row = 0;
        int col = 0;

        for (T value : values) {
            JPFormGroupElement btn = new JPFormGroupElement(value);
            buttonGroup.add(btn);
            this.add(btn, "cell " + col + " " + row);
            col++;
            if (col == columns) {
                col = 0;
                row++;
            }
        }

        this.repaint();
        this.revalidate();
    }

    public void addOption(T value) {
        values.add(value);
        init();
    }

    public void removeOption(T value) {
        values.remove(value);
        init();
    }

    public void enableOptions() {
        values.forEach(item -> getButtonByItem(buttonGroup, item).setEnabled(true));
    }

    public void disableOptions() {
        values.forEach(item -> getButtonByItem(buttonGroup, item).setEnabled(false));
    }

    public void enableOption(T value) {
        getButtonByItem(buttonGroup, value).setEnabled(true);
    }

    public void disableOption(T value) {
        getButtonByItem(buttonGroup, value).setEnabled(false);
    }

    public void removeAllOptions() {
        values.clear();
        init();
    }

    public void setValues(List<T> values) {
        this.values = values;
    }

    public void addActionListener(ActionListener al) {
        values.forEach(item -> getButtonByItem(buttonGroup, item).addActionListener(al));
    }

    public void removeActionListener(ActionListener al) {
        values.forEach(item -> getButtonByItem(buttonGroup, item).removeActionListener(al));
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
    public void setComponentValue(T value) {
        if (value != null) {
            buttonGroup.clearSelection();
            setSelectedButtonValue(buttonGroup, value);
        }
    }

    @Override
    public T getComponentValue() {
        return getSelectedButtonValue(buttonGroup);
    }

    @Override
    public void setUpdatable(boolean updatable) {
        this.updatable = updatable;
    }

    @Override
    public boolean isUpdatable() {
        return updatable;
    }

    public int getColumns() {
        return columns;
    }

    public void setColumns(int columns) {
        if (this.columns != columns) {
            this.columns = columns;
            init();
        }
    }

    @Override
    public void onConfigure() {

    }

    @SuppressWarnings("unchecked")
    private void setSelectedButtonValue(ButtonGroup buttonGroup, T value) {
        for (Enumeration<AbstractButton> buttons = buttonGroup.getElements(); buttons.hasMoreElements(); ) {
            AbstractButton obj = buttons.nextElement();

            if (obj instanceof JPFormRadioGroup.JPFormGroupElement) {
                JPFormGroupElement button = (JPFormGroupElement) obj;
                if (button.getValue().equals(value)) {
                    button.setSelected(true);
                    break;
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    private T getSelectedButtonValue(ButtonGroup buttonGroup) {
        for (Enumeration<AbstractButton> buttons = buttonGroup.getElements(); buttons.hasMoreElements(); ) {
            AbstractButton button = buttons.nextElement();

            if (button instanceof JPFormRadioGroup.JPFormGroupElement) {
                if (button.isSelected()) {
                    return ((JPFormGroupElement) button).getValue();
                }
            }
        }

        return null;
    }

    @SuppressWarnings("unchecked")
    private JPFormGroupElement getButtonByItem(ButtonGroup buttonGroup, T item) {
        for (Enumeration<AbstractButton> buttons = buttonGroup.getElements(); buttons.hasMoreElements(); ) {
            AbstractButton button = buttons.nextElement();

            if (button instanceof JPFormRadioGroup.JPFormGroupElement) {
                if (((JPFormGroupElement) button).getValue().equals(item)) {
                    return ((JPFormGroupElement) button);
                }
            }
        }

        return null;
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        if (enabled) {
            enableOptions();
        } else {
            disableOptions();
        }
    }

    private class JPFormGroupElement extends JRadioButton {
        private T value;

        public JPFormGroupElement(T value) {
            super(value.toString());
            this.value = value;
        }

        public T getValue() {
            return value;
        }
    }
}
