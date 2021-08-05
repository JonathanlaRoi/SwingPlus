package org.bigfoot.swingplus.configurable.table.sorter;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.swing.*;

/**
 * @author Jonathan la Roi
 * @since 27/10/2020
 */
@AllArgsConstructor
@Getter
public enum JPSortOrder {
    ASCENDING(true, false, SortOrder.ASCENDING),
    DESCENDING(false, true, SortOrder.DESCENDING),
    UNSORTED(false, false, SortOrder.UNSORTED);

    private final boolean ascending;

    private final boolean descending;

    private final SortOrder sortOrder;

    public static JPSortOrder of(SortOrder sortOrder) {
        return valueOf(sortOrder.name());
    }
}
