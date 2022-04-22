package org.bigfoot.swingplus.event;

import lombok.extern.apachecommons.CommonsLog;

import java.lang.invoke.MethodType;
import java.util.ArrayList;
import java.util.List;

@CommonsLog
class JPListenerMap {

    private static final String respondMethodName = "respond";

    private Class<? extends JPListener> type;

    private final List<JPListener> listeners = new ArrayList<>();

    protected JPListenerMap(Class<? extends JPListener> type) {
        this.type = type;
    }

    public Class<? extends JPListener> getType() {
        return type;
    }

    public List<JPListener> getListeners() {
        return listeners;
    }

    public void addListener(JPListener listener) {
        if (listener != null && type.equals(listener.getClass()) && !listeners.contains(listener)) {
            listeners.add(listener);
        }
    }

    public void removeListener(JPListener listener) {
        if (listener != null && type.equals(listener.getClass()) && listeners.contains(listener)) {
            listeners.remove(listener);
        }
    }

    public boolean containsEventRespondMethod(Class<? extends JPEvent> eventType) {
        try {
            MethodType signature = MethodType.methodType(void.class, eventType);
            JPEventManager.caller.findVirtual(type, respondMethodName, signature);
            return true;
        } catch (NoSuchMethodException | IllegalAccessException ex) {
            return false;
        }
    }

    public void clean() {
        listeners.removeIf(l -> l == null);
    }

    @Override
    public String toString() {
        return type + "";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof JPListenerMap) {
            return ((JPListenerMap) obj).getType().equals(type);
        }
        return false;
    }
}
