package org.bigfoot.swingplus.action;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Action die lambda's kan gebruiken
 *
 * @author Jonathan la Roi
 * @since 14 juni 2019
 */
public class JPLambdaAction extends AbstractAction {

    private ActionListener listener;

    public JPLambdaAction(ActionListener listener) {
        this.listener = listener;
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        listener.actionPerformed(event);
    }

    public void setActionListener(ActionListener listener) {
        this.listener = listener;
    }

    public ActionListener getActionListener() {
        return listener;
    }

    public static JPLambdaAction of(ActionListener listener) {
        return new JPLambdaAction(listener);
    }

}
