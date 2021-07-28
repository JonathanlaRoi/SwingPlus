package org.bigfoot.swingplus.configurable.table.sorter;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Jonathan la Roi
 * @since 27/10/2020
 */
@AllArgsConstructor
@Getter
public enum JPSortOrder {
    ASCENDING(true),
    DESCENDING(false);

    private final boolean ascending;
}
