package org.bigfoot.swingplus.form.components.impl;

import java.awt.event.ActionListener;

import org.bigfoot.swingplus.configurable.components.JPCheckBox;
import org.bigfoot.swingplus.form.components.JPFormComponent;

public class JPFormCheckBox extends JPCheckBox implements JPFormComponent<Boolean>{
	
	private String id;
	
	private String label;
	
	private boolean update = true;
	
	public JPFormCheckBox(String id){
		this(id, null);
	}
	
	public JPFormCheckBox(String id, String label){
		super(label);
		setId(id);
	}
	
	public JPFormCheckBox(String id, String label, ActionListener al){
		super(label, al);
		setId(id);
	}
	
	@Override
	public void onConfigure() {
		
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
	public void setComponentValue(Boolean value) {
		if(value==null){
			setSelected(false);
		} else {
			setSelected(value);
		}
	}

	@Override
	public Boolean getComponentValue() {
		return isSelected();
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
