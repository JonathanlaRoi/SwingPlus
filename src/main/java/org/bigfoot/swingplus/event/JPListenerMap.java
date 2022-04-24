package org.bigfoot.swingplus.event;

import lombok.extern.apachecommons.CommonsLog;
import org.bigfoot.swingplus.util.JPClassUtils;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.WeakHashMap;

@CommonsLog
class JPListenerMap {

    private static final String respondMethodName = "respond";

    private final Class<? extends JPListener> type;

    private final Map<Integer, JPListener> listeners = new WeakHashMap<>();

    protected JPListenerMap(Class<? extends JPListener> type) {
        this.type = type;
    }

    public Class<? extends JPListener> getType() {
        return type;
    }

    public Collection<JPListener> getListeners() {
        return listeners.values();
    }

    public void addListener(JPListener listener) {
        if (listener != null && type.equals(JPClassUtils.getRealClassOfObject(listener)) && !containsListener(listener)) {
            listeners.put(listener.hashCode(), listener);
        }
    }

    public void removeListener(JPListener listener) {
        if (listener != null && type.equals(JPClassUtils.getRealClassOfObject(listener))) {
            listeners.remove(listener.hashCode());
        }
    }

    public boolean containsListener(JPListener listener) {
        return listeners.containsKey(listener.hashCode());
    }

    public boolean containsEventRespondMethod(Class<? extends JPEvent> eventType) {
        try {
            type.getMethod(respondMethodName, eventType);
            return true;
        } catch (NoSuchMethodException ex) {
            return false;
        }
    }

    public void clean() {
        System.gc();
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
