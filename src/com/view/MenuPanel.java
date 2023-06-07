package view;

import view.component.Frame;
import view.component.ImageButton;
import view.component.Panel;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Objects;

public class MenuPanel extends Panel{

    private ImageButton musicOnButton, musicOffButton, aboutButton;
    private ImageButton startButton, instructionsButton, exitButton;
    private Panel aboutPanel;

    public MenuPanel(){
        super("bg/menu.png");

        startButton = new ImageButton("buttons/start.png");
        instructionsButton = new ImageButton("buttons/how.png");
        exitButton = new ImageButton("buttons/exit.png");

        musicOnButton = new ImageButton("buttons/music-on.png");
        musicOffButton = new ImageButton("buttons/music-off.png");
        aboutButton = new ImageButton("buttons/about.png");
        aboutPanel = new Panel("bg/info-hover-label.png");

        startButton.setBounds(188, 400, 145, 63);
        instructionsButton.setBounds(188, 489, 317, 63);
        exitButton.setBounds(188, 578, 104, 63);
        musicOnButton.setBounds(939, 22, 40, 54);
        musicOffButton.setBounds(939, 22, 40, 54);
        aboutButton.setBounds(995, 30, 73, 40);
        aboutPanel.setBounds(721, 59, 320, 141);

        musicOffButton.setVisible(false);
        aboutPanel.setVisible(false);



        setListeners();

        ImageIcon background = new ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/bg/menu.png")));

        JLabel bgImage = new JLabel();

        bgImage.setBounds(0, 0, 1100, 800);
        bgImage.setIcon(background);
        bgImage.add(startButton);
        bgImage.add(instructionsButton);
        bgImage.add(exitButton);
        bgImage.add(aboutPanel);
        bgImage.add(musicOnButton);
        bgImage.add(musicOffButton);
        bgImage.add(aboutButton);

        this.add(bgImage);
    }

    private void setListeners(){
        startButton.hover("buttons/start-hover.png", "buttons/start.png");
        instructionsButton.hover("buttons/how-hover.png", "buttons/how.png");
        exitButton.hover("buttons/exit-hover.png", "buttons/exit.png");
        aboutButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { aboutPanel.setVisible(true); }
            public void mouseExited(MouseEvent e) { aboutPanel.setVisible(false); }
        });
        musicOnButton.hover("buttons/music-off-hover.png", "buttons/music-on.png");
        musicOffButton.hover("buttons/music-on-hover.png", "buttons/music-off.png");
        aboutButton.hover("buttons/about-hover.png", "buttons/about.png");
    }

    public static void main(String[] args) {
        MenuPanel m = new MenuPanel();
        Frame frame = new Frame("Menu Panel");
        frame.add(m);
        frame.setVisible(true);
    }

    public ImageButton getStartButton() {
        return startButton;
    }

    public ImageButton getInstructionsButton() {
        return instructionsButton;
    }

    public ImageButton getExitButton() {
        return exitButton;
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

    public ImageButton getMusicOnButton() {
        return musicOnButton;
    }
    public ImageButton getMusicOffButton() {
        return musicOffButton;
    }
}