package org.bigfoot.swingplus.event.v2;

import org.bigfoot.swingplus.event.JPEvent;
import org.bigfoot.swingplus.event.JPEventManagerUtils;
import org.bigfoot.swingplus.event.OnJPEvent;

import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class JPSenderUtils {

	public static void send(Object sender, JPEvent event, BroadcastType broadcastType) {
		try {
			switch (broadcastType) {
				case BREATH:
					handleBreathEvent(sender, event);
					break;
				case DEPTH:
					handleDepthEvent(sender, event);
					break;
				case BUBBLE:
					handleBubbleEvent(sender, event);
					break;
			}
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	private static void handleBreathEvent(Object sender, JPEvent event) throws InvocationTargetException, IllegalAccessException {
		if (sender instanceof Container) {
			handleBreathEvent(((Container) sender).getComponents(), event);
		}
	}

	private static void handleBreathEvent(Component[] components, JPEvent event) throws InvocationTargetException, IllegalAccessException {
		if (components.length > 0) {
			for (Component child : components) {
				if (containsEventMethod(child, event)) {
					Method method = getEventMethod(child, event);
					method.invoke(child, event);
					OnJPEvent eventAnnotation = method.getAnnotation(OnJPEvent.class);
					if (eventAnnotation.stop()) {
						return;
					}
				}
			}

			List<Component> subComponents = Arrays.stream(components)
					.map(c -> c instanceof Container ? ((Container) c).getComponents() : null)
					.filter(Objects::nonNull)
					.flatMap(Arrays::stream)
					.collect(Collectors.toList());

			for (Component c : subComponents) {
				handleBreathEvent(c, event);
			}
		}
	}

	private static void handleDepthEvent(Object sender, JPEvent event) throws InvocationTargetException, IllegalAccessException {
		if (sender instanceof Container) {
			for (Component child : ((Container) sender).getComponents()) {
				if (containsEventMethod(child, event)) {
					Method method = getEventMethod(child, event);
					method.invoke(child, event);
					OnJPEvent eventAnnotation = method.getAnnotation(OnJPEvent.class);
					if (eventAnnotation.stop()) {
						return;
					}
				}
				handleDepthEvent(child, event);
			}
		}
	}

	private static void handleBubbleEvent(Object sender, JPEvent event) throws InvocationTargetException, IllegalAccessException {
		if (sender instanceof Component) {
			Component parent = ((Component) sender).getParent();
			if (parent != null) {
				if (containsEventMethod(parent, event)) {
					Method method = getEventMethod(parent, event);
					method.invoke(parent, event);
					OnJPEvent eventAnnotation = method.getAnnotation(OnJPEvent.class);
					if (eventAnnotation.stop()) {
						return;
					}
				}
				handleBubbleEvent(parent, event);
			}
		}
	}

	private static boolean containsEventMethod(Object type, JPEvent event) {
		return getEventMethod(type, event) != null;
	}

	private static Method getEventMethod(Object obj, JPEvent event) {
		return JPEventManagerUtils.getEventMethodBasedOnAnnotation(obj.getClass(), event.getClass());
	}

}
