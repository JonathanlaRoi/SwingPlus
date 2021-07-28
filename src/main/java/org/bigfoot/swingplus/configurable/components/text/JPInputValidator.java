package org.bigfoot.swingplus.configurable.components.text;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

public abstract class JPInputValidator extends DocumentFilter {

	@Override
    public final void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
		if(validate(getFullText(fb, offset, length, text))){
			super.replace(fb, offset, length, text, attrs);
		}
	}
	
	private String getFullText(FilterBypass fb, int offset, int length, String text){
    	String over = null;
		try {
			over = "";
			if(offset == 0 && length == 0){
				over += text + fb.getDocument().getText(0, fb.getDocument().getLength());
			} else if(length == 0){
				over += fb.getDocument().getText(0, offset) + text + fb.getDocument().getText(offset, fb.getDocument().getLength() - offset);
			} else if(offset == 0){
				over += text + fb.getDocument().getText(length, fb.getDocument().getLength() - length);
			} else {
				over += fb.getDocument().getText(0, offset) + text + fb.getDocument().getText(offset + length, fb.getDocument().getLength() - offset - length);
			}
		} catch (BadLocationException | RuntimeException e) {
			over = null;
		}
		return over;
    }
	
	public abstract boolean validate(String text);
}
