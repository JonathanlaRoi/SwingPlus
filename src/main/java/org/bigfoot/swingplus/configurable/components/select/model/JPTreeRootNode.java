package org.bigfoot.swingplus.configurable.components.select.model;

import javax.swing.tree.DefaultMutableTreeNode;
import java.util.Objects;

/**
 * @author Jonathan la Roi
 * @since 26/07/2021
 */
public class JPTreeRootNode extends DefaultMutableTreeNode {

    public JPTreeRootNode(String name) {
        super(name);
    }

    public String getName() {
        return super.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof JPTreeRootNode) {
            return Objects.equals(this.getName(), ((JPTreeRootNode) obj).getName());
        }
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return getName().hashCode();
    }
}
