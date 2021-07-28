package org.bigfoot.swingplus.notifications.component;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.bigfoot.swingplus.eventlisteners.JPLambdaMouseClickListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;
import java.util.concurrent.CopyOnWriteArrayList;

@Accessors(chain = true)
public class JPNotificationManager {

    private final CopyOnWriteArrayList<JPNotification> notifications = new CopyOnWriteArrayList<>();

    private final JPNotification.Position position;

    private GraphicsConfiguration graphicsConfiguration;

    private Icon closeIcon;

    private boolean placeNotificationAlwaysOnTop = true;

    @Getter
    @Setter
    private String defaultAudioFilePath;

    @Getter
    @Setter
    private boolean dynamicHeight = false;

    @Getter
    @Setter
    private boolean overlapping = false;

    @Getter
    @Setter
    private int buffer = 0;

    public JPNotificationManager(JPNotification.Position position) {
        if (position == null) {
            throw new NullPointerException("position cant be null");
        }
        this.position = position;
    }

    public JPNotificationManager setGraphicsConfiguration(GraphicsConfiguration graphicsConfiguration) {
        this.graphicsConfiguration = graphicsConfiguration;
        return this;
    }

    public GraphicsConfiguration getGraphicsConfiguration() {
        return graphicsConfiguration;
    }

    public JPNotificationManager setCloseIcon(Icon closeIcon) {
        this.closeIcon = closeIcon;
        return this;
    }

    public Icon getCloseIcon() {
        return closeIcon;
    }

    public void setPlaceNotificationAlwaysOnTop(boolean placeNotificationAlwaysOnTop) {
        this.placeNotificationAlwaysOnTop = placeNotificationAlwaysOnTop;
    }

    public void showNotification(String description) {
        showNotification(null, description, null, 0, null, null);
    }

    public void showNotification(String title, String description) {
        showNotification(title, description, null, 0, null, null);
    }

    public void showNotification(String description, int autoTimeout) {
        showNotification(null, description, null, autoTimeout, null, null);
    }

    public void showNotification(String title, String description, Icon icon) {
        showNotification(title, description, icon, 0, null, null);
    }

    public void showNotification(String title, String description, int autoTimeout) {
        showNotification(title, description, null, autoTimeout, null, null);
    }

    public void showNotification(String title, String description, Icon icon, int autoTimeout) {
        showNotification(title, description, icon, autoTimeout, null, null);
    }

    public void showNotification(String title, String description, Icon icon, int autoTimeout, MouseListener mouseListener) {
        showNotification(title, description, icon, autoTimeout, mouseListener, null);
    }

    public void showNotification(String title, String description, Icon icon, int autoTimeout, MouseListener mouseListener,
                                 String pathToAudioResource) {
        JPNotification notification = new JPNotification(title, description, icon, dynamicHeight) {
            @Override
            public void onClose() {
                notifications.removeIf(i -> i.getPositionIndex() == this.getPositionIndex());
                notifications.forEach(n -> {
                    if (n.getPositionIndex() > this.getPositionIndex()) {
                        n.moveDown(getHeight());
                    }
                });
            }
        }.setTimeoutAutoHide(autoTimeout)
                .setPlaceNotificationAlwaysOnTop(placeNotificationAlwaysOnTop)
                .setPositionIndex(notifications.size())
                .setPosition(position)
                .setBuffer(buffer)
                .setPathToAudioResource(pathToAudioResource != null ? pathToAudioResource : defaultAudioFilePath);

        notification.setPositionIndex(notifications.size());

        if (getCloseIcon() != null) {
            notification.setCloseIcon(getCloseIcon());
        }
        if (getGraphicsConfiguration() != null) {
            notification.setGraphicsConfig(getGraphicsConfiguration());
        }
        if (mouseListener != null) {
            notification.addMouseListener(mouseListener);
        } else {
            notification.addMouseListener((JPLambdaMouseClickListener) e -> notification.dispose());
        }

        notification.showNotification(notifications.stream().map(Component::getHeight).reduce(Integer::sum).orElse(null));
        notifications.add(notification);

        if (pathToAudioResource != null || defaultAudioFilePath != null) {
            notification.playSound();
        }
    }
}
