package org.bigfoot.swingplus.form.components.impl;

import lombok.Getter;
import lombok.Setter;
import net.miginfocom.swing.MigLayout;
import org.bigfoot.swingplus.configurable.JPModeledPanel;
import org.bigfoot.swingplus.configurable.components.JPCheckBox;
import org.bigfoot.swingplus.form.components.JPFormComponent;

/**
 * @author Jonathan la Roi
 * @since 28/07/2021
 */
public class JPFormOptionalRadioGroup<MODEL> extends JPModeledPanel<MODEL> implements JPFormComponent<MODEL> {

    @Getter
    @Setter
    private String id;

    @Getter
    @Setter
    private boolean updatable = true;

    private JPCheckBox chb;

    private JPFormRadioGroup<MODEL> rdbgroup;

    public JPFormOptionalRadioGroup(String id, MODEL... values) {
        super(new MigLayout("", "[shrink][grow, fill]", "[]"), null);
        setId(id);
        chb = new JPCheckBox(null, e -> rdbgroup.onConfigure());
        rdbgroup = new JPFormRadioGroup<MODEL>(id, values) {
            @Override
            public void onConfigure() {
                super.onConfigure();
                setVisible(chb.isSelected());
            }
        };
        add(chb, "left, shrinkx");
        add(rdbgroup, "grow");
    }

    @Override
    public void setComponentValue(MODEL value) {
        chb.setSelected(value != null);
        rdbgroup.setComponentValue(value);
        rdbgroup.onConfigure();
    }

    @Override
    public MODEL getComponentValue() {
        return chb.isSelected() ? rdbgroup.getComponentValue() : null;
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        chb.setEnabled(enabled);
        rdbgroup.setEnabled(enabled);
    }
}