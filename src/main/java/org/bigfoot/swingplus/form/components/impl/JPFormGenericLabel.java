package org.bigfoot.swingplus.form.components.impl;

import javax.swing.JLabel;

import org.bigfoot.swingplus.form.components.JPFormComponent;

public class JPFormGenericLabel<T> extends JLabel implements JPFormComponent<T> {
	
	private String id;
	
	private String label;
	
	private boolean updatable = true;
	
	private T value;
	
	public JPFormGenericLabel(String id){
		this.id = id;
	}
	
	@Override
	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String getId() {
		return id;
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
		this.value = value;
		if(value == null){
			this.setText(null);
		} else if(value instanceof String){
			this.setText((String) value);
		} else {
			this.setText(value.toString());
		}
	}

	@Override
	public T getComponentValue() {
		return value;
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
	public void onConfigure() {
		
	}
}
