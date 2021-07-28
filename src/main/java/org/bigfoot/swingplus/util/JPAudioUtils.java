package org.bigfoot.swingplus.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import lombok.experimental.UtilityClass;
import org.bigfoot.swingplus.audio.JPAudioListener;

@UtilityClass
public class JPAudioUtils {

	public static void playWavSound(URL clipFile) throws IOException,
			UnsupportedAudioFileException, LineUnavailableException,
			InterruptedException {
		JPAudioListener listener = new JPAudioListener();
		try (AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(clipFile);
			 Clip clip = AudioSystem.getClip();
		) {
			clip.addLineListener(listener);
			clip.open(audioInputStream);
			clip.start();
			listener.waitUntilDone();
		}
	}

	public static void playWavSound(InputStream clipFile) throws IOException,
			UnsupportedAudioFileException, LineUnavailableException,
			InterruptedException {
		JPAudioListener listener = new JPAudioListener();
		try (AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(clipFile);
				Clip clip = AudioSystem.getClip();
		) {
			clip.addLineListener(listener);
			clip.open(audioInputStream);
			clip.start();
			listener.waitUntilDone();
		}
	}
}
