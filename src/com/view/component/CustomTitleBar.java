package view.component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CustomTitleBar extends JPanel {
    private JFrame frame;

    public CustomTitleBar(JFrame frame) {
        this.frame = frame;
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(frame.getWidth(), 40));

        // Create button container panel with right-aligned FlowLayout
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));

        // Create custom minimize button
        ImageButton minimizeButton = new ImageButton("buttons/minimize.png");
        minimizeButton.setPreferredSize(new Dimension(40, 40));
        minimizeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setState(Frame.ICONIFIED);
            }
        });

        // Create custom close button
        ImageButton closeButton = new ImageButton("buttons/close.png");
        closeButton.setPreferredSize(new Dimension(40, 40));
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        // Add buttons to the button container panel
        buttonPanel.add(minimizeButton);
        buttonPanel.add(closeButton);
        setBackground(Color.BLACK);

        // Add button container panel and title label to the title bar
        add(buttonPanel, BorderLayout.EAST);

        minimizeButton.hover("buttons/minimize-hover.png", "buttons/minimize.png");
        closeButton.hover("buttons/close-hover.png", "buttons/close.png");
    }
}
