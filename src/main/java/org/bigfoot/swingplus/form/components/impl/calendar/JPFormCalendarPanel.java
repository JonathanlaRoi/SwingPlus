package org.bigfoot.swingplus.form.components.impl.calendar;

import java.time.LocalDate;

import org.bigfoot.swingplus.configurable.components.calendar.JPCalendarPanel;
import org.bigfoot.swingplus.form.components.JPFormComponent;

public class JPFormCalendarPanel extends JPCalendarPanel implements JPFormComponent<LocalDate>{
	
	private String id;
	
	private String label;
	
	private boolean updatable = true;
	
	public JPFormCalendarPanel(String id){
		this(id, LocalDate.now());
	}
	
	public JPFormCalendarPanel(String id, LocalDate date){
		this(id, date, date.plusYears(100), date.minusYears(100));
	}
	
	public JPFormCalendarPanel(String id, LocalDate date, LocalDate max, LocalDate min) {
		super(date, max, min);
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
	public void setUpdatable(boolean updatable) {
		this.updatable = updatable;
	}

	@Override
	public boolean isUpdatable() {
		return updatable;
	}

	@Override
	public void setComponentValue(LocalDate value) {
		super.setDate(value);
	}

	@Override
	public LocalDate getComponentValue() {
		return super.getDate();
	}
}
