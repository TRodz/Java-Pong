package fx;

import java.applet.Applet;
import java.applet.AudioClip;

public class Sound {

    public static final AudioClip select = Applet.newAudioClip(Sound.class.getResource("/select.wav"));
    public static final AudioClip start = Applet.newAudioClip(Sound.class.getResource("/Powerup.wav"));
    public static final AudioClip hit = Applet.newAudioClip(Sound.class.getResource("/Powerup5.wav"));
    public static final AudioClip gameOver = Applet.newAudioClip(Sound.class.getResource("/gameOver.wav"));
    public static final AudioClip music = Applet.newAudioClip(Sound.class.getResource("/Reckless.wav"));

}