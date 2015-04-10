package fx;

import javax.swing.*;
import java.awt.*;

/**
 * Pong
 * Creado por Juan Rodríguez el día 8/31/14 a las 12:01 PM.
 */

public class Images {

    private ImageIcon bg, ab, cld, tm, hw;

    // Acceso publico de imgages
    public Image background, about, clouds, tim, howto;
    public void loadImages() {
        bg = new ImageIcon(getClass().getResource("/intro-screen.png"));
        background = bg.getImage();
        ab = new ImageIcon(getClass().getResource("/About.png"));
        about = ab.getImage();
        cld = new ImageIcon(getClass().getResource("/Clouds.png"));
        clouds = cld.getImage();
        tm = new ImageIcon(getClass().getResource("/Tim.png"));
        tim = tm.getImage();
        hw = new ImageIcon(getClass().getResource("/howto.png"));
        howto = hw.getImage();
    }

}