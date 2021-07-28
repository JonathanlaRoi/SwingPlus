package org.bigfoot.swingplus.form.components.impl;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.AbstractButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

import org.bigfoot.swingplus.form.components.JPFormComponent;

public class JPFormCheckboxGroup<T> extends JPanel implements JPFormComponent<List<T>> {
	
	private List<AbstractButton> buttonGroup = new ArrayList<>();
	
	private String id;
	private String label;
	private boolean updatable = true;
	private List<T> values;
	private int columns = 1;
	
	public JPFormCheckboxGroup(String id){
		this(id, new ArrayList<>(), new ArrayList<>());
	}
	
	public JPFormCheckboxGroup(String id, List<T> values){
		this(id, values, new ArrayList<>());
	}
	
	@SafeVarargs
	public JPFormCheckboxGroup(String id, T... values){
		this(id, Arrays.asList(values), new ArrayList<>());
	}
	
	public JPFormCheckboxGroup(String id, List<T> values, List<T> selectedValues){
		super();
		setLayout(new MigLayout("", "[]", "[30px]"));
		setId(id);
		this.values = values;
		init();
		
		setSelectedButtonValues(buttonGroup, selectedValues);
	}
	
	public void init(){
		super.removeAll();
		int row = 0;
		int col = 0;
		
		for(T value : values){
			JPFormGroupElement btn = new JPFormGroupElement(value);
			buttonGroup.add(btn);
			this.add(btn, "cell " + col + " " + row);
			col++;
			if(col==columns){
				col=0;
				row++;
			}
		}
		
		this.repaint();
		this.revalidate();
	}
	
	public void addOption(T value){
		values.add(value);
		init();
	}
	
	public void removeOption(T value){
		values.remove(value);
		init();
	}
	
	public void enableOptions(){
		values.forEach(item -> getButtonByItem(buttonGroup, item).setEnabled(true));
	}
	
	public void disableOptions(){
		values.forEach(item -> getButtonByItem(buttonGroup, item).setEnabled(false));
	}
	
	public void enableOption(T value){
		getButtonByItem(buttonGroup, value).setEnabled(true);
	}
	
	public void disableOption(T value){
		getButtonByItem(buttonGroup, value).setEnabled(false);
	}
	
	public void removeAllOptions(){
		values.clear();
		init();
	}
	
	public List<T> getOptions(){
		return values;
	}
	
	public void addActionListener(ActionListener al){
		values.forEach(item -> getButtonByItem(buttonGroup, item).addActionListener(al));
	}
	
	public void removeActionListener(ActionListener al){
		values.forEach(item -> getButtonByItem(buttonGroup, item).removeActionListener(al));
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
	public void setComponentValue(List<T> values) {
		if(values != null){
			for(AbstractButton btn : buttonGroup){
				if(btn instanceof JPFormCheckboxGroup.JPFormGroupElement){
					btn.setSelected(false);
				}
			}
			setSelectedButtonValues(buttonGroup, values);
		}
	}

	@Override
	public List<T> getComponentValue() {
		return getSelectedButtonValues(buttonGroup);
	}

	@Override
	public void setUpdatable(boolean updatable) {
		this.updatable = updatable;
	}

	@Override
	public boolean isUpdatable() {
		return updatable;
	}

	public int getColumns() {
		return columns;
	}

	public void setColumns(int columns) {
		if(this.columns != columns){
			this.columns = columns;
			init();
		}
	}

	@Override
	public void onConfigure() {
		
	}
	
	@SuppressWarnings("unchecked")
	private void setSelectedButtonValues(List<AbstractButton> buttonGroup, List<T> values) {
        for (AbstractButton obj : buttonGroup) {
            if(obj instanceof JPFormCheckboxGroup.JPFormGroupElement){
            	JPFormGroupElement button = (JPFormGroupElement) obj;
	            if (values.contains(button.getValue())) {
	                button.setSelected(true);
	            }
            }
        }
    }
	
	@SuppressWarnings("unchecked")
	private List<T> getSelectedButtonValues(List<AbstractButton> buttonGroup) {
        List<T> selectedValues = new ArrayList<>();
		
		for (AbstractButton button : buttonGroup) {
            if(button instanceof JPFormCheckboxGroup.JPFormGroupElement){
	            if (button.isSelected()) {
	            	selectedValues.add(((JPFormGroupElement) button).getValue());
	            }
            }
        }

        return selectedValues;
    }
	
	@SuppressWarnings("unchecked")
	private JPFormGroupElement getButtonByItem(List<AbstractButton> buttonGroup, T item){
		for (AbstractButton button : buttonGroup) {
            if(button instanceof JPFormCheckboxGroup.JPFormGroupElement){
	            if (((JPFormGroupElement) button).getValue().equals(item)) {
	                return ((JPFormGroupElement) button);
	            }
            }
        }

        return null;
	}
	
	@Override
	public void setEnabled(boolean enabled){
		super.setEnabled(enabled);
		if(enabled){
			enableOptions();
		} else {
			disableOptions();
		}
	}
	
	private class JPFormGroupElement extends JCheckBox {
		private T value;
		
		public JPFormGroupElement(T value){
			super(value.toString());
			this.value = value;
		}
		
		public T getValue(){
			return value;
		}
	}
}
