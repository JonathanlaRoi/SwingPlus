package org.bigfoot.swingplus.event;

import lombok.extern.apachecommons.CommonsLog;

import java.lang.reflect.InvocationTargetException;

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
            if (JPEventManager.isDebugLogging()) {
                log.info(String.format("Sending event to %s", listener));
            }
            listener.getClass().getMethod(methodName, eventType).invoke(listener, event);
            if (JPEventManager.isDebugLogging()) {
                log.info(String.format("Event received by %s", listener));
            }
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}
