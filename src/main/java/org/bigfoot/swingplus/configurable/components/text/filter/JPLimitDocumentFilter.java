package org.bigfoot.swingplus.configurable.components.text.filter;

import lombok.extern.apachecommons.CommonsLog;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

@CommonsLog
public class JPLimitDocumentFilter extends DocumentFilter {
    private final Integer limit;

    private boolean enforceLimit = true;

    public JPLimitDocumentFilter(Integer limit) {
        super();
        this.limit = limit;
    }

    public void setEnforceLimit(boolean enforceLimit) {
        this.enforceLimit = enforceLimit && limit != null;
    }

    public boolean isEnforceLimit() {
        return enforceLimit;
    }

    @Override
    public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
        if (limit != null && limit >= 0) {
            if (text != null) {
                int currentLength = fb.getDocument().getLength();
                int overLimit = (currentLength + text.length()) - limit - length;
                if (isEnforceLimit() && overLimit > 0) {
                    text = text.substring(0, text.length() - overLimit);
                }
                if (text.length() > 0) {
                    super.replace(fb, offset, length, text, attrs);
                }
            } else {
                int currentLength = fb.getDocument().getLength();
                super.remove(fb, 0, currentLength);
            }
        } else {
            super.replace(fb, offset, length, text, attrs);
        }
    }
}
