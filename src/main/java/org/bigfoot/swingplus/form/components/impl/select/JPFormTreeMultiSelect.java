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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.bigfoot.swingplus.form.components.impl.select.util.JPFormTreeUtils.getNodesForList;

/**
 * @author Jonathan la Roi
 * @since 27/07/2021
 */
public class JPFormTreeMultiSelect<MODEL> extends JPTree<MODEL> implements JPFormComponent<List<MODEL>> {

    @Getter
    @Setter
    private String id;

    @Getter
    @Setter
    private boolean updatable = true;

    public JPFormTreeMultiSelect(String id, JPTreeNode<MODEL> node) {
        super(node);
        setId(id);
        setRootVisible(true);
        getSelectionModel().setSelectionMode(TreeSelectionModel.DISCONTIGUOUS_TREE_SELECTION);
    }

    public JPFormTreeMultiSelect(String id, List<JPTreeNode<MODEL>> nodes) {
        super();
        setId(id);
        setRootVisible(false);
        setModel(nodes);
        getSelectionModel().setSelectionMode(TreeSelectionModel.DISCONTIGUOUS_TREE_SELECTION);
    }

    public JPFormTreeMultiSelect(String id, List<MODEL> values, Function<MODEL, MODEL> parentFunction) {
        super();
        setId(id);
        setModel(values, parentFunction);
        getSelectionModel().setSelectionMode(TreeSelectionModel.DISCONTIGUOUS_TREE_SELECTION);
    }

    public void setModel(JPTreeNode<MODEL> node) {
        List<MODEL> model = getComponentValue();
        super.setModel(new DefaultTreeModel(node));
        setComponentValue(model);
    }

    public void setModel(List<JPTreeNode<MODEL>> nodes) {
        List<MODEL> model = getComponentValue();
        JPTreeRootNode root = new JPTreeRootNode("HiddenRoot");
        nodes.forEach(root::add);
        super.setModel(new DefaultTreeModel(root));
        setRootVisible(false);
        expandRow(0);
        setComponentValue(model);
    }

    public void setModel(List<MODEL> values, Function<MODEL, MODEL> parentFunction) {
        List<JPTreeNode<MODEL>> nodes = getNodesForList(values, parentFunction);
        if(nodes.size() == 1) {
            setModel(nodes.get(0));
        } else {
            setModel(nodes);
        }
    }

    @Override
    public void setComponentValue(List<MODEL> value) {
        Object root = getRoot();
        if (root instanceof TreeNode) {
            setSelectedNodes(getNodesForSelection(value, (TreeNode) root));
        }
    }

    public void setSelectedNodes(List<JPTreeNode<MODEL>> nodes) {
        if (nodes != null && nodes.isEmpty()) {
            DefaultTreeModel model = (DefaultTreeModel) getModel();
            List<TreePath> selects = new ArrayList<>();
            for (JPTreeNode<MODEL> node : nodes) {
                TreeNode[] select = model.getPathToRoot(node);
                selects.add(new TreePath(select));
            }
            TreePath[] selectsArray = new TreePath[selects.size()];
            for (int i = 0; i < selectsArray.length; i++) {
                selectsArray[i] = selects.get(i);
            }
            setSelectionPaths(selectsArray);
        } else {
            clearSelection();
        }
    }

    private List<JPTreeNode<MODEL>> getNodesForSelection(List<MODEL> values, TreeNode node) {
        int childCount = node.getChildCount();
        List<JPTreeNode<MODEL>> nodes = new ArrayList<>();
        if (values == null || values.isEmpty()) {
            return nodes;
        }
        if (node instanceof JPTreeNode && values.contains(((JPTreeNode<MODEL>) node).getModel())) {
            nodes.add((JPTreeNode<MODEL>) node);
        }
        for (int i = 0; i < childCount; i++) {
            nodes.addAll(getNodesForSelection(values, (JPTreeNode<MODEL>) node.getChildAt(i)));
        }
        return nodes;
    }

    @Override
    public List<MODEL> getComponentValue() {
        return Optional.ofNullable(getSelectedNodes()).map(l ->
                l.stream()
                        .map(JPTreeNode::getModel)
                        .collect(Collectors.toList())
        ).orElse(null);
    }

    public List<JPTreeNode<MODEL>> getSelectedNodes() {
        List<JPTreeNode<MODEL>> items = new ArrayList<>();
        TreePath[] paths = getSelectionPaths();
        if (paths != null) {
            for (TreePath path : getSelectionPaths()) {
                if (path.getLastPathComponent() instanceof JPTreeNode) {
                    items.add((JPTreeNode<MODEL>) path.getLastPathComponent());
                }
            }
        }
        return items;
    }

    private Object getRoot() {
        return Optional.ofNullable(getModel())
                .map(TreeModel::getRoot)
                .orElse(null);
    }
}
