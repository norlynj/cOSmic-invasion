package view.component;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class Panel extends JPanel {
    private ImageIcon background;

    public Panel(String imageName) {
        this(true, imageName, 0, 0, 1100, 800); //panel with bg photo
    }

    public Panel(boolean visible, String imageName) {
        this(visible, imageName, 0, 0, 1100, 800); //panel with bg photo
    }


    public Panel(int x, int y, int width, int height) {
        this(true, "", x, y, width, height); //Panel with dimensions and position
    }

    public Panel(boolean visible, String imageName, int x, int y, int width, int height) {
        setOpaque(false);
        setBounds(x, y, width, height);

        if (!imageName.equals("")) { //Checks that the specified object reference is not null. This method is designed primarily for doing parameter validation in methods and constructors,
            background = new ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/" + imageName))); //upload an image file
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (background != null) {
            g.drawImage(background.getImage(), 0, 0, getWidth(), getHeight(), null);
        }
    }

    //allows one to add a bg image to the panel
    public void setImage(String imageName) {
        background = new ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/" + imageName)));
        repaint();
    }
}
