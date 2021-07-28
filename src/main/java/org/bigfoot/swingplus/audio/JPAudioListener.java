package org.bigfoot.swingplus.audio;

import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;

/**
 * 
 * @author Jonathan la Roi
 * @since 17 juni 2019
 */
public class JPAudioListener implements LineListener {
	private boolean done = false;
	@Override 
	public synchronized void update(LineEvent event) {
		LineEvent.Type eventType = event.getType();
		if (eventType == LineEvent.Type.STOP ||
			eventType == LineEvent.Type.CLOSE) {
			done = true;
			notifyAll();
		}
    }
	
    public synchronized void waitUntilDone() throws InterruptedException {
    	while (!done) { wait(); }
	}
}