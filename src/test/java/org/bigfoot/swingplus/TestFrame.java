package org.bigfoot.swingplus;

import lombok.extern.apachecommons.CommonsLog;
import org.bigfoot.swingplus.configurable.JPFrame;
import org.bigfoot.swingplus.configurable.JPPanel;
import org.bigfoot.swingplus.configurable.components.JPButton;
import org.bigfoot.swingplus.event.JPEventManager;

import javax.swing.*;
import java.awt.*;
import java.util.Collections;

/**
 * TODO test maken voor JPEvent manager
 * @author Jonathan la Roi
 * @since 24/04/2022
 */
@CommonsLog
public class TestFrame extends JPFrame {

    public TestFrame() {
        super();
        getContentPane().setLayout(new BorderLayout());
        setSize(1520, 690);
        setExtendedState(this.getExtendedState() | JFrame.MAXIMIZED_BOTH);

        JPanel testPnl = new JPPanel(new FlowLayout());
        JPanel testBtnPnl = new JPPanel(new FlowLayout());

        testPnl.add(new Component());
        testPnl.add(new Component());
        testBtnPnl.add(new JPButton("clearpnl", e-> {
            testPnl.removeAll();
            testPnl.repaint();
            testPnl.revalidate();
        }));
        testBtnPnl.add(new JPButton("debug", e -> {
            System.gc();
            try {
                Thread.sleep(10);
            } catch (InterruptedException ex) {
                log.error("", ex);
            }
            log.info("Debug time");
        }));

        getContentPane().add(testPnl);
        getContentPane().add(testBtnPnl, BorderLayout.SOUTH);

        setVisible(true);
    }

    public static void main(String args[]) {
        JPEventManager.register(Collections.singletonList(Component.class), Collections.singletonList(Event.class));
        new TestFrame();
    }
}
