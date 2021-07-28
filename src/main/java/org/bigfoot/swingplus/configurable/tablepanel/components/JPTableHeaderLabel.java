package org.bigfoot.swingplus.configurable.tablepanel.components;

import javax.swing.Icon;
import javax.swing.JLabel;

import lombok.Getter;

import org.bigfoot.swingplus.configurable.tablepanel.objects.JPSortOrder;
import org.bigfoot.swingplus.eventlisteners.JPLambdaMouseClickListener;

public abstract class JPTableHeaderLabel<SORT> extends JLabel {
	
	@Getter
	private final SORT sort;
	
	@Getter
	private JPSortOrder order;
	
	public JPTableHeaderLabel(String label, SORT sort){
		this(label, sort, null);
	}
	
	public JPTableHeaderLabel(String label, SORT sort, JPSortOrder order){
		super();
		this.sort = sort;
		setText("<html><b>" + label + "</b></html>");
		
		if(this.sort != null){
			addMouseListener((JPLambdaMouseClickListener) event -> internalOnSort());
		}
	}
	
	public void setOrder(JPSortOrder order){
		this.order = order;
		if(JPSortOrder.ASC.equals(order)){
			setIcon(getAscIcon());
		} else if (JPSortOrder.DESC.equals(order)){
			setIcon(getDescIcon());
		} else {
			setIcon(null);
		}
	}

	private void internalOnSort() {
		setOrder(order == null || order.equals(JPSortOrder.DESC) ? JPSortOrder.ASC : JPSortOrder.DESC);
		onSort();
	}
	
	public void onSort() {
		
	}
	
	public abstract Icon getAscIcon();
	
	public abstract Icon getDescIcon();
}
