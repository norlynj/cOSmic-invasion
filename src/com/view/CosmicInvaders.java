package view;
import view.component.AudioPlayer;
import view.component.Frame;
import view.component.Panel;

import java.awt.*;

public class CosmicInvaders {
    private Frame frame;
    private MenuPanel menuPanel;
    private HowPanel howPanel;
    private Game game;
    private Panel contentPane;
    private CardLayout cardLayout;

    AudioPlayer menuMusic = new AudioPlayer("bgmusic.wav");

    public CosmicInvaders(){
        menuMusic.play();
        menuMusic.loop();
        frame = new Frame("Cosmic Invasion");

        // create Panels
        menuPanel = new MenuPanel();
        howPanel = new HowPanel();
        game = new Game(frame);

        // setup the content pane and card layout
        contentPane = new Panel(true, "bg/menu.png");
        cardLayout = new CardLayout();
        contentPane.setLayout(cardLayout);

        // add the panels to the content pane
        contentPane.add(menuPanel, "menuPanel");
        contentPane.add(howPanel, "howPanel");
        contentPane.add(game, "game");

        listenToMenu();
        listenToHow();
        listenToMainGame();

        frame.addKeyListener(game);
        frame.addMouseListener(game);
        frame.add(contentPane);
        frame.pack();
        frame.setVisible(true);
    }

    public void listenToMenu() {
        menuPanel.getStartButton().addActionListener(e -> {
            cardLayout.show(contentPane, "game" );
            menuMusic.stop();
            game.startGame(1);
        });
        menuPanel.getInstructionsButton().addActionListener(e -> cardLayout.show(contentPane, "howPanel" ));
        menuPanel.getExitButton().addActionListener(e -> System.exit(0));
        menuPanel.getMusicOnButton().addActionListener(e -> soundClick(false));
        menuPanel.getMusicOffButton().addActionListener(e -> soundClick(false));
    }

    public void listenToHow() {
         howPanel.getMusicOnButton().addActionListener(e -> soundClick(false));
         howPanel.getMusicOffButton().addActionListener(e -> soundClick(false));
         howPanel.getHomeButton().addActionListener(e -> cardLayout.show(contentPane, "menuPanel"));
    }

    public void listenToMainGame() {
        game.getMusicOnButton().addActionListener(e -> soundClick(true));
        game.getMusicOffButton().addActionListener(e -> soundClick(true));
        game.getPauseHomeButton().addActionListener(e -> {
            cardLayout.show(contentPane, "menuPanel");
            game.mainGameMusic.stop();
            menuMusic.play();
        });
        game.getHomeButton().addActionListener(e -> {
            cardLayout.show(contentPane, "menuPanel");
            game.mainGameMusic.stop();
            menuMusic.play();
        });
        game.getPauseExitButton().addActionListener(e -> System.exit(0));
    }

    public void soundClick(boolean fromMainGame) {
        if (fromMainGame) {
            if (game.mainGameMusic.isPlaying()) {
                game.mainGameMusic.stop();
            } else {
                game.mainGameMusic.play();
            }
        } else {
            menuPanel.musicClick();
            howPanel.musicClick();
            if (menuMusic.isPlaying()) {
                menuMusic.stop();
            } else {
                menuMusic.play();
            }
        }
    }
}
