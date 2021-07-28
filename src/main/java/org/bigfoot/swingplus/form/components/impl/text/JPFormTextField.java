package org.bigfoot.swingplus.form.components.impl.text;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.border.Border;

import org.bigfoot.swingplus.configurable.components.text.JPTextField;
import org.bigfoot.swingplus.form.components.JPFormComponent;

public class JPFormTextField extends JPTextField implements JPFormComponent<String>{
	
	private String id;
	
	private String label;
	
	private boolean update = true;
	
	private Border defaultBorder = this.getBorder();
	
	public JPFormTextField(String id){
		super(null, false, null);
		setId(id);
	}

	@Deprecated
	public JPFormTextField(String id, Integer maxLength){
		super(null, maxLength!=null, maxLength);
		setId(id);
	}
	
	public JPFormTextField(String id, String text){
		super(text, false, null);
		setId(id);
	}

	@Deprecated
	public JPFormTextField(String id, String text, Integer maxLength){
		super(text, maxLength!=null, maxLength);
		setId(id);
	}

	@Deprecated
	public JPFormTextField(String id, String text, boolean enforceLimit, Integer maxLength){
		super(text, enforceLimit && maxLength!=null, maxLength);
		setId(id);
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
	public void markAsInvalid(){
		this.setBorder(BorderFactory.createLineBorder(Color.RED));
	}
	
	@Override
	public void markAsValid(){
		this.setBorder(defaultBorder);
	}

	@Override
	public void setComponentValue(String value) {
		if(value != null && value.equals("")){
			setText(null);
		}
		setText(value);
	}

	@Override
	public String getComponentValue() {
		return getText();
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
