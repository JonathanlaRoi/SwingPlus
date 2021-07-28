package org.bigfoot.swingplus.form.components.impl.path;

import java.awt.Color;
import java.nio.file.Path;

import javax.swing.BorderFactory;
import javax.swing.border.Border;

import org.bigfoot.swingplus.configurable.components.path.JPFileSelectionType;
import org.bigfoot.swingplus.configurable.components.path.JPFileSelector;
import org.bigfoot.swingplus.form.components.JPFormComponent;

public class JPFormFileSelector extends JPFileSelector implements JPFormComponent<Path> {
	
	private String id;
	private String label;
	private boolean updateable = true;
	
	private Border defaultBorder = this.getBorder();
	
	public JPFormFileSelector(String id, JPFileSelectionType type){
		this(id);
		this.setFileSelectionType(type);
	}
	
	public JPFormFileSelector(String id) {
		super();
		setId(id);
	}
	
	@Override
	public String getId() {
		return id;
	}

	@Override
	public Path getComponentValue() {
		return getPath();
	}

	@Override
	public boolean isUpdatable() {
		return updateable;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	@Override
	public void setUpdatable(boolean updateable) {
		this.updateable = updateable;
	}

	@Override
	public void setComponentValue(Path path) {
		setPath(path);
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
	public void onConfigure() {
		
	}
}
