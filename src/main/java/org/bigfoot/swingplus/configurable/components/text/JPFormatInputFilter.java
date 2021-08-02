package org.bigfoot.swingplus.configurable.components.text;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JPFormatInputFilter extends JPLimitInputFilter {
	
	private final Pattern pattern;
	
	public JPFormatInputFilter(String regex) {
		this(0, regex);
	}
	
	public JPFormatInputFilter(int maxLength, String regex) {
		super(maxLength);
		pattern = regex != null ? Pattern.compile(regex) : null;
	}
	
	public boolean validate(String text){
		if(pattern == null){
			return super.validate(text);
		} else {
			Matcher matcher = pattern.matcher(text);
			return super.validate(text) && matcher.matches();
		}
	}
	
}
