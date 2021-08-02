package org.bigfoot.swingplus.form.components.impl.text;

import org.bigfoot.swingplus.configurable.components.text.JPFormats;

public class JPFormIntegerField extends JPFormNumberField<Integer> {
	public JPFormIntegerField(String id){
		super(id, null, null);
		this.setFormat(JPFormats.ONLY_NUMBERS);
	}
	
	public JPFormIntegerField(String id, Integer number){
		super(id, number, null);
		this.setFormat(JPFormats.ONLY_NUMBERS);
	}

	public JPFormIntegerField(String id, Integer number, Integer maxLength){
		super(id, number, maxLength);
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
