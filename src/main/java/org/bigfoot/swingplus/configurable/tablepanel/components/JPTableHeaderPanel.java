package org.bigfoot.swingplus.configurable.tablepanel.components;

import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Icon;

import lombok.Getter;
import net.miginfocom.swing.MigLayout;

import org.bigfoot.swingplus.configurable.JPPanel;
import org.bigfoot.swingplus.configurable.tablepanel.objects.JPSortOrder;
import org.bigfoot.swingplus.configurable.tablepanel.objects.JPTablePanelColumn;

@Deprecated
public abstract class JPTableHeaderPanel<OBJECT, SORT> extends JPPanel {
	
	@Getter
	private SORT sort;
	
	@Getter
	private JPSortOrder order;
	
	public JPTableHeaderPanel(List<JPTablePanelColumn<OBJECT, ?, SORT>> columns){
		super();
		setLayout(new MigLayout("", getLayoutString(columns), "[30px]"));
		setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
		int columnIndex = 0;
		for(JPTablePanelColumn<OBJECT, ?, SORT> column : columns){
			add(new JPTableHeaderLabel<SORT>(column.getHeader(), column.getSortProperty(), order){
				@Override
				public void onSort(){
					setSortOrder(getSort(), getOrder());
					JPTableHeaderPanel.this.onSort();
				}
				
				@Override
				public Icon getAscIcon() {
					return JPTableHeaderPanel.this.getAscIcon();
				}

				@Override
				public Icon getDescIcon() {
					return JPTableHeaderPanel.this.getDescIcon();
				}
				
			}, "cell " + columnIndex + " 0" + getCellLayoutString(column));
			columnIndex++;
		}
	}
	
	private String getLayoutString(List<JPTablePanelColumn<OBJECT, ?, SORT>> columns){
		String value = "";
		
		for(JPTablePanelColumn<OBJECT, ?, SORT> column : columns){
			value += "["+column.getMigLayoutProperties()+"]";
		}
		
		return value;
	}
	
	private String getCellLayoutString(JPTablePanelColumn<OBJECT, ?, SORT> column){
		return column.getMigLayoutCellProperties() != null ? "," + column.getMigLayoutCellProperties() : "";
	}
	
	@SuppressWarnings("unchecked")
	private List<JPTableHeaderLabel<SORT>> getColumnComponents(){
		List<Component> comps = Arrays.asList(this.getComponents());
		List<JPTableHeaderLabel<SORT>> columns = new ArrayList<>();
		for(Component comp : comps){
			if(comp instanceof JPTableHeaderLabel){
				columns.add((JPTableHeaderLabel<SORT>)comp);
			}
		}
		return columns;
	}
	
	public void setSortOrder(SORT sort, JPSortOrder order){
		this.sort = sort;
		this.order = order;
		getColumnComponents().forEach(c -> {
			if(c.getSort() != null && c.getSort().equals(sort)){
				c.setOrder(order);
			} else {
				c.setOrder(null);
			}
		});
	}
	
	public abstract void onSort();
	
	public abstract Icon getAscIcon();
	
	public abstract Icon getDescIcon();
}
