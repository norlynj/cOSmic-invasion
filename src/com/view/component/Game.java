package view.component;

import model.*;
import model.FlyingBoost;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Game extends Panel implements ActionListener, KeyListener, MouseListener {
    public int screenW = this.getWidth(), screenH = this.getHeight();
    Virus[][] viruses;
    Tux tux;
    ArrayList<Blast> tuxBlasts, virusBlasts;
    ArrayList<FlyingBoost> boost;
    ArrayList<Explosion> explosions;
    ArrayList<Message> messages;

    Timer t = new Timer(16, this);
    int rewardTimer;
    boolean playing, gameOver;
    boolean bossFight;

    private ImageButton musicOnButton, musicOffButton, pauseButton;
    public Game() {

        super("bg/game-panel.png");
        t.start();
        generate();
        initializeButtons();
        setListeners();
        addComponentsToFrame();

    }

    private void initializeButtons() {
        musicOnButton = new ImageButton("buttons/music-on.png");
        musicOffButton = new ImageButton("buttons/music-off.png");
        pauseButton = new ImageButton("buttons/pause.png");

        musicOnButton.setBounds(939, 22, 40, 54);
        musicOffButton.setBounds(939, 22, 40, 54);
        pauseButton.setBounds(995, 30, 73, 40);

    }

    private void setListeners(){
        musicOnButton.hover("buttons/music-off-hover.png", "buttons/music-on.png");
        musicOffButton.hover("buttons/music-on-hover.png", "buttons/music-off.png");
        pauseButton.hover("buttons/pause-hover.png", "buttons/pause.png");

        musicOnButton.addActionListener(e -> {
            musicOnButton.setVisible(false);
            musicOffButton.setVisible(true);
        });

        musicOffButton.addActionListener(e -> {
            musicOffButton.setVisible(false);
            musicOnButton.setVisible(true);
            // Handle music off button click
        });
    }

    private void addComponentsToFrame() {
        this.add(musicOffButton);
        this.add(musicOnButton);
        this.add(pauseButton);
    }

    public void paint(Graphics g) {
        super.paintComponent(g);

        // END SCREEN
        if (tux.lives() <= 0 || gameOver) {
            g.setColor(Color.WHITE);
            g.setFont(new Font("Dialog", Font.PLAIN, 50));
            g.drawString("Game Over", screenW / 2 - 150, screenH / 2);
            g.setFont(new Font("Dialog", Font.PLAIN, 25));
            g.drawString("Press ESC to restart.", screenW / 2 - 140, screenH / 2 + 150);
            return;
        }

        if (!playing) {
            return;
        }
        g.setFont(new Font("Dialog", Font.PLAIN, 20));

        rewardTimer++;
        if (rewardTimer / 400 == 1) {
            rewardTimer = 0;
            if (Math.random() > 0.5) {
                boost.add(new Ammo());
            } else {
                if (tux.lives() < 10) {
                    boost.add(new Memory());
                }
            }
        }

        removals();

        paints(g);

        comparisons();

        // cooldown bar
        g.setColor(new Color(130, 130, 130));
        g.fillRect(50 - 1, screenH - 100 - 1, tux.getCooldown()[1] * (200 / tux.getCooldown()[1]) + 2, 10 + 2);
        g.setColor(Color.BLUE);
        g.fillRect(50, screenH - 100,
                (tux.getCooldown()[1] - tux.getCooldown()[0]) * (200 / tux.getCooldown()[1]), 10);

        // kill bar
        g.setColor(Color.GREEN);
        g.drawString(tux.getKills() + "", 1610, 990);

        g.setColor(Color.RED);
        for (int i = 0; i < tux.lives(); i++) {
            g.drawOval(1700 + 20 * i, 980, 10, 10);
        }
    }

    private void removals() {

        // removes expired explosions
        for (int i = 0; i < messages.size(); i++) {
            if (messages.get(i).time() <= 0) {
                messages.remove(i);
                i--;
            }
        }

        // removes expired explosions
        for (int i = 0; i < explosions.size(); i++) {
            if (explosions.get(i).time() <= 0) {
                explosions.remove(i);
                i--;
            }
        }

        // removes tuxBlasts outside of screen
        for (int i = 0; i < tuxBlasts.size(); i++) {
            if (tuxBlasts.get(i).y() < -50) {
                tuxBlasts.remove(i);
                i--;
            }
        }
        for (int i = 0; i < virusBlasts.size(); i++) {
            if (virusBlasts.get(i).y() > screenH + 50) {
                virusBlasts.remove(i);
                i--;
            }
        }

        // removes boost outside of screen
        for (int i = 0; i < boost.size(); i++) {
            if (boost.get(i).x() > screenW + 50) {
                boost.remove(i);
                i--;
            }
        }
    }

    private void paints(Graphics g) {

        for (int i = 0; i < messages.size(); i++) {
            g.setColor(messages.get(i).color());
            g.drawString(messages.get(i).message(), 10, 20 + 20 * i);
            messages.get(i).incTime();
        }

        // paint explosions
        for (Explosion e : explosions) {
            e.paint(g);
        }

        // paint boost
        for (FlyingBoost f : boost) {
            f.paint(g);
        }

        // paint viruses
        for (Virus[] a1 : viruses) {
            for (Virus a : a1) {
                if (a.y() > screenH + 10) {
                    // explosionSound.play();
                    // explosionSound.play();
                    gameOver = true;
                }
                if (a.shoot()) {
                    virusBlasts.add(new Blast(a.x() + 40, a.y() + 55, "spark", 1)); // alien center is +40,+55
                }
                a.paint(g);
            }
        }

        // paint tux tuxBlasts
        for (Blast b : tuxBlasts) {
            b.paint(g);
        }

        // paint alien tuxBlasts
        for (Blast b : virusBlasts) {
            b.paint(g);
        }

        if (tux.checkShot()) {
            tuxBlasts.add(new Blast(tux.x() + 11, tux.y() + 60, "bit-0", 0));
            tuxBlasts.add(new Blast(tux.x() + 115, tux.y() + 60, "bit-1", 0));
            tux.incShotsFired();
        }

        tux.paint(g);
    }

    private void comparisons() {
        // compare every alien with every tux blast
        for (int r = 0; r < viruses.length; r++) {
            for (int c = 0; c < viruses[r].length; c++) {
                Virus a = viruses[r][c];
                for (int i = 0; i < tuxBlasts.size(); i++) {
                    Blast b = tuxBlasts.get(i);
                    if (b.hit(a)) {
                        explosions.add(new Explosion(b));
                        //explosionSound.play();
                        tuxBlasts.remove(i);
                        a.respawn();
                        tux.incKills();

                        messages.add(new Message("Virus destroyed", Color.cyan));
                        i--;
                    }
                }
            }
        }

        // compare every reward with every blast
        for (int i = 0; i < tuxBlasts.size(); i++) {
            Blast b = tuxBlasts.get(i);
            for (int x = 0; x < boost.size(); x++) {
                if (b.hit(boost.get(x))) {
                    if (boost.get(x).isType("bullet")) {
                        tux.decreaseCooldown();
                        messages.add(new Message("Reload decreased to " + tux.getCooldown()[1], Color.GREEN));
                    } else if (boost.get(x).isType("memory")) {
                        tux.lives(1);
                        messages.add(new Message("Memory increased", Color.GREEN));
                    }
                    tuxBlasts.remove(i);
                    boost.remove(x);
                    x--;
                    i--;
                }
            }
        }

        // compare tux with every blast
        for (int i = 0; i < virusBlasts.size(); i++) {
            Blast b = virusBlasts.get(i);
            if (b.hit(tux)) {
                explosions.add(new Explosion(b));
                //explosionSound.play();
                virusBlasts.remove(i);
                tux.hit();
                messages.add(new Message("Tux got hit", Color.RED));
                i--;
            }
        }
    }

    public void generate() {
        viruses = new Virus[5][3];
        String[] colors = {"blue", "blue", "blue", "blue", "blue", "violet", "violet", "violet", "violet", "violet", "green", "green", "green", "green", "green"};
        ArrayList<String> colorList = new ArrayList<>(Arrays.asList(colors));
        Collections.shuffle(colorList);

        // populate viruses array
        for (int r = 0; r < viruses.length; r++) {
            for (int c = 0; c < viruses[r].length; c++) {
                String color = colorList.remove(0);
                viruses[r][c] = new Virus(100 * r + 280, 100 * c - 150, color);
            }
        }

        tux = new Tux(screenW / 2, 557);
        tuxBlasts = new ArrayList<Blast>();
        virusBlasts = new ArrayList<Blast>();
        boost = new ArrayList<FlyingBoost>();
        explosions = new ArrayList<Explosion>();
        messages = new ArrayList<Message>();

        rewardTimer = 0;
        playing = true;
        gameOver = false;
    }



    public void actionPerformed(ActionEvent m) {
        repaint();
    }

    public void keyPressed(KeyEvent arg0) {
        switch (arg0.getKeyCode()) {
            case 37: // left
                tux.motion(-1);
                break;
            case 39: // right
                tux.motion(1);
                break;
            case 32: // space
                // was 68, not 60
                tux.setShooting(true);
                break;
            case 27: // ESC
                generate();
                break;
            default:
//			 System.out.println("Unrecognized, key code: " + arg0.getKeyCode());
                break;
        }
    }

    public void keyReleased(KeyEvent arg0) {
        switch (arg0.getKeyCode()) {
            case 37: // left
                if (tux.vx() == -1) {
                    tux.motion(0);
                }
                break;
            case 39: // right
                if (tux.vx() == 1) {
                    tux.motion(0);
                }
                break;
            case 32: // space
                tux.setShooting(false);
                break;
            default:
                break;
        }
    }

    public void keyTyped(KeyEvent arg0) {

    }

    public void mouseClicked(MouseEvent arg0) {
        System.out.println(arg0);
    }

    public void mouseEntered(MouseEvent arg0) {
        playing = true;
    }

    public void mouseExited(MouseEvent arg0) {
        playing = false;
    }

    public void mousePressed(MouseEvent arg0) {

    }

    public void mouseReleased(MouseEvent arg0) {

    }
}
