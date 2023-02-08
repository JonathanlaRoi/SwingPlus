package org.bigfoot.swingplus.event;

import lombok.experimental.UtilityClass;
import org.bigfoot.swingplus.util.JPClassUtils;

import java.lang.reflect.Method;

@UtilityClass
public class JPEventManagerUtils {

    private static final String respondMethodName = "respond";

    public static boolean listenerContainsEventMethod(Class<? extends JPListener> listener, Class<? extends JPEvent> eventType) {
        return getResponseMethodForListener(listener, eventType) != null;
    }

    public static Method getResponseMethodForListener(Class<? extends JPListener> listener, Class<? extends JPEvent> eventType) {
        try {
            return listener.getMethod(respondMethodName, eventType);
        } catch (NoSuchMethodException e) {
            return getEventMethodBasedOnAnnotation(listener, eventType);
        }
    }

    private static Method getEventMethodBasedOnAnnotation(Class<? extends JPListener> listener, Class<? extends JPEvent> eventType) {
        Class<?> listenerType = listener;
        while (listenerType != Object.class) {
            for (final Method method : listenerType.getDeclaredMethods()) {
                if (method.isAnnotationPresent(OnJPEvent.class) &&
                        method.getParameterCount() == 1
                        && JPClassUtils.getRealClass(method.getParameterTypes()[0]) == eventType) {
                    return method;
                }
            }
            // move to the upper class in the hierarchy in search for more methods
            listenerType = listenerType.getSuperclass();
        }
        return null;
    }
}
