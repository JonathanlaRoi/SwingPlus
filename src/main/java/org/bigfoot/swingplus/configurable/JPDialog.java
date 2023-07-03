package org.bigfoot.swingplus.configurable;

import lombok.extern.apachecommons.CommonsLog;
import org.bigfoot.swingplus.event.JPListener;
import org.bigfoot.swingplus.event.v2.JPSender;

import javax.swing.*;
import java.awt.*;

/**
 * @author Jonathan la Roi
 * @since 1 augustus 2019
 */
@CommonsLog
public class JPDialog extends JDialog implements JPContainer, JPListener, JPResizable, JPSender {

    public JPDialog() {
        super();
        subscribe();
    }

    public JPDialog(Dialog owner) {
        super(owner);
        subscribe();
    }

    public JPDialog(Dialog owner, boolean modal) {
        super(owner, modal);
        subscribe();
    }

    public JPDialog(Dialog owner, String title) {
        super(owner, title);
        subscribe();
    }

    public JPDialog(Dialog owner, String title, boolean modal) {
        super(owner, title, modal);
        subscribe();
    }

    public JPDialog(Dialog owner, String title, boolean modal, GraphicsConfiguration gc) {
        super(owner, title, modal, gc);
        subscribe();
    }

    public JPDialog(Frame owner) {
        super(owner);
        subscribe();
    }

    public JPDialog(Frame owner, boolean modal) {
        super(owner, modal);
        subscribe();
    }

    public JPDialog(Frame owner, String title) {
        super(owner, title);
        subscribe();
    }

    public JPDialog(Frame owner, String title, boolean modal) {
        super(owner, title, modal);
        subscribe();
    }

    public JPDialog(Frame owner, String title, boolean modal, GraphicsConfiguration gc) {
        super(owner, title, modal, gc);
        subscribe();
    }

    public JPDialog(Window owner) {
        super(owner);
        subscribe();
    }

    public JPDialog(Window owner, ModalityType modalityType) {
        super(owner, modalityType);
        subscribe();
    }

    public JPDialog(Window owner, String title) {
        super(owner, title);
        subscribe();
    }

    public JPDialog(Window owner, String title, ModalityType modalityType) {
        super(owner, title, modalityType);
        subscribe();
    }

    public JPDialog(Window owner, String title, ModalityType modalityType, GraphicsConfiguration gc) {
        super(owner, title, modalityType, gc);
        subscribe();
    }

    @Override
    public void dispose() {
        unsubscribe();
        super.dispose();
    }
}
