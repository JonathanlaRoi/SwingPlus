package org.bigfoot.swingplus.configurable.components.select.model;

import javax.swing.tree.DefaultMutableTreeNode;
import java.util.Objects;

/**
 * @author Jonathan la Roi
 * @since 23/07/2021
 */
public class JPTreeNode<MODEL> extends DefaultMutableTreeNode {

    public JPTreeNode(MODEL model) {
        super(model);
    }

    public MODEL getModel() {
        return (MODEL) getUserObject();
    }

    @Override
    public boolean equals(Object other) {
        if (other != null && getModel() != null && other instanceof JPTreeNode) {
            return Objects.equals(((JPTreeNode<?>) other).getModel(), getModel());
        }
        return super.equals(other);
    }

    @Override
    public int hashCode() {
        if (getModel() != null) {
            return getModel().hashCode();
        }
        return super.hashCode();
    }
}
