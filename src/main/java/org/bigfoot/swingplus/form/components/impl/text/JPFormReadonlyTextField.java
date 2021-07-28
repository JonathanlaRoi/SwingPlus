package org.bigfoot.swingplus.form.components.impl.text;

import org.bigfoot.swingplus.form.components.JPReadOnly;

/**
 * @author Jonathan la Roi
 * @since 8/24/2020
 */
public class JPFormReadonlyTextField extends JPFormTextField implements JPReadOnly {
    public JPFormReadonlyTextField(String id) {
        super(id);
        setEditable(false);
    }

    public JPFormReadonlyTextField(String id, String text) {
        super(id, text);
        setEditable(false);
    }
}
