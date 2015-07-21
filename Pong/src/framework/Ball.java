package framework;

import fx.Sound;

import java.awt.*;

public class Ball {

    private Core core;
    private int dx = 1, dy = -1, hitdx = 2, hitdy = 3;
    private static final int diameter = 15;

    // Puntaje de ambos, posicion de la pelota para AI
    public int scoreO = 0, scoreT = 0, lastScoreO, lastScoreT, currentPosition;

    // La pelota aparece en una posicion (x, y) cualquiera
    private int x = (int) ((200 * Math.random()) + 400);
    private int y = (int) ((200 * Math.random()) + 200);

    public Ball(Core core) {
            this.core = core;
    }

    public void update() {
        // Esto sucedera mientras estes jugando
        if (core.state == Core.GameState.P1) {
            if (x + dx < 0) {
                Sound.gameOver.play();
                lastScoreO = scoreO;
                core.state = Core.GameState.lose;
                reset();
            }
            if (x + dx > core.getWidth() - diameter) {
                Sound.gameOver.play();
                scoreO++;
                lastScoreO = scoreO;
                core.state = Core.GameState.win;
                reset();
            }
            if (y + dy < 0) {
                Sound.hit.play();
                dy = 1;
            }
            if (y + dy > core.getHeight() - diameter) {
                Sound.hit.play();
                dy = (int) -1.5;
            }

            x += dx * hitdx;
            y += dy * hitdy;
            currentPosition = y;
            
            // Si esta por mas de la mitad y la y es igual o menor menos el random que la pelota:
            if (x >= core.getWidth() / 2 && Enemy.y <= currentPosition - (Math.random() * 36 + 20)) Enemy.dy = 7;
            else Enemy.dy = - 7;
            // Cuando se pase de la mitad - 100 la dy del enemigo sera 0
            if (x < core.getWidth() / 2 - 100) Enemy.dy = 0;

            if (collisionPO()) {
                core.setBackground(core.color());
                Sound.hit.play();
                dx = 1;
                hitdx = (int) 5.5;
                hitdy = 5;
                scoreO++;
            }
            if (collisionPT()) {
                core.setBackground(core.color());
                Sound.hit.play();
                dx = - 1;
                hitdx = (int) 6.5;
                hitdy = 5;
                scoreT++;
            }

            if (scoreO >= 3 && scoreO <= 6 && scoreT >= 4 && scoreT <= 6) {
                core.setBackground(core.color());
            }
        }

        /** TWO PLAYERS **/
        else if (core.state == Core.GameState.P2) {
            if (x + dx < 0) {
//                Sound.gameOver.play();
                scoreT++;
                lastScoreT = scoreT;
                core.state = Core.GameState.GOP2;
                reset();
            }
            if (x + dx > core.getWidth() - diameter) {
//                Sound.gameOver.play();
                scoreO++;
                lastScoreO = scoreO;
                core.state = Core.GameState.GOP1;
                reset();
            }
            if (y + dy < 0) dy = 1;
            if (y + dy > core.getHeight() - diameter) dy = (int) - 1.5;

            x += dx * hitdx;
            y += dy * hitdy;
            currentPosition = y;

            if (collisionPO()) {
                core.setBackground(core.color());
                Sound.hit.play();
                dx = 1;
                hitdx = (int) 5.5;
                hitdy = 5;
                scoreO++;
            }
            if (collisionPT()) {
                core.setBackground(core.color());
                Sound.hit.play();
                dx = - 1;
                hitdx = (int) 6.5;
                hitdy = 5;
                scoreT++;
            }

            if (scoreO >= 3 && scoreO <= 6 && scoreT >= 4 && scoreT <= 6) {
                core.setBackground(core.color());
            }
        }
    }

    private void reset() {
        x = core.getWidth() / 2;
        y = x = core.getHeight() / 2;
        dx = 1;
        dy = -1;
        hitdx = 2;
        hitdy = 3;
        scoreO = 0;
        scoreT = 0;
    }


    public void paint(Graphics2D g) {
        g.fillRect(x, y, diameter, diameter);
        g.drawString(String.valueOf(scoreO), core.getWidth() / 2 - 70, 40);
        g.drawString(String.valueOf(scoreT), core.getWidth() / 2 + 30, 40);
    }

    public boolean collisionPO() {
        return core.Players.getBounds().intersects(getBounds());
    }

    public boolean collisionPT() {
        return core.enemy.getBounds().intersects(getBounds());
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, diameter, diameter);
    }

}