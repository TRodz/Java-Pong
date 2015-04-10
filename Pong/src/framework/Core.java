package framework;

import fx.Images;
import fx.Sound;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.io.InputStream;

import static java.awt.Color.black;
import static java.awt.Color.white;

public class Core extends JPanel implements Runnable, KeyListener {

    Players Players = new Players(this);
    Images bg = new Images();
    Enemy enemy = new Enemy(this);
    Ball ball = new Ball(this);
    Cloud c1 = new Cloud(0, 0);
    Cloud c2 = new Cloud(900, 0);

    // Dimensions
    private static final int width = 400, height = 300, scale = 2;

    // Extras
    int menuOptions = 0;
    Font wonder40;

    // Poorly made buffering strat.
    private Thread thread;
    boolean running = false;
    private final int FPS = 60;
    private final long targetTime = 1000 / FPS;

    // Menu options
    String[] options = {
            "1P", "2P", "How to", "Info","Salir"
    };

    // Para manejar los estados del juego
    enum GameState {
        Menu, P1, P2, about, instructions, GOP2, GOP1, win, lose;
    }
    GameState state = GameState.Menu;
    
    

    /*** Game initialization ***/

    public Core() {
        super();
        new Brain(width * scale, height * scale, "Pong, un juego por Juan Rodríguez", this);
        setFocusable(true);
//        addKeyListener(this);
        requestFocus();
    }

    public static void main(String[] arg) {
        new Core();
    }

    @Override
    public void run() {

        long start;
        long elapsed;
        long wait;

        if (state == GameState.Menu) {

            while (running) {
                repaint();
                start = System.nanoTime();
                elapsed = System.nanoTime() - start;
                wait = targetTime - elapsed / 1000000;
                if(wait < 0) wait = 5;
                if (state == GameState.P1 || state == GameState.P2) update();
                try {
                    Thread.sleep(wait);
                }
                catch(InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void addNotify() {
        super.addNotify();
        if (running) return;
        if (thread == null) {
            thread = new Thread(this);
            addKeyListener(this);
            thread.start();
            running = true;
        }
    }
    /**
    public synchronized void start() {
        // Si ya está corriendo no crear otro thread (causa bugs).
        if (running) return;
        // Si no existe, crearlo C:
        if (thread == null) {
            thread = new Thread(this);
            thread.start();
            running = true;
        }
    }*/

    public synchronized void stop() {
        try {
            thread.join();
            running = false;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /*** Game initialization ***/






    /** Painting **/

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        bg.loadImages();
        g2d.setFont(getWonder40());
        Color aqua = new Color(60, 107, 107);
        /** Menu **/
        if (state == GameState.Menu) {

            g2d.drawImage(bg.background, 0, 0, width * scale, height * scale, this);

            // Drawing menu options
            for (int i = 0; i < options.length; i++){

                if (i == menuOptions) g2d.setColor(aqua);
                else g2d.setColor(white);
                FontMetrics fm = g2d.getFontMetrics();
                Dimension d = getSize();
                // Centre the text
                int xc;
                xc = (d.width / 2 - fm.stringWidth(options[i]) / 2);
                g2d.drawString(options[i],xc, (int) (getHeight() / 2.7 + i * 50));
            }
        }

        /** 1P & 2P **/
        if (state == GameState.P1 || state == GameState.P2) {
            g2d.setBackground(black);
            g2d.setColor(white);
            ball.paint(g2d);
            Players.paint(g2d);
            enemy.paint(g2d);
        }

        /** Instructions **/
        if (state == GameState.instructions) {
            g2d.drawImage(bg.howto, 0, 0, 400 * scale, 300 * scale, this);
        }

        /** Info **/
        if (state == GameState.about) {
            c1.update();
            c2.update();
            // Este orden define la profundidad de los personajes
            g2d.drawImage(bg.about, 0, 0, 400 * scale, 300 * scale, this);
            g2d.drawImage(bg.clouds, c1.getcX(), c1.getcY(), 400 * scale, 300 * scale, this);
            g2d.drawImage(bg.clouds, c2.getcX(), c2.getcY(), 400 * scale, 300 * scale, this);
            g2d.drawImage(bg.tim, 0, 0, 400 * scale, 300 * scale, this);
        }

        /** Win **/
        if (state == GameState.win) {
            g2d.setColor(white);
            Sound.music.stop();
            g2d.drawString("Ganaste", (int) (getWidth() / 3.5), getHeight() / 2 - 50);
            g2d.drawString("Con " + ball.lastScoreO + " puntos", (float) (getWidth() / 4.5), getHeight() / 2 + 50);
        }

        /** Lose **/
        if (state == GameState.lose) {
            g2d.setColor(white);
            Sound.music.stop();
            g2d.drawString("Perdiste", (int) (getWidth() / 3.5), getHeight() / 2 - 50);
            g2d.drawString("Con " + ball.lastScoreO + " puntos", (float) (getWidth() / 4.5), getHeight() / 2 + 50);
        }

        /** player one wins **/
        if (state == GameState.GOP1) {
            g2d.setColor(white);
            Sound.music.stop();
            g2d.drawString("Gana el jugador 1", (getWidth() / 9), getHeight() / 2 - 50);
            g2d.drawString("Con " + ball.lastScoreO + " puntos", (float) (getWidth() / 4.5), getHeight() / 2 + 50);
        }

        /** player two wins **/
        if (state == GameState.GOP2) {
            g2d.setColor(white);
            Sound.music.stop();
            g2d.drawString("Gana el jugador 2", (getWidth() / 9), getHeight() / 2 - 50);
            g2d.drawString("Con " + ball.lastScoreT + " puntos", (float) (getWidth() / 4.5), getHeight() / 2 + 50);
        }
    }

    private void menuChoice() {
        if (menuOptions == 0) {
            Sound.music.play();
            state = GameState.P1;
        }
        if (menuOptions == 1) {
            Sound.music.play();
            state = GameState.P2;
        }
        if (menuOptions == 2) {
            state = GameState.instructions;
        }
        if (menuOptions == 3) {
            state = GameState.about;
        }
        if (menuOptions == 4) {
            System.exit(0);
            stop();
        }
    }

    public void update() {
        ball.update();
        Players.update();
        enemy.update();
    }

    /*** Menu control ***/
    private void menuKey(int k) {
        if (state == GameState.Menu && k == KeyEvent.VK_ENTER) {
            Sound.start.play();
            menuChoice();
        }
        if (k == KeyEvent.VK_UP) {
            Sound.select.play();
            menuOptions--;
            if (menuOptions == - 1) {
                menuOptions = options.length - 1;
            }
        }
        if (k == KeyEvent.VK_DOWN) {
            Sound.select.play();
            menuOptions++;
            if (menuOptions == options.length) {
                menuOptions = 0;
            }
        }
    }

    private void backToMenuKey(int k) {
        if (k == KeyEvent.VK_ESCAPE) {
            state = GameState.Menu;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) { /* Do not touch */ }

    @Override
    public void keyPressed(KeyEvent e) {
        if (state == GameState.Menu) menuKey(e.getKeyCode());
        if (state == GameState.P1 || state == GameState.P2) Players.keyPressed(e);
        if (state == GameState.GOP2 || state == GameState.GOP1 || state == GameState.instructions
         || state == GameState.lose || state == GameState.win || state == GameState.about) backToMenuKey(e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e) {
        Players.keyReleased(e);
    }


    /** Aesthetics */

    public Font getWonder40() {
        InputStream is = Font.class.getResourceAsStream("/8bitwonder.TTF");
        try {
            wonder40 = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(Font.BOLD, 40f);
        } catch (FontFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return wonder40;
    }

    public Color color() {
        int r, g, b;
        r = (int) (255 * Math.random());
        g = (int) (255 * Math.random());
        b = (int) (255 * Math.random());
        return new Color(r, g, b);
    }

} /*** FIN ***/