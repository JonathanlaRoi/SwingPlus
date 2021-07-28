package org.bigfoot.swingplus.configurable.components.text;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * @author Jonathan la Roi
 * @since 8/26/2020
 */
@FunctionalInterface
public interface JPDocumentListener extends DocumentListener {
    @Override
    default void insertUpdate(DocumentEvent e) {
        onChange(e);
    }

    @Override
    default void removeUpdate(DocumentEvent e) {
        onChange(e);
    }

    @Override
    default void changedUpdate(DocumentEvent e) {
        onChange(e);
    }

    void onChange(DocumentEvent e);
}
