package org.bigfoot.swingplus.configurable.components.text;

import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.text.AbstractDocument;
import javax.swing.text.DocumentFilter;

import org.bigfoot.swingplus.configurable.JPConfigurable;
import org.bigfoot.swingplus.configurable.components.text.filter.JPFormatDocumentFilter;
import org.bigfoot.swingplus.configurable.components.text.filter.JPLimitDocumentFilter;
import org.bigfoot.swingplus.eventlisteners.JPLambdaMouseClickListener;
import org.bigfoot.swingplus.eventlisteners.JPLambdaPopupMouseListener;

import lombok.Getter;

/**
 * TODO Refactor voor alle input validatie meuk
 *
 * @author Jonathan la Roi
 */
public class JPTextField extends JTextField implements JPConfigurable {

    private static final int ICON_SPACING = 4;

    private boolean required = false;

    //TODO vervangen
    @Deprecated
    private String format;

    @Getter
    private ImageIcon icon;

    @Getter
    private Border border;

    public JPTextField() {
        this(null, null);
    }

    @Deprecated
    public JPTextField(Integer maxLength) {
        this(null, maxLength);
    }

    public JPTextField(String text) {
        this(text, null);
    }

    @Deprecated
    public JPTextField(String text, Integer maxLength) {
        super(text);
        setMaxLength(maxLength);
        this.border = getBorder();
    }

    @Deprecated
    public void setMaxLength(Integer limit) {
        if (limit != null) {
            if (limit < this.getText().length()) {
                this.setText(this.getText().substring(0, limit));
            }
            JPLimitDocumentFilter documentFilter;
            if (format == null || format.isEmpty()) {
                documentFilter = new JPLimitDocumentFilter(limit);
            } else {
                documentFilter = new JPFormatDocumentFilter(limit, format);
            }
            ((AbstractDocument) this.getDocument()).setDocumentFilter(documentFilter);
        } else if (format != null) {
            ((AbstractDocument) this.getDocument()).setDocumentFilter(new JPFormatDocumentFilter(null, format));
        } else {
            ((AbstractDocument) this.getDocument()).setDocumentFilter(null);
        }
    }

    @Deprecated
    public Integer getMaxLength() {
        AbstractDocument document = ((AbstractDocument) this.getDocument());
        if (document.getDocumentFilter() instanceof JPLimitDocumentFilter) {
            return ((JPLimitDocumentFilter) document.getDocumentFilter()).getLimit();
        }
        return null;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    @Deprecated
    public String getFormat() {
        return format;
    }

    @Deprecated
    public void setFormat(String format) {
        this.format = format;
        setMaxLength(getMaxLength());
    }

    public JPTextField addDocumentListener(JPDocumentListener documentListener) {
        getDocument().addDocumentListener(documentListener);
        return this;
    }

    public JPTextField addAction(ActionListener action) {
        super.addActionListener(action);
        return this;
    }

    public JPTextField addMouseListener(JPLambdaPopupMouseListener adapter) {
        super.addMouseListener(adapter);
        return this;
    }

    public JPTextField addMouseListener(JPLambdaMouseClickListener adapter) {
        super.addMouseListener(adapter);
        return this;
    }

    public JPTextField setDocumentFilter(DocumentFilter documentFilter) {
        ((AbstractDocument) this.getDocument()).setDocumentFilter(documentFilter);
        return this;
    }

    //TODO what is this shit
    @Override
    public void setBorder(Border border) {
        this.border = border;

        if (icon == null || this.border == null) {
            super.setBorder(this.border);
        } else {
            Border margin = BorderFactory.createEmptyBorder(0, icon.getIconWidth() + ICON_SPACING, 0, 0);
            Border compound = BorderFactory.createCompoundBorder(this.border, margin);
            super.setBorder(compound);
        }
    }

    //TODO what is this shit
    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        if (icon != null && border != null) {
            Insets iconInsets = border.getBorderInsets(this);
            icon.paintIcon(this, graphics, iconInsets.left, iconInsets.top);
        }
    }

    public void setIcon(ImageIcon icon) {
        this.icon = icon;
        resetBorder();
        repaint();
        revalidate();
    }

    private void resetBorder() {
        setBorder(border);
    }
}
