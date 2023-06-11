package view.component;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class  Frame extends JFrame {
    JWindow window = new JWindow();
    public Frame(String name) {
//        loadImage("bg/splashscreen.gif");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getRootPane().setWindowDecorationStyle(JRootPane.NONE); // Remove system title bar
        setTitle(name);
        setUndecorated(true);
        setResizable(false);

        CustomTitleBar titleBar = new CustomTitleBar(this);
        setContentPane(new JPanel(new BorderLayout()));
        getContentPane().add(titleBar, BorderLayout.NORTH);
        getContentPane().setPreferredSize(new Dimension(1100, 800));
        pack();
        setLocationRelativeTo(null);
    }

    private void loadImage(String imageName) {

        //splash screen
        window.getContentPane().add(
                new JLabel("", new ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/" + imageName))), SwingConstants.CENTER));

        window.setSize(500, 200);
        window.setLocationRelativeTo(null); //set position to the center screen

        window.setVisible(true);
        try {
            Thread.sleep(4000); //show the splash screen for 4 seconds
        } catch (InterruptedException e) {
            e.getMessage();
        }

        window.dispose(); //disappears then after
    }
}
