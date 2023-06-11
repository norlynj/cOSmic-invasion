package view;

import model.*;
import model.FlyingBoost;
import view.component.*;
import view.component.Frame;
import view.component.Label;
import view.component.Panel;
import view.component.AudioPlayer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;

public class Game extends view.component.Panel implements ActionListener, KeyListener, MouseListener, MouseMotionListener {
    public int screenW = this.getWidth(), screenH = this.getHeight();
    private Frame frame;
    private Virus[][] viruses;
    private Tux tux;
    private ArrayList<Blast> tuxBlasts, virusBlasts;
    private ArrayList<FlyingBoost> boost;
    private ArrayList<Explosion> explosions;
    private ArrayList<Message> messages;
    private JLabel levelLabel, livesLabel, killLabel, cutSceneImage, gameOverImage, successImage, correct, wrong, questionLabel, choiceALabel, choiceBLabel, choiceCLabel, choiceDLabel;
    private ImageIcon cutSceneBG;
    private ImageButton homeButton, musicOnButton, musicOffButton, pauseButton, playButton, pauseHomeButton, pauseExitButton, choiceAButton, choiceBButton, choiceCButton, choiceDButton;
    private JPanel questionPanel, questionWrapper, choicesPanel;
    private JScrollPane questionPane;
    private String[] levels = {"Level 1: System Startup", "Level 2: Malware Madness", "Level 3: System Shutdown"};
    private QuestionSheet questionSheet;
    Image memoryImage = new ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/elements/memory.png"))).getImage();

    private Timer t = new Timer(16, this);
    private int rewardTimer, currentLevel;
    private boolean playing, gameOver, boostHit = false, isCutsceneShowing = true, pauseClicked;

    // Sound
    AudioPlayer mainGameMusic = new AudioPlayer("maingame_bg.wav");
    AudioPlayer explosion = new AudioPlayer("explosion_bg.wav");
    AudioPlayer powerUp = new AudioPlayer("powerups_bg.wav");
    AudioPlayer levelUp = new AudioPlayer("levelup_bg.wav");
    AudioPlayer correctMusic = new AudioPlayer("correct.wav");
    AudioPlayer wrongMusic = new AudioPlayer("wrong.wav");
    AudioPlayer gameoverBg = new AudioPlayer("gameover_bg.wav");
    AudioPlayer success = new AudioPlayer("success_bg.wav");
    AudioPlayer laser = new AudioPlayer("laser.wav");

    public Game(Frame frame) {
        super("bg/lvl1-bg.png");
        this.frame = frame;

        t.start();
        questionSheet = new QuestionSheet();

        initializeLabels();
        initializeQuestionsPanel();
        initializeButtons();
        setListeners();
        addComponentsToFrame();
        setDoubleBuffered(true);

        musicOffButton.setVisible(false);
        questionPane.setVisible(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        drawButtonsAndsLabels();


        if (isCutsceneShowing || gameOver) {
            handleSpecialCases(g);
            return;
        }

        paintLivesandKills(g);
        updateBlastSpeedBar(g);

        if (boostHit) {
            handleBoostHit(g);
        } else {
            handleRegularGame(g);
        }

        Toolkit.getDefaultToolkit().sync();
    }

    private void handleSpecialCases(Graphics g) {
        if (gameOver) {
            gameOverImage.setVisible(true);
            gameoverBg.play();
            gameOverImage.paint(g);
        }
    }

    private void handleBoostHit(Graphics g) {
        drawSprites(g, true);
        questionPane.setVisible(true);
    }

    private void handleRegularGame(Graphics g) {
        questionPane.setVisible(false);
        if (!playing && isPlayerAlive() || pauseClicked) {
            drawSprites(g, true);
            return;
        }

        if (shouldStartNextLevel()) {
            return;
        }

        if (isPlayerAlive()) {
            updateGame(g);
        } else {
            gameOverImage.setVisible(true);
        }
    }

    private boolean shouldStartNextLevel() {
        if (tux.getKills() == 30 && currentLevel == 3) {
            successImage.setVisible(true);
            return true;
        }

        if (tux.getKills() == 20 && currentLevel == 2) {
            startGame(3);
            return true;
        }

        if (tux.getKills() == 15 && currentLevel == 1) {
            startGame(2);
            return true;
        }

        return false;
    }

    private boolean isPlayerAlive() {
        return tux.lives() > 0 && !gameOver;
    }

    private void updateGame(Graphics g) {
        drawSprites(g, false);
        removals();
        checkCollisions();
        updateRewardTimer();
    }

    private void drawSprites(Graphics g, boolean pause) {
        // paint messages
        g.setFont(new Font("Dialog", Font.PLAIN, 20));
        for (int i = 0; i < messages.size(); i++) {
            g.setColor(messages.get(i).color());
            g.drawString(messages.get(i).message(), 18, 160 + 20 * i);
            messages.get(i).incTime();
        }

        // paint explosions
        for (Explosion e : explosions) {
            e.setPaused(pause);
            e.paint(g);
        }

        // paint boost
        for (FlyingBoost f : boost) {
            f.setPaused(pause);
            f.paint(g);
        }

        // paint viruses
        for (Virus[] v1 : viruses) {
            for (Virus v : v1) {
                if (v.isAlive()) {
                    if (v.y() > screenH + 10) {
                        gameOver = true;
                    }
                    if (v.shoot() && !pause && !pauseClicked) { // prevent virus from creating a sblast while on pause
                        virusBlasts.add(new Blast(v.x() + 40, v.y() + 55, "spark", 1));
                    }
                    v.setPaused(pause);
                    v.paint(g);
                }
            }
        }

        // paint tuxBlasts
        for (Blast b : tuxBlasts) {
            b.setPaused(pause);
            b.paint(g);
        }

        // paint alien tuxBlasts
        for (Blast b : virusBlasts) {
            b.setPaused(pause);
            b.paint(g);
        }

        if (tux.checkShot()) {
            tuxBlasts.add(new Blast(tux.x() + 11, tux.y() + 60, "bit-0", 0));
            tuxBlasts.add(new Blast(tux.x() + 115, tux.y() + 60, "bit-1", 0));
            laser.play();
        }

        tux.setPaused(pause);
        tux.paint(g);
    }

    private void removals() {

        // removes expired messages
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


    private void checkCollisions() {
        // compare every alien with every tux blast
        for (int r = 0; r < viruses.length; r++) {
            for (int c = 0; c < viruses[r].length; c++) {
                Virus v = viruses[r][c];
                for (int i = 0; i < tuxBlasts.size(); i++) {
                    Blast b = tuxBlasts.get(i);
                    if (b.hit(v)) {
                        explosions.add(new Explosion(b));
                        explosion.play();
                        tuxBlasts.remove(i);
                        v.hit();
                        if (v.getShotsRequired() == 0 && v.isAlive()) {
                            v.setAlive(false);
                            v.moveOutOfScreen();
                            tux.increaseKills();
                            messages.add(new Message("Virus destroyed", Color.cyan));
                        }
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
                    boostHit = true;
                    powerUp.play();
                    updateQuestion();
                    checkAnswers(boost.get(x));
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
                shake();
                explosion.play();
                virusBlasts.remove(i);
                tux.hit();
                messages.add(new Message("Tux got hit", Color.RED));
                messages.add(new Message("Reload increased to " + tux.getReloadTime()[1], Color.RED));
                i--;
            }
        }
        if (!isPlayerAlive()) {
            mainGameMusic.stop();
            gameoverBg.play(); // play gameover
        } else if (tux.getKills() == 30 && currentLevel == 3) { // if tux finishes all levels
            mainGameMusic.stop();
            success.play();
        }
    }


    private void updateBlastSpeedBar(Graphics g) {
        g.setColor(new Color(130, 130, 130));
        g.fillRect(50 - 1, screenH - 150 - 1, tux.getReloadTime()[1] * (200 / tux.getReloadTime()[1]) + 2, 10 + 2);
        g.setColor(Color.cyan);
        g.fillRect(50, screenH - 150,
                (tux.getReloadTime()[1] - tux.getReloadTime()[0]) * (200 / tux.getReloadTime()[1]), 10);
    }

    private void paintLivesandKills(Graphics g) {
        // update kill
        g.setFont(new Font("Dialog", Font.PLAIN, 20));
        g.setColor(Color.GREEN);
        g.drawString(tux.getKills() + "", 120, 115);

        // update Lives
        Graphics2D g2d = (Graphics2D) g;
        g.setColor(Color.RED);
        for (int i = 0; i < tux.lives(); i++) { // for firewall shield token
            if (currentLevel != 3) {
                g2d.drawImage(memoryImage, 80 + 30 * i, 60, this);
            } else if ((i) % 2 == 0) {
                g2d.drawImage(memoryImage, 80 + 30 * i/2, 60, this);
            }
        }
    }

    private void updateRewardTimer() {
        rewardTimer++;

        if (rewardTimer / 300 == 1) {
            rewardTimer = 0;
            if (Math.random() > 0.5) { // show blast boost randomly
                boost.add(new Ammo());
            } else {
                int lifeThreshold = currentLevel == 3 ? 6 : 3;
                if (tux.lives() < lifeThreshold) { // show life boost when life is less than 3
                    boost.add(new Memory());
                }
            }
        }
    }

    public void startGame(int level) {
        currentLevel = level;

        pauseClicked = false;
        pauseHomeButton.setVisible(false);
        pauseExitButton.setVisible(false);
        pauseButton.setVisible(true);
        playButton.setVisible(false);
        musicOnButton.setVisible(true);
        musicOffButton.setVisible(false);

        // show level number
        if (currentLevel == 2 || currentLevel == 3) {
            cutSceneBG = new ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/bg/lvl" + currentLevel + "-cutscene.gif")));
        }
        cutSceneImage.setIcon(cutSceneBG);
        cutSceneImage.setVisible(true);
        isCutsceneShowing = true;
        levelUp.play();
        mainGameMusic.stop();
        Timer timer = new Timer(4000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cutSceneImage.setVisible(false);
                isCutsceneShowing = false;
                mainGameMusic.play();
                mainGameMusic.loop();
            }
        });
        timer.setRepeats(false);
        timer.start();

        // set bg image
        setImage("bg/lvl" + currentLevel + "-bg.png");
        gameOverImage.setVisible(false);
        successImage.setVisible(false);
        correct.setVisible(false);
        wrong.setVisible(false);

        // create new instance of game objects
        tux = new Tux(screenW / 2, 557, currentLevel);
        tuxBlasts = new ArrayList<Blast>();
        virusBlasts = new ArrayList<Blast>();
        boost = new ArrayList<FlyingBoost>();
        explosions = new ArrayList<Explosion>();
        messages = new ArrayList<Message>();

        rewardTimer = 0;
        playing = true;
        gameOver = false;


        // determine viruses on each level
        String[] colors = new String[]{"blue", "blue", "blue", "blue", "blue", "violet", "violet", "violet", "violet", "violet", "green", "green", "green", "green", "green"};
        levelLabel.setText(levels[0]);
        viruses = new Virus[5][3];

        if (level == 2) {
            viruses = new Virus[5][4];
            levelLabel.setText(levels[1]);
            colors = new String[]{"blue", "blue", "blue", "blue", "blue", "violet", "violet", "violet", "violet", "violet", "green", "green", "green", "green", "green", "yellow", "yellow", "yellow", "yellow", "yellow"};
        } else if (level == 3) {
            viruses = new Virus[10][3];
            levelLabel.setText(levels[2]);
            colors = new String[]{"blue", "blue", "blue", "blue", "blue", "violet", "violet", "violet", "violet", "violet", "green", "green", "green", "green", "green", "yellow", "yellow", "yellow", "yellow", "yellow", "orange", "orange", "orange", "orange", "orange", "red", "red", "red", "red", "red"};
        }
        ArrayList<String> colorList = new ArrayList<>(Arrays.asList(colors));
        Collections.shuffle(colorList);

        // populate viruses array
        for (int r = 0; r < viruses.length; r++) {
            for (int c = 0; c < viruses[r].length; c++) {
                String color = colorList.remove(0);
                viruses[r][c] = new Virus(100 * r + 280, 100 * c - 100, color, currentLevel);
            }
        }
    }

    private void initializeQuestionsPanel() {
        questionPanel = new Panel("bg/questions-bg.png");
        questionPanel.setLayout(new BoxLayout(questionPanel, BoxLayout.Y_AXIS));
        questionPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        questionLabel = new Label(questionSheet.question, true);
        questionLabel.setForeground(Color.WHITE);
        questionLabel.setSize(new Dimension(207, 28));

        choicesPanel = new JPanel();
        choicesPanel.setLayout(new BoxLayout(choicesPanel, BoxLayout.Y_AXIS));

        choiceALabel = new Label();
        choiceBLabel = new Label();
        choiceCLabel = new Label();
        choiceDLabel = new Label();

        choiceAButton = new ImageButton("buttons/A.png");
        choiceBButton = new ImageButton("buttons/B.png");
        choiceCButton = new ImageButton("buttons/C.png");
        choiceDButton = new ImageButton("buttons/D.png");
        choiceAButton.setName("A");
        choiceBButton.setName("B");
        choiceCButton.setName("C");
        choiceDButton.setName("D");

        JPanel choiceAPanel = createChoicePanel("A", questionSheet.choiceA, choiceAButton, choiceALabel);
        JPanel choiceBPanel = createChoicePanel("B", questionSheet.choiceB, choiceBButton, choiceBLabel);
        JPanel choiceCPanel = createChoicePanel("C", questionSheet.choiceC, choiceCButton, choiceCLabel);
        JPanel choiceDPanel = createChoicePanel("D", questionSheet.choiceD, choiceDButton, choiceDLabel);

        choicesPanel.add(Box.createVerticalStrut(10));
        choicesPanel.add(choiceAPanel);
        choicesPanel.add(choiceBPanel);
        choicesPanel.add(choiceCPanel);
        choicesPanel.add(choiceDPanel);
        choicesPanel.setOpaque(false);

        questionPanel.add(questionLabel);
        questionPanel.add(choicesPanel);
        questionPanel.setAlignmentX(SwingConstants.CENTER);

        questionPane = new JScrollPane(questionPanel);
        CustomScrollBar sbH = new CustomScrollBar();
        sbH.setOrientation(JScrollBar.HORIZONTAL);
        questionPane.setHorizontalScrollBar(sbH);
        questionPane.setBorder(BorderFactory.createEmptyBorder());
        questionPane.getViewport().setOpaque(false);
        questionPane.setOpaque(false);
        questionPane.setBounds(198, 140, 700, 400);
    }

    private JPanel createChoicePanel(String choice, String choiceText, ImageButton choiceButton, JLabel choiceLabel) {
        JPanel choicePanel = new JPanel();
        choicePanel.setOpaque(false);
        choicePanel.setLayout(new BoxLayout(choicePanel, BoxLayout.X_AXIS));
        choicePanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        choicePanel.setSize(new Dimension(700, 500));

        choicePanel.add(choiceButton);
        choicePanel.add(Box.createHorizontalStrut(20));  // Add horizontal spacing between the button and label

        choiceLabel.setText(choiceText);
        choicePanel.add(choiceLabel);

        return choicePanel;
    }

    private void checkAnswers(FlyingBoost fBoost) {

        ActionListener buttonActionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JButton clickedButton = (JButton) e.getSource();

                if (questionSheet.correctChoice.equals(clickedButton.getName())) {
                    if (fBoost != null && fBoost.isType("bullet")) {

                        tux.decreaseReloadTime();
                        messages.add(new Message("Reload decreased to " + tux.getReloadTime()[1], Color.GREEN));
                    } else if (fBoost != null && fBoost.isType("memory")) {
                        if (currentLevel == 3) {
                            tux.addLife(2);
                        } else {
                            tux.addLife(1);
                        }
                        messages.add(new Message("Memory increased", Color.GREEN));
                    }
                    correctMusic.play();
                } else {
                    System.out.println("wrong");
                    messages.add(new Message("Wrong Answer. You didn't get the boost", Color.RED));
                    wrongMusic.play();
                }
                boostHit = false;
                // Remove the action listener from the buttons
                choiceAButton.removeActionListener(this);
                choiceBButton.removeActionListener(this);
                choiceCButton.removeActionListener(this);
                choiceDButton.removeActionListener(this);
                playing = true;
            }
        };

        choiceAButton.addActionListener(buttonActionListener);
        choiceBButton.addActionListener(buttonActionListener);
        choiceCButton.addActionListener(buttonActionListener);
        choiceDButton.addActionListener(buttonActionListener);
    }

    private void updateQuestion() {
        questionSheet.getRandomQuestion();
        questionLabel.setText(questionSheet.question);
        choiceALabel.setText(questionSheet.choiceA);
        choiceBLabel.setText(questionSheet.choiceB);
        choiceCLabel.setText(questionSheet.choiceC);
        choiceDLabel.setText(questionSheet.choiceD);
    }

    private void initializeLabels() {
        levelLabel = new view.component.Label(levels[0]);
        livesLabel = new view.component.Label("Lives: ");
        killLabel = new Label("Kills: ");
        cutSceneBG = new ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/bg/lvl1-cutscene.png")));

        cutSceneImage = new JLabel();
        cutSceneImage.setBounds(0, 0, 1100, 800);
        cutSceneImage.setIcon(cutSceneBG);

        gameOverImage = new JLabel();
        gameOverImage.setBounds(0, 0, 1100, 800);
        gameOverImage.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/bg/gameover.gif"))));

        successImage = new JLabel();
        successImage.setBounds(0, 0, 1100, 800);
        successImage.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/bg/success.gif"))));

        correct = new JLabel();
        correct.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/bg/correct.png"))));

        wrong = new JLabel();
        wrong.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/bg/wrong.png"))));
    }

    private void initializeButtons() {
        homeButton = new ImageButton("buttons/home.png");
        musicOnButton = new ImageButton("buttons/music-on.png");
        musicOffButton = new ImageButton("buttons/music-off.png");
        pauseButton = new ImageButton("buttons/pause.png");
        playButton = new ImageButton("buttons/play.png");
        pauseHomeButton = new ImageButton("buttons/home-cutscene.png");
        pauseExitButton = new ImageButton("buttons/exit-cutscene.png");
    }

    private void setListeners(){
        homeButton.hover("buttons/home-hover.png", "buttons/home.png");
        musicOnButton.hover("buttons/music-off-hover.png", "buttons/music-on.png");
        musicOffButton.hover("buttons/music-on-hover.png", "buttons/music-off.png");
        pauseButton.hover("buttons/pause-hover.png", "buttons/pause.png");
        playButton.hover("buttons/play-hover.png", "buttons/play.png");
        pauseHomeButton.hover("buttons/home-cutscene-hover.png", "buttons/home-cutscene.png");
        pauseExitButton.hover("buttons/exit-cutscene-hover.png", "buttons/exit-cutscene.png");

        pauseButton.addActionListener( e -> {
            pauseClicked = true;
            System.out.println("pause button clicked");
            pauseHomeButton.setVisible(true);
            pauseExitButton.setVisible(true);
            pauseButton.setVisible(false);
            playButton.setVisible(true);
        });

        playButton.addActionListener(e -> {
            pauseClicked = false;
            System.out.println("play button clicked");
            pauseHomeButton.setVisible(false);
            pauseExitButton.setVisible(false);
            pauseButton.setVisible(true);
            playButton.setVisible(false);
        });
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
        this.add(homeButton);
        this.add(musicOnButton);
        this.add(musicOffButton);
        this.add(pauseButton);
        this.add(playButton);
        this.add(successImage);
        this.add(gameOverImage);
        this.add(cutSceneImage);
        this.add(questionPane);

        this.add(pauseHomeButton);
        this.add(pauseExitButton);
        this.add(levelLabel);
        this.add(livesLabel);
        this.add(killLabel);
        this.add(correct);
        this.add(wrong);

        this.addMouseListener(this);
        this.addMouseMotionListener(this);
    }

    private void drawButtonsAndsLabels() {
        levelLabel.setBounds(18, 25, 370, 33);
        livesLabel.setBounds(18, 60, 100, 28);
        killLabel.setBounds(18, 95, 100, 28);
        homeButton.setBounds(screenW - 180, 22, 40, 54);
        musicOnButton.setBounds(screenW - 100, 22, 40, 54);
        musicOffButton.setBounds(screenW - 100, 22, 40, 54);
        pauseButton.setBounds(screenW - 180, 30, 44, 44);
        playButton.setBounds(screenW - 180, 30, 44, 44);
        pauseHomeButton.setBounds(326, 400, 250, 65);
        pauseExitButton.setBounds(587, 400, 250, 65);


        if (successImage.isVisible() || gameOverImage.isVisible()) {
            homeButton.setVisible(true);
            pauseButton.setVisible(false);
            playButton.setVisible(false);
        } else {
            homeButton.setVisible(false);
        }

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
                if (tux!=null) {
                    tux.setShooting(true);
                }
                break;
            case 27: // ESC
                startGame(1);
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
                if (tux!=null) {
                    tux.setShooting(false);
                }
                break;
            default:
                break;
        }
    }

    public void keyTyped(KeyEvent arg0) {

    }

    public void mouseClicked(MouseEvent e) {

    }

    public void mouseEntered(MouseEvent arg0) {
        playing = true;
    }

    public void mouseExited(MouseEvent arg0) {
        playing = false;
    }

    @Override
    public void mousePressed(MouseEvent arg0) {
        if (tux != null) {
            tux.setShooting(true);
        }
    }

    @Override
    public void mouseReleased(MouseEvent arg0) {
        if (tux != null) {
            tux.setShooting(false);
        }
    }

    public static void main(String[] args) {
        view.component.Frame frame = new Frame("Menu Panel");
        Game m = new Game(frame);
        frame.add(m);
        frame.setVisible(true);
    }

    public ImageButton getHomeButton() {
        return homeButton;
    }

    public ImageButton getMusicOnButton() {
        return musicOnButton;
    }

    public ImageButton getMusicOffButton() {
        return musicOffButton;
    }

    public ImageButton getPauseHomeButton() {
        return pauseHomeButton;
    }

    public ImageButton getPauseExitButton() {
        return pauseExitButton;
    }

    private void shake() {

        int vibrationLength = 7;
        int vibrationSpeed = 2;
        try {
            final int originalX = frame.getLocationOnScreen().x;
            final int originalY = frame.getLocationOnScreen().y;
            for (int i = 0; i < vibrationLength; i++) {
                Thread.sleep(10);
                frame.setLocation(originalX, originalY + vibrationSpeed);
                Thread.sleep(10);
                frame.setLocation(originalX, originalY - vibrationSpeed);
                Thread.sleep(10);
                frame.setLocation(originalX + vibrationSpeed, originalY);
                Thread.sleep(10);
                frame.setLocation(originalX, originalY);
            }
        }

        catch (Exception err) {
            err.printStackTrace();
        }
    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {
        int mouseX = mouseEvent.getX();
        tux.x(mouseX);
    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {
        int mouseX = mouseEvent.getX();
        tux.x(mouseX);
    }
}
