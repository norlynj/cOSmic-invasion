package view.component;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AudioPlayer {
    private Clip clip;

    public AudioPlayer(String soundName) {

        URL path = getClass().getResource("/resources/sounds/" +soundName);
        Line.Info linfo = new Line.Info(Clip.class);
        Line line;
        try {
            line = AudioSystem.getLine(linfo);
            clip = (Clip) line;
            AudioInputStream ais;
            ais = AudioSystem.getAudioInputStream(path);
            clip.open(ais);

        } catch (UnsupportedAudioFileException | IOException ex) {
            Logger.getLogger(AudioPlayer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (LineUnavailableException ex) {
            Logger.getLogger(AudioPlayer.class.getName()).log(Level.SEVERE, null, ex);
            Logger.getLogger(AudioPlayer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //audio starts playing
    public void play() {
        clip.start();

    }

    //plays the audio repeatedly
    public void loop() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }


    //the audio stops playing
    public void stop() {
        clip.stop();

    }

    public boolean isPlaying() {
        return clip.isActive();
    }
}
