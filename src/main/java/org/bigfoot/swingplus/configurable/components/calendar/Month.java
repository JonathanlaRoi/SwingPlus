package org.bigfoot.swingplus.configurable.components.calendar;

import java.text.DateFormatSymbols;
import java.util.Locale;

public class Month {
	private Integer month;
	
	protected Month(Integer month){
		if(month == null)
			throw new NullPointerException();
		if(month > 12 || month < 1)
			throw new IllegalArgumentException();
		setMonth(month);
	}

	public Integer getMonth() {
		return month;
	}

	public void setMonth(Integer month) {
		this.month = month;
	}
	
	@Override
	public String toString(){
		return new DateFormatSymbols(Locale.getDefault()).getMonths()[getMonth()-1];
	}
	
	@Override
	public boolean equals(Object obj){
		if(obj instanceof Month){
			Month maand = (Month) obj;
			return this.getMonth().equals(maand.getMonth());
		} else if(obj instanceof Integer){
			return this.getMonth().equals(obj);
		}
		return false;
	}
	
	protected static Month of(Integer month){
		if(month > 12){
			return new Month(12);
		} else if(month < 1){
			return new Month(1);
		}
		return new Month(month);
	}
}
