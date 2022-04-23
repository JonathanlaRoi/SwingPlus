package org.bigfoot.swingplus.event;

import java.lang.reflect.InvocationTargetException;

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
            listener.getClass().getMethod(methodName, eventType).invoke(listener, event);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}
