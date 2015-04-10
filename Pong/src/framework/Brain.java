package framework;

import javax.swing.*;
import java.awt.*;

public class Brain {

    // 22 IS THE MAGIC NUMBER

    public Brain(int w, int h, String t, Core core) {
        JFrame ventana = new JFrame(t);
        // In mac, we add 22 to the size
        ventana.setPreferredSize(new Dimension(w, h + 22));
        ventana.setMaximumSize(new Dimension(w, h   + 22));
        ventana.setMinimumSize(new Dimension(w, h   + 22));
        ventana.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        ventana.setResizable(false);
        ventana.setLocationRelativeTo(null);
        ventana.add(core);
        ventana.setVisible(true);
    }

}