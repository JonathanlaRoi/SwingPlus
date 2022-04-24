package org.bigfoot.swingplus.event;

import lombok.extern.apachecommons.CommonsLog;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * @author Jonathan la Roi
 */
@CommonsLog
class JPEventWorker extends SwingWorker<List<JPRespondMethod>, Void> {
    private final JPEvent event;

    private final List<JPListenerMap> listeners;

    protected JPEventWorker(JPEvent event, List<JPListenerMap> listeners) {
        this.event = event;
        this.listeners = new ArrayList<>(listeners);
    }

    @Override
    protected List<JPRespondMethod> doInBackground() throws Exception {
        List<JPRespondMethod> respondMethods = new ArrayList<>();
        for (JPListenerMap map : listeners) {
            boolean typedRespond = map.containsEventRespondMethod(event.getClass());
            if (typedRespond) {
                for (JPListener listener : map.getListeners()) {
                    if (listener != null) {
                        respondMethods.add(new JPRespondMethod(listener, event, event.getClass()));
                    } else if (JPEventManager.isDebugLogging()) {
                        log.error("Listener is null");
                    }
                }
            } else if (JPEventManager.isDebugLogging()) {
                log.error("Can't find respond method for " + event.getClass());
            }
        }
        return respondMethods;
    }

    @Override
    protected void done() {
        try {
            if (JPEventManager.isDebugLogging()) {
                log.info(String.format("Sending event %s", event.getClass()));
            }
            for (JPRespondMethod rm : get()) {
                rm.execute();
            }
        } catch (InterruptedException | ExecutionException |
                IllegalArgumentException | SecurityException e) {
            throw new RuntimeException(e);
        }
    }
}
