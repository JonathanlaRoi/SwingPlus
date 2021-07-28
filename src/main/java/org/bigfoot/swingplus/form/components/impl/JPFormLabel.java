package org.bigfoot.swingplus.form.components.impl;

import javax.swing.JLabel;

import org.bigfoot.swingplus.form.components.JPFormComponent;
import org.bigfoot.swingplus.form.components.JPReadOnly;

public class JPFormLabel extends JLabel implements JPFormComponent<Object>, JPReadOnly {
	private String id;
	
	private String label;
	
	private boolean update = true;
	
	public JPFormLabel(String id){
		super();
		this.setId(id);
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
	public void setComponentValue(Object obj) {
		if(obj == null){
			this.setText(null);
		} else if(obj instanceof String){
			this.setText((String) obj);
		} else {
			this.setText(obj.toString());
		}
	}

	@Override
	public String getComponentValue() {
		return this.getText();
	}

	@Override
	public void setUpdatable(boolean update) {
		this.update = update;
	}

	@Override
	public boolean isUpdatable() {
		return update;
	}

	@Override
	public void onConfigure() {
		
	}
}