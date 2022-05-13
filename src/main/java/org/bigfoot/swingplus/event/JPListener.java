package org.bigfoot.swingplus.event;

public interface JPListener {

    default void subscribe() {
        JPEventManager.subscribe(this);
    }

    default void unsubscribe() {
        JPEventManager.unsubscribe(this);
    }

}
