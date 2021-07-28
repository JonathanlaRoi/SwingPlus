package org.bigfoot.swingplus.configurable.components.select;

import org.bigfoot.swingplus.configurable.JPConfigurable;

import javax.swing.*;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;
import java.util.Hashtable;
import java.util.Vector;

/**
 * @author Jonathan la Roi
 * @since 23/07/2021
 */
public class JPTree<MODEL> extends JTree implements JPConfigurable {

    public JPTree() {
        super();
        postConstruct();
    }

    public JPTree(Object[] value) {
        super(value);
        postConstruct();
    }

    public JPTree(Vector<?> value) {
        super(value);
        postConstruct();
    }

    public JPTree(Hashtable<?, ?> value) {
        super(value);
        postConstruct();
    }

    public JPTree(TreeNode root) {
        super(root);
        postConstruct();
    }

    public JPTree(TreeNode root, boolean asksAllowsChildren) {
        super(root, asksAllowsChildren);
        postConstruct();
    }

    public JPTree(TreeModel newModel) {
        super(newModel);
        postConstruct();
    }

    private void postConstruct() {
        setExpandsSelectedPaths(true);
        setScrollsOnExpand(true);
    }
}
