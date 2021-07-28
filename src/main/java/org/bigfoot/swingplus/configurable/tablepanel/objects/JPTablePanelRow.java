package org.bigfoot.swingplus.configurable.tablepanel.objects;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class JPTablePanelRow<OBJECT, SORT> {
	
	private OBJECT object;
	
	private List<JPTablePanelColumn<OBJECT, ?, SORT>> columns;
	
}
