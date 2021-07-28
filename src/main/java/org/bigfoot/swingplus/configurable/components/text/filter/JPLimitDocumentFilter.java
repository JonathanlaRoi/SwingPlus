package org.bigfoot.swingplus.configurable.components.text.filter;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

public class JPLimitDocumentFilter extends DocumentFilter {
	private Integer limit;

	private boolean enforceLimit = true;

	public JPLimitDocumentFilter(Integer limit) {
        this.limit = limit;
    }

	public void setEnforceLimit(boolean enforceLimit){
    	this.enforceLimit = enforceLimit && limit != null;
    }

	public boolean isEnforceLimit(){
    	return enforceLimit;
    }

    @Override
    public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
    	if(limit != null && limit >= 0){
	    	if(text!=null){
	        	int currentLength = fb.getDocument().getLength();
	            int overLimit = (currentLength + text.length()) - limit - length;
	            if (isEnforceLimit() && overLimit > 0) {
	                text = text.substring(0, text.length() - overLimit);
	            }
	            try {
		            if (text.length() > 0) {
		                super.replace(fb, offset, length, text, attrs); 
		            }
	            } catch(NullPointerException ex){
	            	//TODO Fix this
	            }
	    	} else {
	    		int currentLength = fb.getDocument().getLength();
	    		super.remove(fb, 0, currentLength);
	    	}
    	} else {
    		super.replace(fb, offset, length, text, attrs);
    	}
    }
    
    public String getFullReplaceText(FilterBypass fb, int offset, int length, String text){
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
}
