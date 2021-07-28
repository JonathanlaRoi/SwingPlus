package org.bigfoot.swingplus.form.components.impl.text;

import org.bigfoot.swingplus.configurable.components.text.JPFormats;

public abstract class JPFormNumberField<T extends Number> extends JPFormGenericTextField<T> {
	public JPFormNumberField(String id){
		super(id, null, false, null);
		this.setFormat(JPFormats.DECIMAL_NUMBER);
	}

	@Deprecated
	public JPFormNumberField(String id, Integer maxLength){
		super(id, null, maxLength!=null, maxLength);
		this.setFormat(JPFormats.DECIMAL_NUMBER);
	}
	
	public JPFormNumberField(String id, T number){
		super(id, number != null ? number.toString() : null, false, null);
		this.setFormat(JPFormats.DECIMAL_NUMBER);
	}

	@Deprecated
	public JPFormNumberField(String id, T number, Integer maxLength){
		super(id, number != null ? number.toString() : null, maxLength!=null, maxLength);
		this.setFormat(JPFormats.DECIMAL_NUMBER);
	}

	@Deprecated
	public JPFormNumberField(String id, T number, boolean enforceLimit, Integer maxLength){
		super(id, number != null ? number.toString() : null, enforceLimit && maxLength!=null, maxLength);
		this.setFormat(JPFormats.DECIMAL_NUMBER);
	}
}
