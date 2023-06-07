package view;

import view.component.Frame;
import view.component.ImageButton;
import view.component.Panel;

public class HowPanel extends Panel{
//
//    private ImageButton musicOnButton, musicOffButton, homeButton, defButton, termsButton,
//            choicesButton, formatButton, backButton, inputButton, outputButton;
    public HowPanel() {

        super("bg/how-1.png");
//
//        musicOnButton = new ImageButton("buttons/volume-on.png");
//        musicOffButton = new ImageButton("buttons/volume-off.png");
//        homeButton = new ImageButton("buttons/home.png");
//        defButton = new ImageButton("buttons/def.png");
//        termsButton = new ImageButton("buttons/terms.png");
//        choicesButton = new ImageButton("buttons/choices.png");
//        formatButton = new ImageButton("buttons/format.png");
//        backButton = new ImageButton("buttons/back.png");
//        inputButton = new ImageButton("buttons/input.png");
//        outputButton = new ImageButton("buttons/output.png");
//
//        musicOnButton.setBounds(958, 37, 44, 44);
//        musicOffButton.setBounds(958, 37, 44, 44);
//        homeButton.setBounds(1020, 37, 44, 44);
//        defButton.setBounds(55, 157, 401, 40);
//        termsButton.setBounds(463, 157, 121, 40);
//        formatButton.setBounds(881, 405, 121, 40);
//        backButton.setBounds(352, 220, 47, 47);
//        inputButton.setBounds(591,157, 121, 40);
//        outputButton.setBounds(719,157, 121, 40);
//
//
//        musicOffButton.setVisible(false);
//        backButton.setVisible(false);
//        choicesButton.setVisible(false);
//
//
//        setListeners();
//
//        this.add(musicOnButton);
//        this.add(musicOffButton);
//        this.add(homeButton);
//        this.add(defButton);
//        this.add(termsButton);
//        this.add(choicesButton);
//        this.add(formatButton);
//        this.add(backButton);
//        this.add(inputButton);
//        this.add(outputButton);
    }
//
//    private void setListeners() {
//        musicOnButton.hover("buttons/volume-off-hover.png", "buttons/volume-on.png");
//        musicOffButton.hover("buttons/volume-on-hover.png", "buttons/volume-off.png");
//        homeButton.hover("buttons/home-hover.png", "buttons/home.png");
//        defButton.hover("buttons/def-hover.png", "buttons/def.png");
//        termsButton.hover("buttons/terms-hover.png", "buttons/terms.png");
//        formatButton.hover("buttons/format-hover.png", "buttons/format.png");
//        backButton.hover("buttons/back-hover.png", "buttons/back.png");
//        choicesButton.hover("buttons/choices-hover.png", "buttons/choices.png");
//        inputButton.hover("buttons/input-hover.png", "buttons/input.png");
//        outputButton.hover("buttons/output-hover.png", "buttons/output.png");
//        listenToButtonClicks();
//    }
//
//    private void listenToButtonClicks() {
//        defButton.addActionListener(e -> {
//            setImage("bg/how-3.png");
//            backButton.setVisible(false);
//            termsButton.setVisible(true);
//            termsButton.setBounds(55, 157, 121, 40);
//            choicesButton.setVisible(true);
//            choicesButton.setBounds(180, 157, 121, 40);
//            defButton.setVisible(false);
//            formatButton.setVisible(false);
//            inputButton.setVisible(true);
//            outputButton.setVisible(true);
//            inputButton.setBounds(308,157, 121, 40);
//            outputButton.setBounds(436,157, 121, 40);
//        });
//        termsButton.addActionListener(e -> {
//            setImage("bg/how-2.png");
//            backButton.setVisible(false);
//            defButton.setVisible(true);
//            defButton.setBounds(55, 157, 401, 40);
//            choicesButton.setVisible(true);
//            choicesButton.setBounds(463, 157, 121, 40);
//            termsButton.setVisible(false);
//            formatButton.setVisible(false);
//            inputButton.setVisible(true);
//            outputButton.setVisible(true);
//            inputButton.setBounds(591,157, 121, 40);
//            outputButton.setBounds(719,157, 121, 40);
//        });
//        formatButton.addActionListener(e -> {
//            setImage("bg/how-1-1.png");
//            defButton.setVisible(true);
//            defButton.setBounds(55, 157, 401, 40);
//            termsButton.setVisible(true);
//            termsButton.setBounds(463, 157, 121, 40);
//            backButton.setVisible(true);
//            choicesButton.setVisible(false);
//            formatButton.setVisible(false);
//            inputButton.setVisible(true);
//            outputButton.setVisible(true);
//            inputButton.setBounds(591,157, 121, 40);
//            outputButton.setBounds(719,157, 121, 40);
//        });
//        backButton.addActionListener(e -> {
//            setImage("bg/how-1.png");
//            defButton.setVisible(true);
//            termsButton.setVisible(true);
//            defButton.setBounds(55, 157, 401, 40);
//            termsButton.setBounds(463, 157, 121, 40);
//            backButton.setVisible(false);
//            formatButton.setVisible(true);
//            formatButton.setBounds(881, 405, 121, 40);
//            inputButton.setVisible(true);
//            outputButton.setVisible(true);
//            choicesButton.setVisible(false);
//            inputButton.setBounds(591,157, 121, 40);
//            outputButton.setBounds(719,157, 121, 40);
//        });
//        choicesButton.addActionListener(e -> {
//            setImage("bg/how-1.png");
//            defButton.setVisible(true);
//            termsButton.setVisible(true);
//            defButton.setBounds(55, 157, 401, 40);
//            termsButton.setBounds(463, 157, 121, 40);
//            backButton.setVisible(false);
//            formatButton.setVisible(true);
//            formatButton.setBounds(881, 405, 121, 40);
//            inputButton.setVisible(true);
//            outputButton.setVisible(true);
//            choicesButton.setVisible(false);
//            inputButton.setBounds(591,157, 121, 40);
//            outputButton.setBounds(719,157, 121, 40);
//        });
//        inputButton.addActionListener(e -> {
//            setImage("bg/how-4.png");
//            defButton.setVisible(true);
//            termsButton.setVisible(true);
//            defButton.setBounds(438, 156, 401, 40);
//            termsButton.setBounds(55, 157, 121, 40);
//            backButton.setVisible(false);
//            formatButton.setVisible(false);
//            outputButton.setVisible(true);
//            outputButton.setBounds(308,157, 121, 40);
//            choicesButton.setVisible(true);
//            choicesButton.setBounds(180, 157, 121, 40);
//            inputButton.setVisible(false);
//        });
//        outputButton.addActionListener(e -> {
//            setImage("bg/how-5.png");
//            defButton.setVisible(true);
//            termsButton.setVisible(true);
//            defButton.setBounds(438, 156, 401, 40);
//            termsButton.setBounds(55, 157, 121, 40);
//            backButton.setVisible(false);
//            formatButton.setVisible(false);
//            inputButton.setVisible(true);
//            inputButton.setBounds(308,157, 121, 40);
//            choicesButton.setVisible(true);
//            choicesButton.setBounds(180, 157, 121, 40);
//            outputButton.setVisible(false);
//        });
//    }
//
//    public void musicClick() {
//        if (musicOffButton.isVisible()){
//            musicOnButton.setVisible(true);
//            musicOffButton.setVisible(false);
//        } else {
//            musicOnButton.setVisible(false);
//            musicOffButton.setVisible(true);
//        }
//    }
//
//
//    public static void main(String[] args) {
//        HowPanel m = new HowPanel();
//        Frame frame = new Frame("How Panel");
//        frame.add(m);
//        frame.setVisible(true);
//    }
//
//
//    public ImageButton getHomeButton() {
//        return homeButton;
//    }
//    public ImageButton getMusicOnButton() {
//        return musicOnButton;
//    }
//    public ImageButton getMusicOffButton() {
//        return musicOffButton;
//    }

}
