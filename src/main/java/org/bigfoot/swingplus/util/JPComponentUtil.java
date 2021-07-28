package org.bigfoot.swingplus.util;

import lombok.experimental.UtilityClass;
import org.bigfoot.swingplus.action.JPLambdaAction;

import javax.swing.*;
import java.awt.event.ActionListener;

/**
 * Utiliy class for adding behaviour between JP components that don't share a parent
 *
 * @author Jonathan la Roi
 * @since 22/03/2021
 */
@UtilityClass
public class JPComponentUtil {

    public static void addActionListener(AbstractButton abstractButton, ActionListener listener, KeyStroke... keyStrokes) {
        addActionListener(abstractButton, listener.toString(), listener, keyStrokes);
    }

    public static void addActionListener(AbstractButton abstractButton, String name, ActionListener listener, KeyStroke... keyStrokes) {
        if (keyStrokes != null && keyStrokes.length > 0) {
            JPLambdaAction action = JPLambdaAction.of(listener);
            abstractButton.getActionMap().put(name, action);
            for (KeyStroke keyStroke : keyStrokes) {
                abstractButton.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(keyStroke, name);
            }
        }
        abstractButton.addActionListener(listener);
    }
}
