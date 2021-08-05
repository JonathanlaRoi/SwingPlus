package org.bigfoot.swingplus.configurable.table.sorter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Jonathan la Roi
 * @since 04/08/2021
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class JPSort<MODEL> {

    private MODEL sortProperty;

    private JPSortOrder sortOrder;
}
