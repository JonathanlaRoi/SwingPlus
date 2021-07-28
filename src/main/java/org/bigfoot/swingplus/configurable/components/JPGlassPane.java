package org.bigfoot.swingplus.configurable.components;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.apachecommons.CommonsLog;
import net.miginfocom.swing.MigLayout;
import org.bigfoot.swingplus.configurable.JPContainer;
import org.bigfoot.swingplus.event.JPListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseMotionAdapter;

/**
 * @author Jonathan la Roi
 * @since 01/11/2020
 */
@CommonsLog
public class JPGlassPane extends JComponent implements KeyListener, JPContainer, JPListener {
    private static final String inactiveColorId = "inactiveCaptionBorder";

    @Getter
    @Setter
    private Icon icon;

    @Getter
    @Setter
    private String message;

    private final JLabel messageLabel = new JPLabel();

    public JPGlassPane() {
        super();
        setOpaque(false);
        Color base = UIManager.getColor(inactiveColorId);
        //The color when the above color isn't available
        //Color is based on the inactive color of the "Windows" look and feel
        Color background = new Color(244, 247, 252, 128);
        if (base != null) {
            background = new Color(base.getRed(), base.getGreen(), base.getBlue(), 128);
        }
        setBackground(background);
        setLayout(new MigLayout("align center", "", ""));
        messageLabel.setOpaque(true);
        add(messageLabel);

        addMouseListener(new MouseAdapter() {
        });
        addMouseMotionListener(new MouseMotionAdapter() {
        });

        addKeyListener(this);

        setFocusTraversalKeysEnabled(false);
    }

    public void setFont(Font font) {
        messageLabel.setFont(font);
    }

    public Font getFont() {
        return messageLabel.getFont();
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(getBackground());
        g.fillRect(0, 0, getSize().width, getSize().height);
    }

    @Override
    public void setBackground(Color background) {
        super.setBackground(background);

        Color messageBackground = new Color(background.getRGB());
        messageLabel.setBackground(messageBackground);
    }

    public void keyPressed(KeyEvent e) {
        e.consume();
    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyReleased(KeyEvent e) {
        e.consume();
    }

    public void activate() {
        if (message != null && message.length() > 0 || icon != null) {
            messageLabel.setIcon(icon);
            messageLabel.setVisible(true);
            messageLabel.setText(message);
            messageLabel.setForeground(getForeground());
        } else {
            messageLabel.setVisible(false);
        }

        setVisible(true);
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        requestFocusInWindow();
    }

    public void activate(String message) {
        this.message = message;
        this.icon = null;
        activate();
    }

    public void activate(Icon icon) {
        this.message = null;
        this.icon = icon;
        activate();
    }

    public void activate(Icon icon, String message) {
        this.message = message;
        this.icon = icon;
        activate();
    }

    public void deactivate() {
        setCursor(null);
        setVisible(false);
    }
}
