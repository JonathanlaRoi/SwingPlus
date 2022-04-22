package org.bigfoot.swingplus.event;

import org.bigfoot.swingplus.util.JPClassUtils;

import java.util.List;
import java.util.stream.Collectors;

class JPEventMap {

    private final Class<? extends JPEvent> type;

    private final List<JPListenerMap> listenerMaps;

    protected JPEventMap(Class<? extends JPEvent> type, List<JPListenerMap> listenerMaps) {
        this.type = type;
        this.listenerMaps = listenerMaps.stream().filter(map -> map.containsEventRespondMethod(type)).collect(Collectors.toList());
    }

    public Class<? extends JPEvent> getType() {
        return type;
    }

    public void addListener(JPListener listener) {
        JPListenerMap map = new JPListenerMap((Class<? extends JPListener>) JPClassUtils.getRealClassOfObject(listener));
        int index = -1;
        if (listenerMaps.contains(map)) {
            index = listenerMaps.indexOf(map);
            map = listenerMaps.get(index);
        }
        if (!map.getListeners().contains(listener) && map.containsEventRespondMethod(type)) {
            map.addListener(listener);
            if (index != -1) {
                listenerMaps.set(index, map);
            } else {
                listenerMaps.add(map);
            }
        }
    }

    public void removeListener(JPListener listener) {
        JPListenerMap map = new JPListenerMap((Class<? extends JPListener>) JPClassUtils.getRealClassOfObject(listener));
        if (listenerMaps.contains(map)) {
            JPListenerMap curMap = listenerMaps.get(listenerMaps.indexOf(map));
            curMap.removeListener(listener);
            if (curMap.getListeners().size() == 0) {
                listenerMaps.remove(listenerMaps.indexOf(curMap));
            }
        }
    }

    public List<JPListenerMap> getListenerMaps() {
        return listenerMaps;
    }

    public void clean() {
        listenerMaps.forEach(l -> l.clean());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof JPListenerMap) {
            return ((JPEventMap) obj).getType().equals(type);
        }
        return false;
    }

}
