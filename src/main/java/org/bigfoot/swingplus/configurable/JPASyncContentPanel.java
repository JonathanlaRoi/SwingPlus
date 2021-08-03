package org.bigfoot.swingplus.configurable;

import lombok.extern.apachecommons.CommonsLog;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.*;
import java.awt.*;
import java.util.concurrent.ExecutionException;
import java.util.function.Supplier;

/**
 * Panel voor het asynchroon laden van een ander component {@link TYPE extends JComponent}
 *
 * @author Jonathan la Roi
 * @since 29 mei 2019
 */
@CommonsLog
public class JPASyncContentPanel<TYPE extends Component> extends JPPanel {
    private ImageIcon icon;

    private String label;

    private TYPE currentContent;

    private JLabel lblComp;

    public JPASyncContentPanel() {
        this(null, null);
    }

    public JPASyncContentPanel(String label) {
        this(null, label);
    }

    public JPASyncContentPanel(ImageIcon icon) {
        this(icon, null);
    }

    public JPASyncContentPanel(ImageIcon icon, String label) {
        super();
        this.icon = icon;
        this.label = label;
        init();
    }

    private void init() {
        this.setLayout(new BorderLayout());
        lblComp = new JLabel(icon, SwingConstants.CENTER);
        lblComp.setText(label);
        add(lblComp, BorderLayout.CENTER);
    }

    public void setIcon(ImageIcon icon) {
        this.icon = icon;
        lblComp.setIcon(icon);
        this.repaint();
        this.revalidate();
    }

    public void setLabel(String label) {
        this.label = label;
        lblComp.setText(label);
        this.repaint();
        this.revalidate();
    }

    public ImageIcon getIcon() {
        return icon;
    }

    public String setLabel() {
        return label;
    }

    public void loadContent(@Nonnull Supplier<TYPE> componentSupplier) {
        getLoaderWorker(componentSupplier).execute();
    }

    private SwingWorker<TYPE, Void> getLoaderWorker(Supplier<TYPE> componentSupplier) {
        return new SwingWorker<TYPE, Void>() {
            @SuppressWarnings("unchecked")
            @Override
            protected TYPE doInBackground() throws Exception {
                return componentSupplier.get();
            }

            @Override
            protected void done() {
                try {
                    currentContent = this.get();
                    JPASyncContentPanel.this.removeAll();
                    JPASyncContentPanel.this.add(currentContent, BorderLayout.CENTER);
                    JPASyncContentPanel.this.repaint();
                    JPASyncContentPanel.this.revalidate();
                } catch (RuntimeException | InterruptedException | ExecutionException e) {
                    log.error("", e);
                    showExceptionComponent(e);
                }
            }
        };
    }

    private void showExceptionComponent(Exception exception) {
        JComponent errorComp = getErrorComponent(exception);
        if (errorComp == null) {
            errorComp = new JLabel("Exception: " + exception.getClass(), SwingConstants.CENTER);
        }
        JPASyncContentPanel.this.removeAll();
        JPASyncContentPanel.this.add(errorComp, BorderLayout.CENTER);
        JPASyncContentPanel.this.repaint();
        JPASyncContentPanel.this.revalidate();
    }

    /**
     * Default implementatie returned null, kan geoverride worden om custom component te weergeven bij fouten
     *
     * @param exception {@link Exception}
     * @return {@link JComponent}
     */
    @Nullable
    public JComponent getErrorComponent(Exception exception) {
        return null;
    }

    /**
     * Returned de huidige content
     *
     * @return {@link TYPE}
     */
    @Nullable
    public TYPE getContent() {
        return currentContent;
    }
}
