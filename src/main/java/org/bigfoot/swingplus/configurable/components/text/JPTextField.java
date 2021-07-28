package org.bigfoot.swingplus.configurable.components.text;

import lombok.Getter;
import org.bigfoot.swingplus.configurable.JPConfigurable;
import org.bigfoot.swingplus.configurable.components.text.filter.JPFormatDocumentFilter;
import org.bigfoot.swingplus.configurable.components.text.filter.JPLimitDocumentFilter;
import org.bigfoot.swingplus.eventlisteners.JPLambdaMouseClickListener;
import org.bigfoot.swingplus.eventlisteners.JPLambdaPopupMouseListener;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.text.AbstractDocument;
import javax.swing.text.DocumentFilter;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * TODO Refactor voor alle input validatie meuk
 *
 * @author Jonathan la Roi
 */
public class JPTextField extends JTextField implements JPConfigurable {

    private static final int ICON_SPACING = 4;

    @Deprecated
    private Integer limit = 50;

    @Deprecated
    private boolean enforceLimit = true;

    private boolean required = false;

    @Deprecated
    private String format, activeFormat;

    @Getter
    private ImageIcon icon;

    @Getter
    private Border border;

    public JPTextField() {
        this(null, false, null);
    }

    @Deprecated
    public JPTextField(Integer maxLength) {
        this(null, maxLength != null, maxLength);
    }

    public JPTextField(String text) {
        this(text, false, null);
    }

    @Deprecated
    public JPTextField(String text, Integer maxLength) {
        this(text, maxLength != null, maxLength);
    }

    @Deprecated
    public JPTextField(String text, boolean enforceLimit, Integer maxLength) {
        super(text);
        setMaxLength(maxLength);
        setEnforceLimit(enforceLimit && maxLength != null);
        this.border = getBorder();
    }

    public void setEnforceLimit(boolean b) {
        this.enforceLimit = b && getMaxLength() != null;
        setMaxLength(getMaxLength());
    }

    public void setMaxLength(Integer i) {
        this.limit = i;
        if (this.enforceLimit && limit != null && this.limit < this.getText().length()) {
            this.setText(this.getText().substring(0, i));
        }
        JPLimitDocumentFilter documentFilter;
        if (activeFormat == null) {
            documentFilter = new JPLimitDocumentFilter(limit);
        } else {
            documentFilter = new JPFormatDocumentFilter(limit, activeFormat);
        }
        documentFilter.setEnforceLimit(enforceLimit);
        ((AbstractDocument) this.getDocument()).setDocumentFilter(documentFilter);
    }

    public Integer getMaxLength() {
        return this.limit;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getActiveFormat() {
        return format;
    }

    public void setActiveFormat(String activeFormat) {
        this.activeFormat = activeFormat;
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

    @Override
    public void onConfigure() {

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

    //Icon shizzles
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
