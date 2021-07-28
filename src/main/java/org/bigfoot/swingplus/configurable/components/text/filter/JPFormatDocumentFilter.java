package org.bigfoot.swingplus.configurable.components.text.filter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;

import lombok.Getter;

@Deprecated
public class JPFormatDocumentFilter extends JPLimitDocumentFilter {

	@Getter
	private String regex;
	
	private Pattern pattern;

	public JPFormatDocumentFilter(Integer limit, String regex) {
		super(limit);
		setRegex(regex);
	}
	
	public void setRegex(String regex){
		this.regex = regex;
		pattern = regex != null ? Pattern.compile(regex) : null;
	}
	
	@Override
    public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
    	if(regex != null && pattern != null){
    		Matcher matcher = pattern.matcher(text);
    		if (!matcher.matches()){
    			String charsToRemove = matcher.replaceAll(regex);
    			for(int i = 0; i < charsToRemove.length(); i++){
    				text = text.replaceAll(Character.toString(charsToRemove.charAt(i)), "");
    			}
    		}
    	}
    	super.replace(fb, offset, length, text, attrs);
    }
}
