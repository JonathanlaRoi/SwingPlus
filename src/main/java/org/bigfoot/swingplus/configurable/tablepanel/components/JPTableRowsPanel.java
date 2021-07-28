package org.bigfoot.swingplus.configurable.tablepanel.components;

import java.awt.Component;
import java.util.List;

import net.miginfocom.swing.MigLayout;

import org.bigfoot.swingplus.configurable.JPPanel;
import org.bigfoot.swingplus.configurable.tablepanel.objects.JPTablePanelColumn;
import org.bigfoot.swingplus.configurable.tablepanel.objects.JPTablePanelRow;

public class JPTableRowsPanel<OBJECT, SORT> extends JPPanel {
	
	public JPTableRowsPanel(List<JPTablePanelRow<OBJECT, SORT>> rows){
		super();
		setLayout(new MigLayout("","[grow]",""));
		
		int rowIndex = 0;
		for(JPTablePanelRow<OBJECT, SORT> row : rows){
			int columnIndex = 0;
			
			JPTableRowPanel<OBJECT> rowPanel = new JPTableRowPanel<OBJECT>(new MigLayout("", getLayoutString(rows), "[30px]"), row.getObject(), row.getColumns().size());
			for(JPTablePanelColumn<OBJECT, ?, SORT> column : row.getColumns()){
				rowPanel.add(column.getComponent(row.getObject()), "cell " + columnIndex + " 0 " + getCellLayoutString(column));
				columnIndex++;
			}
			add(rowPanel, "cell 0 " + rowIndex + ", growx");
			rowIndex++;
		}
	}
	
	@SuppressWarnings("unchecked")
	protected JPTableRowPanel<OBJECT> getRowPanel(OBJECT model){
		Component[] comps = getComponents();
		for(Component comp : comps){
			if(comp instanceof JPTableRowPanel){
				JPTableRowPanel<OBJECT> rowPnl = (JPTableRowPanel<OBJECT>) comp;
				if(rowPnl.getModel().equals(model)){
					return rowPnl;
				}
			}
		}
		return null;
	}
	
	protected String getLayoutString(List<JPTablePanelRow<OBJECT, SORT>> rows){
		String value = "";
		
		if(rows.size() > 0){
			List<JPTablePanelColumn<OBJECT, ?, SORT>> columns = rows.get(0).getColumns();
			for(JPTablePanelColumn<OBJECT, ?, SORT> column : columns){
				//TODO verdere styling
				value += "["+column.getMigLayoutProperties()+"]";
			}
		}
		
		return value;
	}
	
	protected String getCellLayoutString(JPTablePanelColumn<OBJECT, ?, SORT> column){
		return column.getMigLayoutCellProperties() != null ? "," + column.getMigLayoutCellProperties() : "";
	}
	
	
}
