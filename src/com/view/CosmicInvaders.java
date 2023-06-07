package view;
import view.component.AudioPlayer;
import view.component.Frame;
import view.component.Game;
import view.component.Panel;

import java.awt.*;

public class CosmicInvaders {
    private Frame frame;
    private MenuPanel menuPanel;
    private HowPanel howPanel;
    private Game game;
    private Panel contentPane;
    private CardLayout cardLayout;
    private AudioPlayer audio;

    public CosmicInvaders(){
        audio = new AudioPlayer("bgmusic.wav");
        audio.play();
        audio.loop();
        frame = new Frame("CosmicInvaders");

        // create Panels
        menuPanel = new MenuPanel();
        howPanel = new HowPanel();
        game = new Game();

        // setup the content pane and card layout
        contentPane = new Panel(true, "bg/menu.png");
        cardLayout = new CardLayout();
        contentPane.setLayout(cardLayout);

        // add the panels to the content pane
        contentPane.add(menuPanel, "menuPanel");
        contentPane.add(howPanel, "howPanel");
        contentPane.add(game, "game");

        listenToMenu();
//        listenToHow();

        frame.addKeyListener(game);
        frame.addMouseListener(game);
        frame.add(contentPane);
        frame.pack();
        frame.setVisible(true);
    }

    public void listenToMenu() {
        menuPanel.getStartButton().addActionListener(e -> cardLayout.show(contentPane, "game" ));
        menuPanel.getInstructionsButton().addActionListener(e -> cardLayout.show(contentPane, "howPanel" ));
        menuPanel.getExitButton().addActionListener(e -> System.exit(0));
        menuPanel.getMusicOnButton().addActionListener(e -> soundClick());
        menuPanel.getMusicOffButton().addActionListener(e -> soundClick());
    }

    public void listenToHow() {
        // howPanel.getMusicOnButton().addActionListener(e -> soundClick());
        // howPanel.getMusicOffButton().addActionListener(e -> soundClick());
        // howPanel.getHomeButton().addActionListener(e -> cardLayout.show(contentPane, "menuPanel"));
    }

    public void soundClick() {
        menuPanel.musicClick();
        // howPanel.musicClick();
        if (audio.isPlaying()) {
            audio.stop();
        } else {
            audio.play();
        }
    }

}
