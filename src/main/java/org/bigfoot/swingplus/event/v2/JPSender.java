package org.bigfoot.swingplus.event.v2;

import org.bigfoot.swingplus.event.JPEvent;

/**
 * @author Jonathan la Roi
 * @since 24/04/2023
 */
public interface JPSender {
	default void send(JPEvent event, BroadcastType broadcastType) {
		JPSenderUtils.send(this, event, broadcastType);
	}
}
