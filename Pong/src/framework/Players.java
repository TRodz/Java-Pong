package framework;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Players {

    private Core core;
    private static final int x = 20;
    private static final int width = 12;
    private static final int height = 60;
    public int y=0, dy=0;

    public Players(Core core) {
        this.core = core;
    }

    void update() {
        if (y + dy > 0 && y + dy < core.getHeight() - height ) y+=dy;
        if (core.state == Core.GameState.GOP2 
                || core.state == Core.GameState.GOP1
                || core.state == Core.GameState.win 
                || core.state == Core.GameState.lose) {
            y = 0;
            Enemy.y = 0;
        }
    }

    public void paint(Graphics2D g) {
        g.fillRect(x, y, width, height);
    }

    public void keyReleased(KeyEvent e) {
        if (core.state == Core.GameState.P1) dy = 0;
        if (core.state == Core.GameState.P2) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_UP:
                    Enemy.dy = 0;
                    break;
                case KeyEvent.VK_DOWN:
                    Enemy.dy = 0;
                    break;
                case KeyEvent.VK_W:
                    dy = 0;
                    break;
                case KeyEvent.VK_S:
                    dy = 0;
                    break;
            }
        }
    }

    public void keyPressed(KeyEvent e) {
        if (core.state == Core.GameState.P1) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                dy = - 7;
                break;
            case KeyEvent.VK_DOWN:
                dy = 7;
                break;
        }
        } else if (core.state == Core.GameState.P2) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_W:
                    dy = -7;
                    break;
                case KeyEvent.VK_S:
                    dy = 7;
                    break;
                case KeyEvent.VK_UP:
                    Enemy.dy = -7;
                    break;
                case KeyEvent.VK_DOWN:
                    Enemy.dy = 7;
                    break;
            }
        }

    }

    // Obtener limites del objeto
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
}