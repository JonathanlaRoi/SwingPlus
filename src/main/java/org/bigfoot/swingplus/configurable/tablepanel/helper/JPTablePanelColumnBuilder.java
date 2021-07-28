package org.bigfoot.swingplus.configurable.tablepanel.helper;

import java.awt.Component;
import java.util.function.Function;

import javax.swing.JLabel;

import org.bigfoot.swingplus.configurable.tablepanel.objects.JPTablePanelColumn;

public class JPTablePanelColumnBuilder {
	
	public static <OBJECT, SORT> JPTablePanelColumn<OBJECT, Void, SORT> getEmptyColumn(String migLayoutProperties, String migLayoutCellProperties){
		return new JPTablePanelColumn<OBJECT, Void, SORT>("", null, null, migLayoutProperties, migLayoutCellProperties){
			@Override
			public Component createComponent(OBJECT model, Void value) {
				return new JLabel();
			}
		};
	}
	
	public static <OBJECT, SORT> JPTablePanelColumn<OBJECT, String, SORT> getStringColumn(String header, SORT sort, Function<OBJECT, String> getter){
		return new JPTablePanelColumn<OBJECT, String, SORT>(header, sort, getter, "[grow]", "growx"){
			@Override
			public Component createComponent(OBJECT model, String value) {
				return new JLabel(value);
			}
			
		};
	}
}
