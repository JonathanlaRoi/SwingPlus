package org.bigfoot.swingplus.event;

import org.reflections.Reflections;

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

/**
 * Event manager
 *
 * @author Jonathan la Roi
 * @since 30 augustus 2019
 */
public class JPEventManager {

    protected static final MethodHandles.Lookup caller = MethodHandles.lookup();

    private static JPEventManager instance;

    private final List<JPEventMap> eventMaps;

    private JPEventManager() {
        eventMaps = new CopyOnWriteArrayList<>();
    }

    private static JPEventManager getInstance() {
        if (instance == null) {
            instance = new JPEventManager();
        }
        return instance;
    }

    /**
     * TODO Spring integration toevoegen want dit werkt shit
     */
    public static void autoRegister() {
        Reflections refs = new Reflections();
        Set<Class<? extends JPListener>> types = refs.getSubTypesOf(JPListener.class);
        for (Class<? extends JPEvent> type : refs.getSubTypesOf(JPEvent.class)) {
            getInstance().eventMaps.add(
                    new JPEventMap(type, types.stream()
                            .map(JPListenerMap::new)
                            .collect(Collectors.toList()))
            );
        }
    }

    public static void register(List<Class<? extends JPListener>> listeners, List<Class<? extends JPEvent>> events) {
        for (Class<? extends JPEvent> type : events) {
            getInstance().eventMaps.add(
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
        int index = 0;
        for (JPEventMap eventMap : instance.eventMaps) {
            eventMap.addListener(listener);
            instance.eventMaps.set(index, eventMap);
            index++;
        }
    }

    public static void removeListener(JPListener listener) {
        if (listener == null) {
            return;
        }

        JPEventManager instance = getInstance();

        //Typed listeners (met een specefieke respond method)
        int index = 0;
        for (JPEventMap eventMap : instance.eventMaps) {
            eventMap.removeListener(listener);
            instance.eventMaps.set(index, eventMap);
            index++;
        }
    }

    public static void clear() {
        getInstance().eventMaps.clear();
    }

    /**
     * method voor het opschonen van de EventManager
     */
    public static void clean() {
        getInstance().eventMaps.forEach(JPEventMap::clean);
    }

    public static void send(JPEvent event) {
        List<JPEventMap> maps = getInstance().eventMaps
                .stream()
                .filter(e -> e.getType().equals(event.getClass()))
                .collect(Collectors.toList());
        for (JPEventMap map : maps) {
            getTypedWorker(event, map.getListenerMaps()).execute();
        }
    }

    public static void send(JPEvent event, Class<? extends JPListener> listener) {
        List<JPEventMap> maps = getInstance().eventMaps
                .stream()
                .filter(e -> e.getType().equals(event.getClass()))
                .collect(Collectors.toList());
        for (JPEventMap map : maps) {
            getTypedWorker(event, map.getListenerMaps().stream()
                    .filter(m -> m.getType().equals(listener))
                    .collect(Collectors.toList())).execute();
        }
    }

    private static JPEventWorker getTypedWorker(JPEvent event, List<JPListenerMap> listeners) {
        return new JPEventWorker(event, listeners);
    }
}
