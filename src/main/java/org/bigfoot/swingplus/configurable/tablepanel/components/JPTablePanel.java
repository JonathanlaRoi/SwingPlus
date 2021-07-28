package org.bigfoot.swingplus.configurable.tablepanel.components;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Icon;
import javax.swing.JScrollPane;

import lombok.Getter;
import lombok.Setter;

import org.bigfoot.swingplus.configurable.JPPanel;
import org.bigfoot.swingplus.configurable.tablepanel.objects.JPSortOrder;
import org.bigfoot.swingplus.configurable.tablepanel.objects.JPTablePanelColumn;
import org.bigfoot.swingplus.configurable.tablepanel.objects.JPTablePanelRow;

@Deprecated
public abstract class JPTablePanel<OBJECT, SORT> extends JPPanel {
	
	protected JScrollPane pane;
	
	protected List<JPTablePanelColumn<OBJECT, ?, SORT>> columns;
	
	private JPTableHeaderPanel<OBJECT, SORT> header;
	
	private JPTableRowsPanel<OBJECT, SORT> rowsPanel;

	@Getter
	@Setter
	private Icon ascIcon;
	
	@Getter
	@Setter
	private Icon descIcon;
	
	@Setter
	@Getter
	private String migLayoutProperties;
	
	@Getter
	private JPTableGraphicsConfig graphicsConfig = new JPTableGraphicsConfig();
	
	public JPTablePanel(List<JPTablePanelColumn<OBJECT, ?, SORT>> columns){
		super();
		setLayout(new BorderLayout());
		this.columns = columns;
		
		pane = new JScrollPane();
		pane.getVerticalScrollBar().setUnitIncrement(16);
		header = new JPTableHeaderPanel<OBJECT, SORT>(columns){
			@Override
			public void onSort() {
				refresh();
			}

			@Override
			public Icon getAscIcon() {
				return getGraphicsConfig().getAscendingIcon() != null ? getGraphicsConfig().getAscendingIcon() : JPTablePanel.this.getAscIcon();
			}

			@Override
			public Icon getDescIcon() {
				return getGraphicsConfig().getDescendingIcon() != null ? getGraphicsConfig().getDescendingIcon() : JPTablePanel.this.getDescIcon();
			}
		};
		pane.setColumnHeaderView(header);
		
		add(pane, BorderLayout.CENTER);
	}
	
	public final void setSortOrder(SORT sort, JPSortOrder order){
		header.setSortOrder(sort, order);
	}
	
	public final JPTableRowPanel<OBJECT> getRowPanel(OBJECT model){
		return rowsPanel.getRowPanel(model);
	}
	
	protected void load(SORT sort, JPSortOrder order){
		List<OBJECT> models = getRows(sort, order);
		List<JPTablePanelRow<OBJECT, SORT>> rijen = new ArrayList<>();
		for(OBJECT model : models){
			rijen.add(new JPTablePanelRow<OBJECT, SORT>(model, columns));
		}
		
		rowsPanel = new JPTableRowsPanel<OBJECT, SORT>(rijen);
		pane.setViewportView(rowsPanel);
		pane.repaint();
		pane.revalidate();
	}
	
	public void refresh(){
		load(header.getSort(), header.getOrder());
	}
	
	public void scrollUp(){
		pane.getVerticalScrollBar().setValue(pane.getVerticalScrollBar().getMinimum());
	}
	
	public void scrollDown(){
		pane.getVerticalScrollBar().setValue(pane.getVerticalScrollBar().getMaximum());
	}
	
	public abstract List<OBJECT> getRows(SORT sort, JPSortOrder order);	
}
