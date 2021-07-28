package org.bigfoot.swingplus.configurable.components.select;

import lombok.Getter;
import lombok.Setter;
import org.bigfoot.swingplus.configurable.JPConfigurable;
import org.bigfoot.swingplus.configurable.components.JPButton;

import javax.annotation.Nonnull;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Jonathan la Roi
 * @since 8/24/2020
 */
public class JPMultiSelect<MODEL> extends JPButton implements JPConfigurable {

    private final JPopupMenu menu;

    @Getter
    private List<MODEL> items = new ArrayList<>();

    @Getter
    private List<MODEL> selectedItems = new ArrayList<>();

    public JPMultiSelect(String label) {
        this(label, new ArrayList<>());
    }

    public JPMultiSelect(String label, MODEL... items) {
        this(label, Arrays.asList(items));
    }

    public JPMultiSelect(String label, List<MODEL> items) {
        super(label);
        setCursor(Cursor.getDefaultCursor());
        this.items = items;

        menu = new JPopupMenu() {
            /**
             *
             */
            private static final long serialVersionUID = -6309867311798105665L;

            @Override
            public Dimension getPreferredSize() {
                Dimension preferred = super.getPreferredSize();
                Dimension minimum = getMinimumSize();
                Dimension maximum = getMaximumSize();
                preferred.width = Math.min(Math.max(preferred.width, JPMultiSelect.this.getWidth()), maximum.width);
                preferred.height = Math.min(Math.max(preferred.height, minimum.height), maximum.height);
                return preferred;
            }
        };

        addActionListener(e -> {
            if (!menu.isVisible()) {
                Point p = JPMultiSelect.this.getLocationOnScreen();
                menu.setInvoker(JPMultiSelect.this);
                menu.setLocation((int) p.getX(), (int) p.getY() + JPMultiSelect.this.getHeight());
                menu.setVisible(true);
            } else {
                menu.setVisible(false);
            }
        });
        initMenu();
    }

    public void setItems(@Nonnull List<MODEL> items) {
        this.items = items;
        selectedItems = new ArrayList<>();
        initMenu();
    }

    public void setSelectedItems(@Nonnull List<MODEL> items) {
        selectedItems.clear();
        for (MODEL item : items) {
            if (this.items.contains(item)) {
                selectedItems.add(item);
            }
        }
        initMenu();
    }

    public void selectItem(MODEL item) {
        if (items.contains(item) && !selectedItems.contains(item)) {
            selectedItems.add(item);
        }
    }

    public void deselectItem(MODEL item) {
        if (items.contains(item)) {
            selectedItems.remove(item);
        }
    }

    public void clearSelection() {
        selectedItems.clear();
    }

    public void onSelectionChange(List<MODEL> selection) {

    }

    private void initMenu() {
        menu.removeAll();
        for (MODEL item : items) {
            menu.add(new JPMultiSelectCheckbox(item));
        }
        menu.repaint();
        menu.revalidate();
    }

    @Getter
    @Setter
    private class JPMultiSelectCheckbox extends JCheckBoxMenuItem {
        private MODEL item;

        public JPMultiSelectCheckbox(@Nonnull MODEL item) {
            super(item.toString());
            this.item = item;
            setSelected(selectedItems.contains(item));
            addActionListener(e -> {
                if (isSelected()) {
                    selectedItems.add(item);
                } else {
                    selectedItems.remove(item);
                }
                onSelectionChange(selectedItems);
            });
        }
    }
}
