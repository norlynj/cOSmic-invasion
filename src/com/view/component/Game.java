package view.component;

public class Game extends Panel {

    private ImageButton musicOnButton, musicOffButton, pauseButton;
    public Game() {
        super("bg/game-panel.png");
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

}
