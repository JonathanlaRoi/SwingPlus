package org.bigfoot.swingplus.configurable;

import lombok.extern.apachecommons.CommonsLog;
import org.bigfoot.swingplus.event.JPEventManager;
import org.bigfoot.swingplus.event.JPListener;

import javax.swing.*;
import java.awt.*;

/**
 * @author Jonathan la Roi
 * @since 1 augustus 2019
 */
@CommonsLog
public class JPDialog extends JDialog implements JPContainer, JPListener, JPResizable {

    public JPDialog() {
        super();
        register();
    }

    public JPDialog(Dialog owner) {
        super(owner);
        register();
    }

    public JPDialog(Dialog owner, boolean modal) {
        super(owner, modal);
        register();
    }

    public JPDialog(Dialog owner, String title) {
        super(owner, title);
        register();
    }

    public JPDialog(Dialog owner, String title, boolean modal) {
        super(owner, title, modal);
        register();
    }

    public JPDialog(Dialog owner, String title, boolean modal, GraphicsConfiguration gc) {
        super(owner, title, modal, gc);
        register();
    }

    public JPDialog(Frame owner) {
        super(owner);
        register();
    }

    public JPDialog(Frame owner, boolean modal) {
        super(owner, modal);
        register();
    }

    public JPDialog(Frame owner, String title) {
        super(owner, title);
        register();
    }

    public JPDialog(Frame owner, String title, boolean modal) {
        super(owner, title, modal);
        register();
    }

    public JPDialog(Frame owner, String title, boolean modal, GraphicsConfiguration gc) {
        super(owner, title, modal, gc);
        register();
    }

    public JPDialog(Window owner) {
        super(owner);
        register();
    }

    public JPDialog(Window owner, ModalityType modalityType) {
        super(owner, modalityType);
        register();
    }

    public JPDialog(Window owner, String title) {
        super(owner, title);
        register();
    }

    public JPDialog(Window owner, String title, ModalityType modalityType) {
        super(owner, title, modalityType);
        register();
    }

    public JPDialog(Window owner, String title, ModalityType modalityType, GraphicsConfiguration gc) {
        super(owner, title, modalityType, gc);
        register();
    }

    @Override
    public void dispose() {
        JPEventManager.removeListener(this);
        super.dispose();
    }
}
