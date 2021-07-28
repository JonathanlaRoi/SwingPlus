package org.bigfoot.swingplus.configurable.tablepanel.components;

import java.awt.Color;
import java.awt.LayoutManager;

import javax.swing.BorderFactory;

import org.bigfoot.swingplus.configurable.JPModeledPanel;

public class JPTableRowPanel<OBJECT> extends JPModeledPanel<OBJECT> {
	public JPTableRowPanel(LayoutManager arg0, OBJECT arg1, int columnCount) {
		super(arg0, arg1);
		this.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY));
	}
}
