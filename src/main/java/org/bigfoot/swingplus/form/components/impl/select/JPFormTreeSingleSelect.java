package org.bigfoot.swingplus.form.components.impl.select;

import lombok.Getter;
import lombok.Setter;
import org.bigfoot.swingplus.configurable.components.select.JPTree;
import org.bigfoot.swingplus.configurable.components.select.model.JPTreeNode;
import org.bigfoot.swingplus.configurable.components.select.model.JPTreeRootNode;
import org.bigfoot.swingplus.form.components.JPFormComponent;

import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static org.bigfoot.swingplus.form.components.impl.select.util.JPFormTreeUtils.getNodesForList;

/**
 * @author Jonathan la Roi
 * @since 23/07/2021
 */
public class JPFormTreeSingleSelect<MODEL> extends JPTree<MODEL> implements JPFormComponent<MODEL> {

    @Getter
    @Setter
    private String id;

    @Getter
    @Setter
    private boolean updatable = true;

    public JPFormTreeSingleSelect(String id, JPTreeNode<MODEL> node) {
        super(node);
        setId(id);
        setRootVisible(true);
        getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
    }

    public JPFormTreeSingleSelect(String id, List<JPTreeNode<MODEL>> nodes) {
        super();
        setId(id);
        setRootVisible(false);
        setModel(nodes);
        getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
    }

    public JPFormTreeSingleSelect(String id, List<MODEL> values, Function<MODEL, MODEL> parentFunction) {
        super();
        setId(id);
        setModel(values, parentFunction);
        getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
    }

    public void setModel(JPTreeNode<MODEL> node) {
        MODEL model = Optional.ofNullable(getSelectedNode()).map(JPTreeNode::getModel).orElse(null);
        setRootVisible(true);
        super.setModel(new DefaultTreeModel(node));
        setComponentValue(model);
    }

    public void setModel(List<JPTreeNode<MODEL>> nodes) {
        MODEL model = Optional.ofNullable(getSelectedNode()).map(JPTreeNode::getModel).orElse(null);
        JPTreeRootNode root = new JPTreeRootNode("HiddenRoot");
        nodes.forEach(root::add);
        super.setModel(new DefaultTreeModel(root));
        setRootVisible(false);
        expandRow(0);
        setComponentValue(model);
    }

    public void setModel(List<MODEL> values, Function<MODEL, MODEL> parentFunction) {
        List<JPTreeNode<MODEL>> nodes = getNodesForList(values, parentFunction);
        if (nodes.size() == 1) {
            setModel(nodes.get(0));
        } else {
            setModel(nodes);
        }
    }

    @Override
    public void setComponentValue(MODEL value) {
        Object root = getRoot();
        if (root instanceof TreeNode) {
            setSelectedNode(getNodeForSelection(value, (TreeNode) root));
        }
    }

    public void setSelectedNode(JPTreeNode<MODEL> node) {
        if (node != null) {
            DefaultTreeModel model = (DefaultTreeModel) getModel();
            TreeNode[] select = model.getPathToRoot(node);
            TreePath path = new TreePath(select);
            setSelectionPath(path);
        } else {
            clearSelection();
        }
    }

    private JPTreeNode<MODEL> getNodeForSelection(MODEL value, TreeNode node) {
        int childCount = node.getChildCount();
        if (value == null) {
            return null;
        }
        if (node instanceof JPTreeNode && value.equals(((JPTreeNode<MODEL>) node).getModel())) {
            return (JPTreeNode<MODEL>) node;
        }
        for (int i = 0; i < childCount; i++) {
            JPTreeNode<MODEL> child = getNodeForSelection(value, (JPTreeNode<MODEL>) node.getChildAt(i));
            if (child != null) {
                return child;
            }
        }
        return null;
    }

    @Override
    public MODEL getComponentValue() {
        return Optional.ofNullable(getSelectedNode()).map(JPTreeNode::getModel).orElse(null);
    }

    public JPTreeNode<MODEL> getSelectedNode() {
        TreePath[] paths = getSelectionPaths();
        if (paths != null) {
            for (TreePath path : getSelectionPaths()) {
                if (path.getLastPathComponent() instanceof JPTreeNode) {
                    return (JPTreeNode<MODEL>) path.getLastPathComponent();
                }
            }
        }
        return null;
    }

    private Object getRoot() {
        return Optional.ofNullable(getModel())
                .map(TreeModel::getRoot)
                .orElse(null);
    }
}
