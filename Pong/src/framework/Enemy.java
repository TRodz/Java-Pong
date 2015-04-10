package framework;

import java.awt.*;

public class Enemy {

    private Core core;
    private int x;
    private static final int width = 12;
    private static final int height = 60;
    public static int y=0;
    public static int dy=0;

    public Enemy(Core core) {
        this.core = core;
    }

    void update() {
        x = core.getWidth() - 30;
        if (y + dy > 0 && y + dy < core.getHeight() - height ) y+=dy;
    }

    public void paint(Graphics2D g) {
        g.fillRect(x, y, width, height);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

}