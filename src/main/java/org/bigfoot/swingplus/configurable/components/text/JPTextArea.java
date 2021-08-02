package org.bigfoot.swingplus.configurable.components.text;

import org.bigfoot.swingplus.configurable.JPConfigurable;
import org.bigfoot.swingplus.configurable.components.text.filter.JPLimitDocumentFilter;
import org.bigfoot.swingplus.eventlisteners.JPLambdaMouseClickListener;
import org.bigfoot.swingplus.eventlisteners.JPLambdaPopupMouseListener;

import javax.swing.*;
import javax.swing.text.AbstractDocument;
import javax.swing.text.DocumentFilter;

public class JPTextArea extends JTextArea implements JPConfigurable {

    public JPTextArea() {
        this(null, null);
    }

    public JPTextArea(Integer maxLength) {
        this(null, maxLength);
    }

    public JPTextArea(String text) {
        this(text, null);
    }

    @Deprecated
    public JPTextArea(String text, Integer maxLength) {
        super(text);
        setLineWrap(true);
        setWrapStyleWord(true);
        setMaxLength(maxLength);
    }

    public void setMaxLength(Integer limit) {
        if (limit != null) {
            if (limit < this.getText().length()) {
                this.setText(this.getText().substring(0, limit));
            }
            ((AbstractDocument) this.getDocument()).setDocumentFilter(new JPLimitDocumentFilter(limit));
        } else {
            ((AbstractDocument) this.getDocument()).setDocumentFilter(null);
        }
    }

    public Integer getMaxLength() {
        AbstractDocument document = ((AbstractDocument) this.getDocument());
        if (document.getDocumentFilter() instanceof JPLimitDocumentFilter) {
            return ((JPLimitDocumentFilter) document.getDocumentFilter()).getLimit();
        }
        return null;
    }

    public JPTextArea addMouseListener(JPLambdaPopupMouseListener adapter) {
        super.addMouseListener(adapter);
        return this;
    }

    public JPTextArea addMouseListener(JPLambdaMouseClickListener adapter) {
        super.addMouseListener(adapter);
        return this;
    }

    public JPTextArea setDocumentFilter(DocumentFilter documentFilter) {
        ((AbstractDocument) this.getDocument()).setDocumentFilter(documentFilter);
        return this;
    }
}
