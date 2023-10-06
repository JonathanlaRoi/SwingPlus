package org.bigfoot.swingplus.event;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.swing.SwingWorker;

import lombok.extern.apachecommons.CommonsLog;

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
            if (map.size() > 0) {
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
                    log.debug("Can't find respond method for " + event.getClass());
                }
            }
        }
        return respondMethods;
    }

    @Override
    protected void done() {
        try {
            List<JPRespondMethod> respondMethods = get();
            if (respondMethods != null && !respondMethods.isEmpty()) {
                if (JPEventManager.isDebugLogging()) {
                    log.debug(String.format("Sending event %s", event.getClass()));
                }
                for (JPRespondMethod rm : get()) {
                    rm.execute();
                }
            }
        } catch (InterruptedException | ExecutionException |
                IllegalArgumentException | SecurityException e) {
            throw new RuntimeException(e);
        }
    }
}
