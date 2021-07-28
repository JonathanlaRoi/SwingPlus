package org.bigfoot.swingplus.form;

import java.awt.BorderLayout;
import java.awt.Component;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import net.miginfocom.swing.MigLayout;

import org.bigfoot.swingplus.configurable.JPModeledPanel;
import org.bigfoot.swingplus.form.components.JPFormComponent;

public abstract class JPFormListView<T> extends JPModeledPanel<List<T>> implements JPFormComponent<List<T>>{
	
	private String id;
	
	private boolean updatable = true;
	
	private List<JPFormComponent<T>> items = new ArrayList<>();
	
	public JPFormListView(String id) {
		super(new ArrayList<>());
		setId(id);
	}

	@Override
	public void setId(String id) {
		this.id = id;
		setLayout(new BorderLayout());
		init();
	}
	
	public void init(){
		this.removeAll();
		
		int row = 0;
		JPanel contentPanel = new JPanel();
		contentPanel.setLayout(new MigLayout("", "[grow]", "[]"));
		
		for(JPFormComponent<T> item : items){
			if(item instanceof Component){
				contentPanel.add((Component) item, "cell 0 " + row + ", growx");
				row++;
			}
		}
		JScrollPane pane = new JScrollPane(contentPanel);
		add(pane, BorderLayout.CENTER);
		
		this.repaint();
		this.revalidate();
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setUpdatable(boolean updatable) {
		this.updatable = updatable;
	}

	@Override
	public boolean isUpdatable() {
		return updatable;
	}
	
	@Override
	public void setEnabled(boolean enabled){
		super.setEnabled(enabled);
		items.forEach(i -> {
			if(i instanceof Component){
				((Component) i).setEnabled(enabled);
			}
		});
	}
	
	public void addItem(JPFormComponent<T> item){
		items.add(item);
		init();
	}
	
	public void addItem(T item){
		items.add(getComponent(item));
		init();
	}
	
	public void removeItem(JPFormComponent<T> item){
		items.remove(item);
		init();
	}
	
	public void removeItem(T item){
		JPFormComponent<T> comp = null;
		for(JPFormComponent<T> temp : items){
			if(temp.getComponentValue().equals(item)){
				comp = temp;
			}
		}
		
		items.remove(comp);
		init();
	}
	
	public void clear(){
		items.clear();
		init();
	}

	@Override
	public void setComponentValue(List<T> values) {
		if(values == null)
			values = new ArrayList<>();
		items.clear();
		for(T value : values){
			JPFormComponent<T> comp = getComponent(value);
			comp.setComponentValue(value);
			items.add(comp);
		}
		init();
	}

	@Override
	public List<T> getComponentValue() {
		updateModel();
		return getModel();
	}
	
	public void updateModel(){
		List<T> values = new ArrayList<>();
		for(JPFormComponent<T> item : items){
			values.add(item.getComponentValue());
		}
		setModel(values);
	}
	
	public void updateForm(){
		items.clear();
		List<T> values = getModel();
		for(T value : values){
			JPFormComponent<T> comp = getComponent(value);
			comp.setComponentValue(value);
			items.add(comp);
		}
		init();
	}
	
	public abstract JPFormComponent<T> getComponent(T item);
}
