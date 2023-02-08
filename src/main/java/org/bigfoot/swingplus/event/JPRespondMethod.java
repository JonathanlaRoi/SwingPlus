package org.bigfoot.swingplus.event;

import lombok.extern.apachecommons.CommonsLog;

import java.lang.reflect.InvocationTargetException;

import static org.bigfoot.swingplus.event.JPEventManagerUtils.getResponseMethodForListener;

@CommonsLog
class JPRespondMethod {

    private static final String methodName = "respond";

    private final JPListener listener;

    private final JPEvent event;

    private final Class<? extends JPEvent> eventType;

    protected JPRespondMethod(JPListener listener, JPEvent event, Class<? extends JPEvent> eventType) {
        this.listener = listener;
        this.event = event;
        this.eventType = eventType;
    }

    protected void execute() throws RuntimeException {
        try {
            getResponseMethodForListener(listener.getClass(), eventType).invoke(listener, event);
            if (JPEventManager.isDebugLogging()) {
                log.debug(String.format("Event received by %s", listener));
            }
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}
