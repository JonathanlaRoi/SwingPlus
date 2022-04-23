package org.bigfoot.swingplus.event;

public interface JPListener {

    default void register() {
        JPEventManager.addListener(this);
    }

    default void unregister() {
        JPEventManager.removeListener(this);
    }

}
