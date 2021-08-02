package org.bigfoot.swingplus.form.components.impl.text;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.border.Border;

import org.bigfoot.swingplus.configurable.components.text.JPTextField;
import org.bigfoot.swingplus.form.components.JPFormComponent;

public abstract class JPFormGenericTextField<T> extends JPTextField implements JPFormComponent<T>{
	
	private String id;
	
	private String label;
	
	private boolean updatable = true;
	
	private final Border defaultBorder = this.getBorder();
	
	public JPFormGenericTextField(String id){
		super(null, null);
		setId(id);
	}

	public JPFormGenericTextField(String id, Integer maxLength){
		super(null, maxLength);
		setId(id);
	}
	
	public JPFormGenericTextField(String id, String text){
		super(text, null);
		setId(id);
	}

	public JPFormGenericTextField(String id, String text, Integer maxLength){
		super(text, maxLength);
		setId(id);
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
	public void markAsInvalid(){
		this.setBorder(BorderFactory.createLineBorder(Color.RED));
	}
	
	@Override
	public void markAsValid(){
		this.setBorder(defaultBorder);
	}

	@Override
	public void setComponentValue(T value) {
		if(value != null){
			if(value.toString().equals("")){
				setText(null);
			} else {
				setText(value.toString());
			}
		} else {
			setText(null);
		}
	}

	@Override
	public T getComponentValue() {
		return parse(getText());
	}
	
	public abstract T parse(String text);

	@Override
	public void setUpdatable(boolean updatable) {
		this.updatable = updatable;
	}

	@Override
	public boolean isUpdatable() {
		return updatable;
	}
}
