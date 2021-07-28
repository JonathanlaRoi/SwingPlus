package org.bigfoot.swingplus.util;

import lombok.experimental.UtilityClass;
import lombok.extern.apachecommons.CommonsLog;
import org.bigfoot.swingplus.lang.JPLang;

import javax.annotation.Nonnull;
import javax.swing.*;
import javax.swing.UIManager.LookAndFeelInfo;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import static org.bigfoot.swingplus.util.JPClassUtils.getRealClass;

/**
 * @author Jonathan la Roi
 * @since 28 mei 2019
 */
@CommonsLog
@UtilityClass
public class JPGuiUtils {

    @Nonnull
    public static String translate(String key, Class<?> clazz) {
        try {
            if (key == null) {
                return null;
            }
            ResourceBundle bundle = getBundle(clazz);
            if (bundle != null) {
                if (bundle.containsKey(key)) {
                    return bundle.getString(key);
                } else if (bundle.containsKey(key.toLowerCase())) {
                    return bundle.getString(key.toLowerCase());
                } else if (bundle.containsKey(key.toUpperCase())) {
                    return bundle.getString(key.toUpperCase());
                } else {
                    return translate(key);
                }
            } else {
                return translate(key);
            }
        } catch (NullPointerException | MissingResourceException | ClassCastException ex) {
            log.error("", ex);
            return translate(key);
        }
    }


    @Nonnull
    public static String translate(String key) {
        ResourceBundle bundle;
        if (getLocale() != null) {
            bundle = ResourceBundle.getBundle(JPLang.getDefaultLocalePath(), getLocale());
        } else {
            bundle = ResourceBundle.getBundle(JPLang.getDefaultLocalePath());
        }
        try {
            if (bundle != null) {
                if (bundle.containsKey(key)) {
                    return bundle.getString(key);
                } else if (bundle.containsKey(key.toLowerCase())) {
                    return bundle.getString(key.toLowerCase());
                } else if (bundle.containsKey(key.toUpperCase())) {
                    return bundle.getString(key.toUpperCase());
                } else {
                    return "[" + key.toUpperCase() + "]";
                }
            } else {
                return "[" + key.toUpperCase() + "]";
            }
        } catch (NullPointerException | MissingResourceException | ClassCastException ex) {
            log.error("", ex);
            return "[" + key.toUpperCase() + "]";
        }
    }

    public static boolean containsKey(String key, Class<?> clazz) {
        if (key == null)
            return false;
        ResourceBundle bundle = getBundle(clazz);

        return bundle != null && (bundle.containsKey(key) ||
                bundle.containsKey(key.toLowerCase()) ||
                bundle.containsKey(key.toUpperCase()) ||
                containsKey(key));
    }

    public static boolean containsKey(String key) {
        if (key == null)
            return false;
        ResourceBundle bundle;
        if (getLocale() != null) {
            bundle = ResourceBundle.getBundle(JPLang.getDefaultLocalePath(), getLocale());
        } else {
            bundle = ResourceBundle.getBundle(JPLang.getDefaultLocalePath());
        }

        return bundle != null && (bundle.containsKey(key) ||
                bundle.containsKey(key.toLowerCase()) ||
                bundle.containsKey(key.toUpperCase()));
    }

    private static ResourceBundle getBundle(Class<?> clazz) throws NullPointerException, MissingResourceException {
        return getBundle(getRealClass(clazz).getName());
    }

    private static ResourceBundle getBundle(String pathToPropertiesFile) throws NullPointerException, MissingResourceException {
        try {
            ResourceBundle resourceBundle;
            if (getLocale() != null) {
                resourceBundle = ResourceBundle.getBundle(pathToPropertiesFile, getLocale());
            } else {
                resourceBundle = ResourceBundle.getBundle(pathToPropertiesFile);
            }
            return resourceBundle;
        } catch (MissingResourceException ex) {
            return null;
        } catch (RuntimeException ex) {
            log.error("", ex);
            return null;
        }
    }

    public static void setLookAndFeel(String name)
            throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
        try {
            if (name == null)
                return;
            for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if (name.equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    return;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            throw ex;
        }
    }

    public static boolean lookAndFeelExists(String name) {
        if (name == null)
            return false;
        for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
            if (name.equals(info.getName())) {
                return true;
            }
        }
        return false;
    }

    public static Locale getLocale() {
        return JPLang.getLocale();
    }

    public static void setLocale(Locale locale) {
        JPLang.setLocale(locale);
    }

    public static GraphicsDevice[] getScreens() {
        GraphicsEnvironment g = GraphicsEnvironment.getLocalGraphicsEnvironment();
        return g.getScreenDevices();
    }

    public static GraphicsDevice getDevice(int screenIndex) {
        GraphicsDevice[] devices = getScreens();
        GraphicsDevice device = null;
        if (devices.length > screenIndex) {
            device = devices[screenIndex];
        }

        return device;
    }

    public static int getScreenIndex(GraphicsDevice device) {
        GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] allScreens = env.getScreenDevices();
        int index = -1;
        for (int i = 0; i < allScreens.length; i++) {
            if (allScreens[i].equals(device)) {
                index = i;
                break;
            }
        }
        return index;
    }

    public static Dimension getScreenDimension(int screenIndex) {
        GraphicsDevice[] devices = getScreens();
        GraphicsDevice device = null;
        if (devices.length > screenIndex) {
            device = devices[screenIndex];
        }

        if (device == null) {
            return null;
        }
        return getScreenDimension(device);
    }

    public static Dimension getScreenDimension(GraphicsDevice device) {
        int width = device.getDisplayMode().getWidth();
        int height = device.getDisplayMode().getHeight();
        return new Dimension(width, height);
    }

    public static void centreWindow(Window window, int screenIndex) {
        GraphicsDevice[] devices = getScreens();
        GraphicsDevice device = null;
        if (devices.length > screenIndex) {
            device = devices[screenIndex];
        }
        if (device != null) {
            JFrame dummy = new JFrame(device.getDefaultConfiguration());
            window.setLocationRelativeTo(dummy);
            Point p = window.getLocation();
            p.setLocation(p.getX() - window.getWidth() / 2, p.getY() - window.getHeight() / 2);
            window.setLocation(p);
            dummy.dispose();
        } else {
            window.setLocationRelativeTo(null);
        }
    }

    public static void fadeOut(Window dialog) {
        int timerDelay = 50; // Milliseconds
        new Timer(timerDelay, new ActionListener() {
            private int counter = 10;

            @Override
            public void actionPerformed(ActionEvent e) {
                counter--;
                if (counter == 1) {
                    dialog.dispose();
                    ((Timer) e.getSource()).stop();
                }
                dialog.setOpacity(counter * 0.1F);
            }
        }).start();
    }
}
