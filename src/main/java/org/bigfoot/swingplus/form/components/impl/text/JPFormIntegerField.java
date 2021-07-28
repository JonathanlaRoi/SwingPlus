package org.bigfoot.swingplus.form.components.impl.text;

import org.bigfoot.swingplus.configurable.components.text.JPFormats;

public class JPFormIntegerField extends JPFormNumberField<Integer> {
	public JPFormIntegerField(String id){
		super(id, null, false, null);
		this.setFormat(JPFormats.ONLY_NUMBERS);
	}
	
	public JPFormIntegerField(String id, Integer number){
		super(id, number, false, null);
		this.setFormat(JPFormats.ONLY_NUMBERS);
	}

	@Deprecated
	public JPFormIntegerField(String id, Integer number, Integer maxLength){
		super(id, number, maxLength!=null, maxLength);
		this.setFormat(JPFormats.ONLY_NUMBERS);
	}

	@Deprecated
	public JPFormIntegerField(String id, Integer number, boolean enforceLimit, Integer maxLength){
		super(id, number, enforceLimit && maxLength!=null, maxLength);
		this.setFormat(JPFormats.ONLY_NUMBERS);
	}

	@Override
	public Integer parse(String text) throws NumberFormatException {
		if(text != null && !text.isEmpty()){
			return Integer.parseInt(text);
		}
		return null;
	}
}
