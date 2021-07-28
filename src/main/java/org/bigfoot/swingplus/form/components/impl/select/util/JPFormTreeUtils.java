package org.bigfoot.swingplus.form.components.impl.select.util;

import org.bigfoot.swingplus.configurable.components.select.model.JPTreeNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

/**
 * @author Jonathan la Roi
 * @since 27/07/2021
 */
public class JPFormTreeUtils {

    private JPFormTreeUtils() {

    }

    public static <MODEL> List<JPTreeNode<MODEL>> getNodesForList(List<MODEL> values, Function<MODEL, MODEL> parentFunction) {
        if (values == null || values.isEmpty()) {
            throw new IllegalArgumentException("values cannot be null");
        }
        if (parentFunction == null) {
            throw new IllegalArgumentException("parentFunction cannot be null");
        }

        List<JPTreeNode<MODEL>> nodes = new ArrayList<>();
        values.stream()
                .filter(value -> parentFunction.apply(value) == null)
                .forEach(value -> {
                    JPTreeNode<MODEL> node = new JPTreeNode<>(value);
                    getChildNodesForParent(values, value, parentFunction).forEach(node::add);
                    nodes.add(node);
                });
        return nodes;
    }

    private static <MODEL> List<JPTreeNode<MODEL>> getChildNodesForParent(List<MODEL> values, MODEL parent, Function<MODEL, MODEL> parentFunction) {
        List<JPTreeNode<MODEL>> nodes = new ArrayList<>();
        values.stream()
                .filter(value -> Objects.equals(parentFunction.apply(value), parent))
                .forEach(value -> {
                    JPTreeNode<MODEL> node = new JPTreeNode<>(value);
                    getChildNodesForParent(values, value, parentFunction).forEach(node::add);
                    nodes.add(node);
                });
        return nodes;
    }
}
