package org.bigfoot.swingplus.event;

import lombok.extern.apachecommons.CommonsLog;
import org.reflections.Reflections;

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

/**
 * Event manager, pile of shit
 *
 * @author Jonathan la Roi
 * @since 30 augustus 2019
 */
@CommonsLog
public class JPEventManager {

    protected static final MethodHandles.Lookup caller = MethodHandles.lookup();

    private static JPEventManager instance;

    private final Map<Class<? extends JPEvent>, JPEventMap> eventMaps;

    private JPEventManager() {
        eventMaps = new ConcurrentHashMap<>();
    }

    private static JPEventManager getInstance() {
        if (instance == null) {
            instance = new JPEventManager();
        }
        return instance;
    }

    public static void autoRegister() {
        Reflections refs = new Reflections();
        Set<Class<? extends JPListener>> types = refs.getSubTypesOf(JPListener.class);
        for (Class<? extends JPEvent> type : refs.getSubTypesOf(JPEvent.class)) {
            getInstance().eventMaps.put(type, new JPEventMap(type, types.stream()
                    .map(JPListenerMap::new)
                    .collect(Collectors.toList()))
            );
        }
    }

    public static void register(List<Class<? extends JPListener>> listeners, List<Class<? extends JPEvent>> events) {
        for (Class<? extends JPEvent> type : events) {
            getInstance().eventMaps.put(type,
                    new JPEventMap(type, listeners.stream()
                            .map(JPListenerMap::new)
                            .collect(Collectors.toList()))
            );
        }
    }

    public static void addListener(JPListener listener) {
        if (listener == null) {
            return;
        }
        JPEventManager instance = getInstance();

        //Typed listeners (met een specefieke respond method)
        for (JPEventMap eventMap : instance.eventMaps.values()) {
            eventMap.addListener(listener);
            instance.eventMaps.put(eventMap.getType(), eventMap);
        }
    }

    public static void removeListener(JPListener listener) {
        if (listener == null) {
            return;
        }

        JPEventManager instance = getInstance();

        //Typed listeners (met een specefieke respond method)
        for (JPEventMap eventMap : instance.eventMaps.values()) {
            eventMap.removeListener(listener);
            instance.eventMaps.put(eventMap.getType(), eventMap);
        }
    }

    public static void clear() {
        getInstance().eventMaps.clear();
    }

    /**
     * method voor het opschonen van de EventManager
     */
    public static void clean() {
        getInstance().eventMaps.values().forEach(JPEventMap::clean);
    }

    public static void send(JPEvent event) {
        if (event != null) {
            JPEventMap map = getInstance().eventMaps.get(event.getClass());
            log.info("Sending event " + event.getClass() + " to listeners");
            getTypedWorker(event, map.getListenerMaps()).execute();
        }
    }

    public static void send(JPEvent event, Class<? extends JPListener> listener) {
        if (event != null && listener != null) {
            JPEventMap map = getInstance().eventMaps.get(event.getClass());
            getTypedWorker(event, map.getListenerMaps().stream()
                    .filter(m -> m.getType().equals(listener))
                    .collect(Collectors.toList())).execute();
        }
    }

    private static JPEventWorker getTypedWorker(JPEvent event, List<JPListenerMap> listeners) {
        return new JPEventWorker(event, listeners);
    }
}
