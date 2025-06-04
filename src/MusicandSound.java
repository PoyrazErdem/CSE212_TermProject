import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class MusicandSound {
    private Clip clip;
    private int pausePosition = 0;

    public void playMusic(String resourcePath, boolean loop) {
        try {
            if (clip != null && clip.isOpen()) {
                clip.close(); // Stop existing music
            }

            URL url = getClass().getResource(resourcePath);
            if (url == null) {
                System.out.println("Could not find resource: " + resourcePath);
                return;
            }

            AudioInputStream audioStream = AudioSystem.getAudioInputStream(url);
            clip = AudioSystem.getClip();
            clip.open(audioStream);

            if (loop) {
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            } else {
                clip.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stopMusic() {
        if (clip != null && clip.isRunning()) {
            pausePosition = clip.getFramePosition(); // Save position
            clip.stop();
        }
    }

    public void resumeMusic() {
        if (clip != null && !clip.isRunning()) {
            clip.setFramePosition(pausePosition);
            clip.start();
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

    public void closeMusic() {
        if (clip != null) {
            clip.stop();
            clip.close();
        }
    }
}
