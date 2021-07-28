package org.bigfoot.swingplus.configurable.tablepanel.components;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.Box;
import javax.swing.Icon;

import lombok.Getter;

import org.bigfoot.swingplus.configurable.JPToolBar;
import org.bigfoot.swingplus.configurable.components.JPButton;
import org.bigfoot.swingplus.configurable.components.JPLabel;
import org.bigfoot.swingplus.configurable.tablepanel.objects.JPSortOrder;
import org.bigfoot.swingplus.configurable.tablepanel.objects.JPTablePanelColumn;

public abstract class JPPageableTablePanel<OBJECT, SORT> extends JPTablePanel<OBJECT, SORT>{
	
	private final JPPageable pageable;

	@Getter
	private Icon firstPageIcon;

	@Getter
	private Icon lastPageIcon;

	@Getter
	private Icon previousPageIcon;

	@Getter
	private Icon nextPageIcon;
	
	private JPToolBar toolbar;
	
	public JPPageableTablePanel(List<JPTablePanelColumn<OBJECT, ?, SORT>> columns, int maxRowsPerPage) {
		super(columns);
		this.pageable = new JPPageable(maxRowsPerPage);
		
		add(toolbar = getPageToolBar(), BorderLayout.SOUTH);
	}
	
	private JPToolBar getPageToolBar(){
		JPToolBar tb = new JPToolBar();
		tb.setFloatable(false);
		
		JPLabel pageLbl = new JPLabel(pageable.getPage() + " / " + pageable.getMaxPage()){
			@Override
			public void onConfigure(){
				setText(pageable.getPage() + " / " + pageable.getMaxPage());
			}
		};
		tb.add(pageLbl);
		
		tb.add(Box.createHorizontalGlue());
		
		JPButton first = new JPButton("|<<", event -> {
			pageable.setPage(1);
			refresh();
		}){
			@Override
			public void onConfigure(){
				setEnabled(pageable.getPage() > 1);
				if(firstPageIcon != null){
					setIcon(firstPageIcon);
					setText(null);
				} else {
					setIcon(null);
					setText("|<<");
				}
			}
		};
		tb.add(first);
		
		JPButton previous = new JPButton("<<", event -> {
			pageable.setPage(pageable.getPage()-1);
			refresh();
		}){
			@Override
			public void onConfigure(){
				setEnabled(pageable.getPage() > 1);
				if(previousPageIcon != null){
					setIcon(previousPageIcon);
					setText(null);
				} else {
					setIcon(null);
					setText("<<");
				}
			}
		};
		tb.add(previous);

		JPButton next = new JPButton(">>", event -> {
			pageable.setPage(pageable.getPage()+1);
			refresh();
		}){
			@Override
			public void onConfigure(){
				setEnabled(pageable.getPage() < pageable.getMaxPage());
				if(nextPageIcon != null){
					setIcon(nextPageIcon);
					setText(null);
				} else {
					setIcon(null);
					setText(">>");
				}
			}
		};
		tb.add(next);
		
		JPButton last = new JPButton(">>|", event -> {
			pageable.setPage(pageable.getMaxPage());
			refresh();
		}){
			@Override
			public void onConfigure(){
				setEnabled(pageable.getPage() < pageable.getMaxPage());
				if(lastPageIcon != null){
					setIcon(lastPageIcon);
					setText(null);
				} else {
					setIcon(null);
					setText(">>|");
				}
			}
		};
		tb.add(last);
		
		return tb;
	}
	
	public void setLastPageIcon(Icon lastPageIcon){
		this.lastPageIcon = lastPageIcon;
		toolbar.onConfigure();
	}
	
	public void setFirstPageIcon(Icon firstPageIcon){
		this.firstPageIcon = firstPageIcon;
		toolbar.onConfigure();
	}
	
	public void setNextPageIcon(Icon nextPageIcon){
		this.nextPageIcon = nextPageIcon;
		toolbar.onConfigure();
	}
	
	public void setPreviousPageIcon(Icon previousPageIcon){
		this.previousPageIcon = previousPageIcon;
		toolbar.onConfigure();
	}
	
	@Override
	public void refresh(){
		pageable.setTotal(count());
		pageable.setPage(Math.min(pageable.getPage(), pageable.getMaxPage()));
		super.refresh();
		toolbar.onConfigure();
	}

	@Override
	public List<OBJECT> getRows(SORT sort, JPSortOrder order) {
		return getRows(sort, order, pageable);
	}
	
	public abstract List<OBJECT> getRows(SORT sort, JPSortOrder order, JPPageable pageable);
	
	public abstract int count();

}
