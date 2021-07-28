package org.bigfoot.swingplus.configurable.components.calendar;

public class Year {
	private Integer year;
	
	private String placeholder;
	
	protected Year(Integer year){
		if(year == null)
			throw new NullPointerException();
		setYear(year);
	}
	
	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		if(year == null)
			throw new NullPointerException();
		this.year = year;
	}

	public String getPlaceholder() {
		return placeholder;
	}

	public void setPlaceholder(String placeholder) {
		this.placeholder = placeholder;
	}
	
	@Override
	public String toString(){
		if(getPlaceholder() != null){
			return getPlaceholder() + " " + getYear();
		}
		return getYear().toString();
	}
	
	@Override
	public boolean equals(Object obj){
		if(obj instanceof Year){
			Year jaar = (Year) obj;
			return this.getYear().equals(jaar.getYear());
		} else if(obj instanceof Long){
			return this.getYear().equals((Long) obj);
		}
		return false;
	}
	
	public static Year of(Integer year){
		return new Year(year);
	}
	
	protected static Year of(Integer year, String placeholder){
		Year obj = of(year);
		obj.setPlaceholder(placeholder);
		return obj;
	}
}
