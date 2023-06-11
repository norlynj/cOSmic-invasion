package view;

import view.component.Frame;
import view.component.ImageButton;
import view.component.Panel;

import java.awt.*;

public class HowPanel extends Panel{

    private ImageButton musicOnButton, musicOffButton, homeButton, entitiesButton, controlsButton, levelsButton, entitiesAButton, controlsAButton, levelsAButton;
    public HowPanel() {

        super("bg/how-1.png");

        musicOnButton = new ImageButton("buttons/music-on.png");
        musicOffButton = new ImageButton("buttons/music-off.png");
        homeButton = new ImageButton("buttons/home.png");
        entitiesButton = new ImageButton("buttons/entities.png");
        controlsButton = new ImageButton("buttons/controls.png");
        levelsButton = new ImageButton("buttons/levels.png");

        // active state
        entitiesAButton = new ImageButton("buttons/entities-hover.png");
        controlsAButton = new ImageButton("buttons/controls-hover.png");
        levelsAButton = new ImageButton("buttons/levels-hover.png");

        entitiesAButton.setVisible(false);
        controlsAButton.setVisible(false);
        levelsAButton.setVisible(false);

        musicOffButton.setVisible(false);

        setListeners();

        this.add(musicOnButton);
        this.add(musicOffButton);
        this.add(homeButton);
        this.add(entitiesButton);
        this.add(controlsButton);
        this.add(levelsButton);
        this.add(entitiesAButton);
        this.add(controlsAButton);
        this.add(levelsAButton);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        musicOnButton.setBounds(939, 22, 40, 54);
        musicOffButton.setBounds(939, 22, 40, 54);
        homeButton.setBounds(993, 30, 40, 42);
        setButtonBounds();

    }

    private void setListeners() {
        musicOnButton.hover("buttons/music-off-hover.png", "buttons/music-on.png");
        musicOffButton.hover("buttons/music-on-hover.png", "buttons/music-off.png");
        homeButton.hover("buttons/home-hover.png", "buttons/home.png");
        entitiesButton.hover("buttons/entities-hover.png", "buttons/entities.png");
        controlsButton.hover("buttons/controls-hover.png", "buttons/controls.png");
        levelsButton.hover("buttons/levels-hover.png", "buttons/levels.png");
        entitiesAButton.hover("buttons/entities.png", "buttons/entities-hover.png");
        controlsAButton.hover("buttons/controls.png", "buttons/controls-hover.png");
        levelsAButton.hover("buttons/levels.png", "buttons/levels-hover.png");
        listenToButtonClicks();
    }

    private void listenToButtonClicks() {
        entitiesButton.addActionListener(e -> {
            setButtonActiveState(entitiesAButton, entitiesButton);
            setButtonActiveState(controlsButton, controlsAButton);
            setButtonActiveState(levelsButton, levelsAButton);
            setImage("bg/how-1.png");
            setButtonBounds();
        });
        controlsButton.addActionListener(e -> {
            setButtonActiveState(controlsAButton, controlsButton);
            setButtonActiveState(entitiesButton, entitiesAButton);
            setButtonActiveState(levelsButton, levelsAButton);
            setImage("bg/how-2.png");
            setButtonBounds();
        });
        levelsButton.addActionListener(e -> {
            setButtonActiveState(controlsButton, controlsAButton);
            setButtonActiveState(entitiesButton, entitiesAButton);
            setButtonActiveState(levelsAButton, levelsButton);
            setImage("bg/how-3.png");
            setButtonBounds();
        });
    }

    private void setButtonActiveState(ImageButton visibleButton, ImageButton hideButton) {
        visibleButton.setVisible(true);
        hideButton.setVisible(false);
    }
    private void setButtonBounds() {
        entitiesButton.setBounds(330, 126, 133, 41);
        controlsButton.setBounds(486, 126, 150, 41);
        levelsButton.setBounds(655, 126, 121, 41);
        entitiesAButton.setBounds(330, 126, 133, 41);
        controlsAButton.setBounds(486, 126, 150, 41);
        levelsAButton.setBounds(655, 126, 121, 41);
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
