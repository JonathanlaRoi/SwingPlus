package org.bigfoot.swingplus.configurable.components;

import org.bigfoot.swingplus.configurable.JPConfigurable;
import org.bigfoot.swingplus.eventlisteners.JPLambdaMouseClickListener;
import org.bigfoot.swingplus.eventlisteners.JPLambdaPopupMouseListener;

import javax.swing.*;
import java.awt.event.ActionListener;

/**
 * @author Jonathan la Roi
 * @since 1 juni 2019
 */
public class JPComboBox<T> extends JComboBox<T> implements JPConfigurable {

    @Deprecated
    private boolean executeListener = true;

    private boolean forceActionListenerExecution = false;

    public JPComboBox(T... items) {
        super(items);
    }

    public JPComboBox(T[] items, ActionListener actionListener) {
        super(items);
        addActionListener(actionListener);
    }

    public void onConfigure() {

    }

    public void setSelectedTypedItem(T value) {
        setExecuteListener(false);
        T item;
        for (int i = 0; i < super.getItemCount(); i++) {
            item = (T) super.getItemAt(i);
            if (item.equals(value)) {
                setSelectedIndex(i);
                break;
            }
        }
        setExecuteListener(true);
    }

    @SuppressWarnings("unchecked")
    public T getSelectedTypedItem() {
        if (super.getSelectedItem() == null) {
            return null;
        }
        return (T) super.getSelectedItem();
    }

    @Deprecated
    public boolean isExecuteListener() {
        return executeListener;
    }

    @Deprecated
    public void setExecuteListener(boolean executeListener) {
        this.executeListener = executeListener;
    }

    public void setForceActionListenerExecution(boolean forceActionListenerExecution) {
        this.forceActionListenerExecution = forceActionListenerExecution;
    }

    @Override
    protected void fireActionEvent() {
        // indien dit component de focus heeft of als forceActionListenerExecution true is,
        //  worden de actionlisteners uitgevoerd
        if (this.hasFocus() || forceActionListenerExecution)
            super.fireActionEvent();
    }

    public void addMouseListener(JPLambdaPopupMouseListener adapter) {
        super.addMouseListener(adapter);
    }

    public void addMouseListener(JPLambdaMouseClickListener adapter) {
        super.addMouseListener(adapter);
    }
}
