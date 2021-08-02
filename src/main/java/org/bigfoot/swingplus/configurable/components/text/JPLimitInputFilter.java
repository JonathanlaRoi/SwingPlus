package org.bigfoot.swingplus.configurable.components.text;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class JPLimitInputFilter extends JPInputFilter {
	
	private final int maxLength;
	
	@Override
	public boolean validate(String text) {
		return text == null || maxLength > 0 && text.length() <= maxLength;
	}

}
