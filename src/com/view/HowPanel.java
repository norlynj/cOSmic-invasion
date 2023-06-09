package view;

import view.component.Frame;
import view.component.ImageButton;
import view.component.Panel;

public class HowPanel extends Panel{

    private ImageButton musicOnButton, musicOffButton, homeButton, entitiesButton, controlsButton,
            levelsButton;
    public HowPanel() {

        super("bg/how-1.png");

        musicOnButton = new ImageButton("buttons/music-on.png");
        musicOffButton = new ImageButton("buttons/music-off.png");
        homeButton = new ImageButton("buttons/home.png");
        entitiesButton = new ImageButton("buttons/entities.png");
        controlsButton = new ImageButton("buttons/controls.png");
        levelsButton = new ImageButton("buttons/levels.png");

        musicOnButton.setBounds(939, 22, 40, 54);
        musicOffButton.setBounds(939, 22, 40, 54);
        homeButton.setBounds(993, 30, 40, 42);
        entitiesButton.setBounds(322, 126, 133, 41);
        controlsButton.setBounds(457, 126, 150, 41);
        levelsButton.setBounds(645, 126, 121, 41);

        musicOffButton.setVisible(false);

        setListeners();

        this.add(musicOnButton);
        this.add(musicOffButton);
        this.add(homeButton);
        this.add(entitiesButton);
        this.add(controlsButton);
        this.add(levelsButton);
    }

    private void setListeners() {
        musicOnButton.hover("buttons/music-off-hover.png", "buttons/music-on.png");
        musicOffButton.hover("buttons/music-on-hover.png", "buttons/music-off.png");
        homeButton.hover("buttons/home-hover.png", "buttons/home.png");
        entitiesButton.hover("buttons/entities-hover.png", "buttons/entities.png");
        controlsButton.hover("buttons/controls-hover.png", "buttons/controls.png");
        levelsButton.hover("buttons/levels-hover.png", "buttons/levels.png");
        listenToButtonClicks();
    }

    private void listenToButtonClicks() {
        entitiesButton.addActionListener(e -> {
            setImage("bg/how-1.png");
            controlsButton.setVisible(true);
            controlsButton.setBounds(457, 126, 150, 41);
            levelsButton.setVisible(true);
            levelsButton.setBounds(645, 126, 121, 41);
            entitiesButton.setVisible(true);
            entitiesButton.setBounds(322, 126, 133, 41);
        });
        controlsButton.addActionListener(e -> {
            setImage("bg/how-2.png");
            controlsButton.setVisible(true);
            controlsButton.setBounds(457, 126, 150, 41);
            levelsButton.setVisible(true);
            levelsButton.setBounds(645, 126, 121, 41);
            entitiesButton.setVisible(true);
            entitiesButton.setBounds(322, 126, 133, 41);
        });
        levelsButton.addActionListener(e -> {
            setImage("bg/how-3.png");
            controlsButton.setVisible(true);
            controlsButton.setBounds(457, 126, 150, 41);
            levelsButton.setVisible(true);
            levelsButton.setBounds(645, 126, 121, 41);
            entitiesButton.setVisible(true);
            entitiesButton.setBounds(322, 126, 133, 41);
        });
    }

    public void musicClick() {
        if (musicOffButton.isVisible()){
            musicOnButton.setVisible(true);
            musicOffButton.setVisible(false);
        } else {
            musicOnButton.setVisible(false);
            musicOffButton.setVisible(true);
        }
    }


    public static void main(String[] args) {
        HowPanel m = new HowPanel();
        Frame frame = new Frame("How Panel");
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

}
