package org.bigfoot.swingplus.configurable.components.calendar;

import java.time.LocalDate;

import javax.swing.Icon;

import lombok.Getter;
import lombok.Setter;

import org.bigfoot.swingplus.configurable.components.JPButton;

class JPCalendarButton extends JPButton {
	
	private LocalDate date;

	@Getter
	@Setter
	private Icon nextIcon, previousIcon;
	
	protected JPCalendarButton(String arg0){
		this(arg0, null);
	}
	
	protected JPCalendarButton(String arg0, LocalDate date){
		super(arg0);
		setDate(date);
		setSelected(false);
	}
	
	public void setSelected(boolean selected){
		setContentAreaFilled(selected);
		setBorderPainted(selected);
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}
}
