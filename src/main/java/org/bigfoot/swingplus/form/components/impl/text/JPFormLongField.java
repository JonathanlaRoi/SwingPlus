package org.bigfoot.swingplus.form.components.impl.text;

import org.bigfoot.swingplus.configurable.components.text.JPFormats;

public class JPFormLongField extends JPFormNumberField<Long> {
	public JPFormLongField(String id){
		super(id, null, false, null);
		this.setFormat(JPFormats.ONLY_NUMBERS);
	}

	@Deprecated
	public JPFormLongField(String id, Integer maxLength){
		super(id, null, maxLength!=null, maxLength);
		this.setFormat(JPFormats.ONLY_NUMBERS);
	}
	
	public JPFormLongField(String id, Long number){
		super(id, number, false, null);
		this.setFormat(JPFormats.ONLY_NUMBERS);
	}

	@Deprecated
	public JPFormLongField(String id, Long number, Integer maxLength){
		super(id, number, maxLength!=null, maxLength);
		this.setFormat(JPFormats.ONLY_NUMBERS);
	}

	@Deprecated
	public JPFormLongField(String id, Long number, boolean enforceLimit, Integer maxLength){
		super(id, number, enforceLimit && maxLength!=null, maxLength);
		this.setFormat(JPFormats.ONLY_NUMBERS);
	}

	@Override
	public Long parse(String text) throws NumberFormatException {
		if(text != null && !text.isEmpty()){
			return Long.parseLong(text);
		}
		return new Long(0);
	}
}
