package org.bigfoot.swingplus.configurable;

import java.awt.*;

public class JPModeledPanel<T> extends JPPanel {

    private T model;

    public JPModeledPanel(LayoutManager arg0, boolean arg1, T arg2) {
        super(arg0, arg1);
        this.setModel(arg2);
    }

    public JPModeledPanel(LayoutManager arg0, T arg1) {
        super(arg0);
        this.setModel(arg1);
    }

    public JPModeledPanel(boolean arg0, T arg1) {
        super(arg0);
        this.setModel(arg1);
    }

    public JPModeledPanel(T arg0) {
        super();
        this.setModel(arg0);
    }

    public T getModel() {
        return model;
    }

    public void setModel(T model) {
        this.model = model;
    }
}
