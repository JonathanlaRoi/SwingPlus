package org.bigfoot.swingplus.notifications.component;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.extern.apachecommons.CommonsLog;
import net.miginfocom.swing.MigLayout;
import org.bigfoot.swingplus.configurable.JPWindow;
import org.bigfoot.swingplus.util.JPAudioUtils;
import org.bigfoot.swingplus.util.JPGuiUtils;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.URL;

/**
 * @author Jonathan la Roi
 * @since 17 juni 2019
 */
@CommonsLog
public class JPNotification extends JPWindow {

    private int positionIndex = 0;

    private Position position = Position.BOTTOM_RIGHT;

    private GraphicsConfiguration graphicsConfig;

    private int timeoutAutoHide = 0;

    private String pathToAudioResource;

    @Getter
    @Setter
    @Accessors(chain = true)
    private int buffer = 0;

    private final JPanel panel;

    private final JLabel titleLabel, descriptionLbl;

    private final JButton closeBtn;

    private final boolean dynamicHeight;

    private static final int DEFAULT_HEIGHT = 100;

    private static final int DEFAULT_WIDTH = 300;

    public JPNotification(String description) {
        this(null, description, null);
    }

    public JPNotification(String title, String description) {
        this(title, description, null);
    }

    public JPNotification(String title, String description, Icon icon) {
        this(title, description, icon, false);
    }

    public JPNotification(String title, String description, Icon icon, boolean dynamicHeight) {
        super();
        graphicsConfig = this.getGraphicsConfiguration();
        this.dynamicHeight = dynamicHeight;
        this.setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        this.setFocusableWindowState(false);

        panel = new JPanel();
        panel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
        getContentPane().add(panel, BorderLayout.CENTER);
        panel.setLayout(new MigLayout("", "[grow][]", "[25px][grow]"));

        titleLabel = new JLabel(title);
        titleLabel.setFont(new Font(titleLabel.getFont().getName(), Font.BOLD, titleLabel.getFont().getSize()));
        titleLabel.setIcon(icon);
        panel.add(titleLabel, "cell 0 0");

        closeBtn = new JButton("X");
        closeBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        closeBtn.setMargin(new Insets(1, 4, 1, 4));
        closeBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JPNotification.this.dispose();
            }
        });
        panel.add(closeBtn, "cell 1 0");

        descriptionLbl = new JLabel(description);
        JScrollPane sp = new JScrollPane(descriptionLbl);
        sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        sp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        sp.getViewport().addChangeListener(e -> {
            if (sp.getVerticalScrollBar().isVisible()) {
                sp.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
            } else {
                sp.setBorder(null);
            }
        });
        panel.add(sp, "cell 0 1 2 1, grow");
    }

    public JPNotification setPlaceNotificationAlwaysOnTop(boolean placeNotificationAlwaysOnTop) {
        this.setAlwaysOnTop(placeNotificationAlwaysOnTop);
        return this;
    }

    public JPNotification showNotification(Integer heightOfNotificaties) {
        if (panel.getPreferredSize().width > DEFAULT_WIDTH) {
            this.setSize(new Dimension(panel.getPreferredSize().width,
                    dynamicHeight ? Math.max(DEFAULT_HEIGHT, panel.getPreferredSize().height) : DEFAULT_HEIGHT));
        }
        setPositionOfNotification(heightOfNotificaties);
        this.setFocusableWindowState(false);
        setVisible(true);
        if (timeoutAutoHide > 0) {
            new SwingWorker<Void, Void>() {
                @Override
                protected Void doInBackground() throws Exception {
                    Thread.sleep(timeoutAutoHide * 1000);
                    return null;
                }

                @Override
                protected void done() {
                    JPGuiUtils.fadeOut(JPNotification.this);
                }
            }.execute();
        }

        return this;
    }

    private void setPositionOfNotification(Integer heightOfNotificaties) {
        int heightCorrection = heightOfNotificaties != null ? heightOfNotificaties : this.positionIndex * DEFAULT_HEIGHT;
        Insets insets = Toolkit.getDefaultToolkit().getScreenInsets(graphicsConfig);

        int notificatieBreedte = this.getWidth();

        switch (position) {
            case TOP_CENTER:
                setLocation(graphicsConfig.getBounds().x + (graphicsConfig.getDevice().getDisplayMode().getWidth() / 2) - (notificatieBreedte / 2)
                        , heightCorrection + insets.top + buffer);
                break;
            case TOP_LEFT:
                setLocation(graphicsConfig.getBounds().x,
                        heightCorrection + insets.top + buffer);
                break;
            case TOP_RIGHT:
                setLocation(graphicsConfig.getBounds().x + graphicsConfig.getDevice().getDisplayMode().getWidth() - notificatieBreedte
                        , heightCorrection + insets.top + buffer);
                break;
            case BOTTOM_CENTER:
                setLocation(graphicsConfig.getBounds().x + (graphicsConfig.getDevice().getDisplayMode().getWidth() / 2) - (notificatieBreedte / 2)
                        , graphicsConfig.getDevice().getDisplayMode().getHeight() - getHeight() - heightCorrection - insets.bottom - buffer);
                break;
            case BOTTOM_LEFT:
                setLocation(graphicsConfig.getBounds().x
                        , graphicsConfig.getDevice().getDisplayMode().getHeight() - getHeight() - heightCorrection - insets.bottom - buffer);
                break;
            case BOTTOM_RIGHT:
                setLocation(graphicsConfig.getBounds().x + graphicsConfig.getDevice().getDisplayMode().getWidth() - notificatieBreedte
                        , graphicsConfig.getDevice().getDisplayMode().getHeight() - getHeight() - heightCorrection - insets.bottom - buffer);
                break;
            default:
                setLocation(0, 0);
                break;
        }
    }

    public void moveDown(int height) {
        this.positionIndex--;
        int timerDelay = 50; // Milliseconds
        new Timer(timerDelay, e -> {
            Point p = JPNotification.this.getLocation();
            if (Position.BOTTOM_CENTER.equals(position) || Position.BOTTOM_LEFT.equals(position) || Position.BOTTOM_RIGHT.equals(position)) {
                final int newY = p.y + height;
                while (p.y < newY) {
                    p = new Point(p.x, p.y + 1);
                    JPNotification.this.setLocation(p);
                }
            } else {
                final int newY = p.y - height;
                while (p.y > newY) {
                    p = new Point(p.x, p.y - 1);
                    JPNotification.this.setLocation(p);
                }
            }
            ((Timer) e.getSource()).stop();
        }).start();
    }

    public void playSound() {
        if (pathToAudioResource == null) {
            return;
        }
        new Thread(() -> {
            URL url = getClass().getResource(pathToAudioResource);
            try {
                if (url != null) {
                    JPAudioUtils.playSound(url);
                }
            } catch (IOException | UnsupportedAudioFileException |
                    LineUnavailableException |
                    InterruptedException | RuntimeException ex) {
                log.error("", ex);
            }
        }).start();
    }

    public int getPositionIndex() {
        return positionIndex;
    }

    public JPNotification setPositionIndex(int positionIndex) {
        this.positionIndex = positionIndex;
        return this;
    }

    public Position getPosition() {
        return position;
    }

    public JPNotification setPosition(Position position) {
        this.position = position;
        return this;
    }

    public GraphicsConfiguration getGraphicsConfig() {
        return graphicsConfig;
    }

    public JPNotification setGraphicsConfig(GraphicsConfiguration graphicsConfig) {
        this.graphicsConfig = graphicsConfig;
        return this;
    }

    public int getTimeoutAutoHide() {
        return timeoutAutoHide;
    }

    public JPNotification setTimeoutAutoHide(int timeoutAutoHide) {
        this.timeoutAutoHide = timeoutAutoHide;
        return this;
    }

    public String getPathToAudioResource() {
        return pathToAudioResource;
    }

    public JPNotification setPathToAudioResource(String pathToAudioResource) {
        this.pathToAudioResource = pathToAudioResource;
        return this;
    }

    @Override
    public void dispose() {
        super.dispose();
        onClose();
    }

    @Override
    public void addMouseListener(MouseListener listener) {
        if (listener != null) {
            super.addMouseListener(listener);
            panel.addMouseListener(listener);
            titleLabel.addMouseListener(listener);
            descriptionLbl.addMouseListener(listener);
        }
    }

    @Override
    public void setVisible(boolean args0) {
        super.setVisible(args0);
        this.toFront();
        this.repaint();
    }

    public void onClose() {

    }

    public Icon getCloseIcon() {
        return closeBtn.getIcon();
    }

    public JPNotification setCloseIcon(Icon closeIcon) {
        if (closeIcon != null) {
            closeBtn.setIcon(closeIcon);
            closeBtn.setText(null);
            closeBtn.setMargin(new Insets(0, 0, 0, 0));
            closeBtn.setContentAreaFilled(false);
        } else {
            closeBtn.setIcon(null);
            closeBtn.setText("X");
            closeBtn.setMargin(new Insets(1, 4, 1, 4));
            closeBtn.setContentAreaFilled(false);
        }
        return this;
    }

    public enum Position {
        TOP_CENTER,
        TOP_LEFT,
        TOP_RIGHT,
        BOTTOM_CENTER,
        BOTTOM_LEFT,
        BOTTOM_RIGHT
    }
}
