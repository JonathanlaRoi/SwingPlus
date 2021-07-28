package org.bigfoot.swingplus.form.components.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bigfoot.swingplus.configurable.components.JPComboBox;
import org.bigfoot.swingplus.form.components.JPFormComponent;

public class JPFormComboBox<T> extends JPComboBox<T> implements JPFormComponent<T>{
	private String id;
	
	private String label;
	
	private boolean update = true;
	
	public JPFormComboBox(String id){
		this(id, new ArrayList<>());
	}
	
	@SafeVarargs
	public JPFormComboBox(String id, T... values){
		this(id, Arrays.asList(values));
	}
	
	public JPFormComboBox(String id, List<T> values){
		super();
		setId(id);
		values.forEach(item -> super.addItem(item));
	}
	
	public void setValues(List<T> values){
		if(values == null){
			throw new NullPointerException();
		}
		T selectedItem = getSelectedTypedItem();
		removeAllItems();
		values.forEach(c -> addItem(c));
		setSelectedTypedItem(selectedItem);
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}
	
	@Override
	public void setValidationLabel(String label){
		this.label = label;
	}
	
	@Override
	public String getValidationLabel(){
		return label;
	}

	@Override
	public void setComponentValue(T value) {
		setSelectedTypedItem(value);
	}

	@Override
	public T getComponentValue() {
		return getSelectedTypedItem();
	}

	@Override
	public void setUpdatable(boolean update) {
		this.update = update;
	}

	@Override
	public boolean isUpdatable() {
		return update;
	}
}
