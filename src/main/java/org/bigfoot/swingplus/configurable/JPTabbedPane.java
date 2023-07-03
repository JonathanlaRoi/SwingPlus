package org.bigfoot.swingplus.configurable;

import lombok.extern.apachecommons.CommonsLog;
import org.bigfoot.swingplus.event.JPListener;
import org.bigfoot.swingplus.event.v2.JPSender;

import javax.swing.*;

@CommonsLog
public class JPTabbedPane extends JTabbedPane implements JPContainer, JPListener, JPSender {

    public JPTabbedPane() {
        super();
        subscribe();
    }

    public void closeCurrent() {
        removeTabAt(getSelectedIndex());
    }

    public void closeAllOnLeft() {
        closeAllOnLeft(getSelectedIndex());
    }

    public void closeAllOnLeft(int index) {
        if (index <= 0) {
            return;
        }
        for (int i = index - 1; i >= 0; i--) {
            removeTabAt(i);
        }
    }

    public void closeAllOnRight() {
        closeAllOnRight(getSelectedIndex());
    }

    public void closeAllOnRight(int index) {
        if (index >= this.getTabCount() - 1) {
            return;
        }
        for (int i = index + 1; i < this.getTabCount(); i++) {
            removeTabAt(i);
        }
    }
}
